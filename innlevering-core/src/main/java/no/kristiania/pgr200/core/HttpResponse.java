package no.kristiania.pgr200.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpResponse {

    private HttpHeaders responseHeaders = new HttpHeaders();

    private String httpResponseMessage;
    private int statusCode;
    private String body;


    public HttpResponse(Socket socket) throws IOException {

        InputStream input = socket.getInputStream();

        parseHttpStatus(HttpReadWrite.readLine(input));
        responseHeaders.readHeaders(input);
        body = HttpReadWrite.readBody(input, responseHeaders.getContentLength());

    }

    public void parseHttpStatus(String status) {

        int first = status.indexOf(" ");
        int second = status.indexOf(" ", first+1);
        this.statusCode = Integer.parseInt(status.substring(first+1, second));
        this.httpResponseMessage = status.substring(second+1);

    }

    public String getValueForHeader(String header) {

        return responseHeaders.get(header);

    }

    public int getContentLength() {
        return responseHeaders.getContentLength();
    }

    public String getBody() {
        return body;
    }

    public String getHttpResponseMessage() {
        return httpResponseMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Helper method for getting and converting the content length to an integer
     * @return
     */

    public void printAllHeaders() {
        responseHeaders.getMap().forEach((k, v) -> System.out.println(k + ": " + v));

    }

}
