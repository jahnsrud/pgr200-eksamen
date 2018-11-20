package no.kristiania.pgr200.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private Map<String, String> outputs;

    public HttpHeaders() {
        outputs = new HashMap<>();
    }

    public void put(String key, String value) {
        outputs.put(key, value);
    }

    public String get(String key) {
        return outputs.get(key);
    }

    public Map<String, String>getMap() {
        return outputs;
    }

    public void writeHeaders(OutputStream outputStream) throws IOException {

        for (Map.Entry<String, String> entry : outputs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            outputStream.write((key + ": " + value + "\r\n").getBytes());

        }

    }

    public void readHeaders(InputStream input) throws IOException {
        String headerLine;
        while (!(headerLine = HttpReadWrite.readLine(input)).isEmpty()) {

            parseHeaderLine(headerLine);
        }


    }

    private void parseHeaderLine(String headerLine) {

        int colonPos = headerLine.indexOf(":");

        if (colonPos != -1) {

            String key = headerLine.substring(0, colonPos).trim();
            String value = headerLine.substring(colonPos + 1).trim();
            outputs.put(key, value);

        }

    }

    public void writeEmptyLine(OutputStream outputStream) throws IOException {

        outputStream.write("\r\n".getBytes());

    }

    public void setContentLength(int contentLength) {
        outputs.put("Content-Length", String.valueOf(contentLength));
    }

    public int getContentLength() {
        String contentLength = get("Content-Length");
        return contentLength != null ? Integer.parseInt(contentLength) : -1;
    }

}
