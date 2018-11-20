package no.kristiania.pgr200.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpReadWrite {

    public static String readLine(InputStream input) throws IOException {

        StringBuilder result = new StringBuilder();
        int c;
        while ((c = input.read()) != -1) {

            if (c == '\r') {
                c = input.read();
                break;
            }


            result.append((char)c);
        }

        return result.toString();

    }

    public static String readBody(InputStream input, int contentLength) throws IOException {

        if (contentLength < 0) {
            return null;
        }

        StringBuilder body = new StringBuilder();

        int c;
        int remainder = contentLength;

        while ((remainder-- > 0) && (c = input.read()) != -1) {

            body.append((char)c);
        }

        return body.toString();

    }


    public static void writeLine(OutputStream outputStream, String line) throws IOException {
        outputStream.write((line + "\r\n").getBytes());
    }


}
