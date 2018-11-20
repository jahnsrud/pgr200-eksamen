package no.kristiania.pgr200.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpRequest {

    private String hostname;
    private int port;
    private String path;
    private String method = "GET";
    private HttpHeaders httpHeaderes;
    private String body;

    public HttpRequest(String hostname, int port, String path) {
        this.hostname = hostname;
        this.port = port;
        this.path = path;

        httpHeaderes = new HttpHeaders();
        httpHeaderes.put("Host", getHostname());
        httpHeaderes.put("Connection", "close");


    }

    public HttpResponse execute() {

        try (Socket socket = new Socket(hostname, port)) {

            writeRequest(socket.getOutputStream());

            if (body != null) {
                httpHeaderes.setContentLength(body.getBytes().length);
            }

            httpHeaderes.writeHeaders(socket.getOutputStream());
            httpHeaderes.writeEmptyLine(socket.getOutputStream());

            if (body != null) {
                socket.getOutputStream().write(body.getBytes());
            }

            return new HttpResponse(socket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPath() {
        return path;
    }

    public void writeRequest(OutputStream output) throws IOException {

        HttpReadWrite.writeLine(output, method + " " + getPath() + " HTTP/1.1");

    }

    public void setBody(String body) {
        this.body = body;
        httpHeaderes.put("Content-Type", "application/x-www-form-urlencoded");

    }

    public String getBody() {
        return body;
    }
}
