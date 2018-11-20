package no.kristiania.pgr200.server;

import com.google.gson.Gson;
import no.kristiania.pgr200.core.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServer {

    private ServerSocket serverSocket;
    private DatabaseManager databaseManager;
    private TalkDao dao;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(this::startServer).start();

    }

    private void startServer() {

        try {
            databaseManager = new DatabaseManager();
            dao = new TalkDao(databaseManager.createDataSource());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server available on port: " + getPort() + " (host: " + serverSocket.getInetAddress().getHostAddress() +")");

        while (true) {

            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("👋 New connection: " + clientSocket.getInetAddress().getHostAddress());
                System.out.println("- Hostname: " + clientSocket.getInetAddress().getHostName());

                handleRequestFromClient(clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void handleRequestFromClient(Socket clientSocket) throws IOException {

        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();

        HttpHeaders responseHeaders = new HttpHeaders();
        HttpQuery query;
        String body = "";

        try {

            String requestLine = HttpReadWrite.readLine(input);
            String requestTarget = requestLine.split(" ")[1];

            HttpPath path = new HttpPath(requestTarget);

            HttpHeaders headers = new HttpHeaders();
            headers.readHeaders(input);

            // Check if request is GET or POST

            boolean isPostRequest = false;
            if (requestLine.split(" ")[0].equals("POST")) {

                System.out.println("Server: Request is POST - HttpServer");

                body = HttpReadWrite.readBody(input, headers.getContentLength());
                query = new HttpQuery(body);

                System.out.println("Server: Content-Length from POST: " + headers.getContentLength());

                if (path.getPath().equalsIgnoreCase("/api/add-talk")) {
                    body = addTalk(body);

                }

                isPostRequest = true;

            } else {
                query = path.getQuery();

                System.out.println("Server: Current Path:");
                System.out.println(path.getPath());


                if (path.getPath().contains("/api/list-talks?")) {
                    body = handleListAllTalksByTopic(query.getParameter("topic"));
                }
                if (path.getPath().equalsIgnoreCase("/api/list-talks")) {
                    body = handleListAllTalks();
                } else if (path.getPath().contains("/api/view-talk?id=")) {
                    body = viewTalk(Integer.parseInt(query.getParameter("id")));
                }


            }

            if (body == null || body.length() == 0) {
                // Returns the default body
                body = "Kristiania!";

                // Uncomment to unleash a time machine:
                // body = getHtmlWebsite();
            }


        } catch (RuntimeException e) {

            writeResponseLine(clientSocket,"500");
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        writeResponseLine(clientSocket, "200");

        responseHeaders.put("X-Server-Name", Utils.SERVER_NAME);
        responseHeaders.put("Content-Type", Utils.CONTENT_TYPE);
        responseHeaders.put("Connection", "close");
        responseHeaders.put("Content-Length", "" + body.length());

        responseHeaders.writeHeaders(output);
        responseHeaders.writeEmptyLine(output);

        output.write((body + "\r\n").getBytes());

    }

    public void writeResponseLine(Socket clientSocket, String statusCode) throws IOException {

        String statusMessage = getStatusMessage(statusCode);
        HttpReadWrite.writeLine(clientSocket.getOutputStream(), "HTTP/1.1 " + statusCode + " " + statusMessage);

    }

    public String handleListAllTalks() {

        try {

            List<Talk> talks = dao.listAll();
            String result = JSONConverter.convertTalkArrayToJSON(talks);

            return result;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

    private String handleListAllTalksByTopic(String topic) {

        try {

            List<Talk> talks = dao.listAllTalksByTopic(topic);
            String result = JSONConverter.convertTalkArrayToJSON(talks);

            return result;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }


    private String addTalk(String jsonBody) throws SQLException {

        Gson gson = new Gson();
        Talk talk = gson.fromJson(jsonBody, Talk.class);

        dao.createTableIfNotExists();

        try {
            dao.create(talk);

            return "✅ Talk added: " + talk.getTitle();

        } catch (SQLException e) {
            e.printStackTrace();

            return "Failed to add talk: " + e.getStackTrace();

        }

    }

    private String viewTalk(int id) throws SQLException {

        Talk talk = dao.getTalkById(id);

        return JSONConverter.convertTalkToJSON(talk);

    }

    private String getHtmlWebsite() {

        StringBuilder contentBuilder = new StringBuilder();

        try {

            InputStream inputBody = getClass().getClassLoader().getResourceAsStream("1995webdesign.html");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputBody));
            String str;
            while ((str = in.readLine())!=null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();

    }

    private static Map<String, String> statusCodes = new HashMap<>();
    static {
        statusCodes.put("100", "Continue");
        statusCodes.put("200", "OK");
        statusCodes.put("301", "Moved Permanently");
        statusCodes.put("302", "Found");
        statusCodes.put("307", "Temporary Redirect");
        statusCodes.put("308", "Permanent Redirect");
        statusCodes.put("400", "Bad Request");
        statusCodes.put("401", "Unauthorized");
        statusCodes.put("403", "Forbidden");
        statusCodes.put("404", "Not Found");
        statusCodes.put("408", "Request Timeout");
        statusCodes.put("418", "I'm a Teapot");
        statusCodes.put("500", "Internal Server Error");
    }

    private String getStatusMessage(String statusCode){
        return statusCodes.get(statusCode);
        }

    public int getPort() {
        return serverSocket.getLocalPort();
    }


}
