package no.kristiania.pgr200.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpQuery {

    private Map<String, String> parameters = new LinkedHashMap<>();

    public HttpQuery() {

    }

    public HttpQuery(String query) {

        if (query == null) {

        }

        else if (query.length() > 0) {

            for (String parameter : query.split("&")) {
                parseParameter(parameter);
            }

        }

    }

    /**
     * Parsing the parameter by
     * @param parameter
     */

    public void parseParameter(String parameter) {

        int equalsPos = parameter.indexOf("=");

        if (equalsPos != -1) {

            String key = decodeURL(parameter.substring(0, equalsPos));
            String value = decodeURL(parameter.substring(equalsPos + 1));

            parameters.put(key, value);
        }

    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding. Wow.");
        }
    }

    private String decodeURL(String substring) {
        try {
            return URLDecoder.decode(substring, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding. Wow.");

        }
    }

    public String toString() {
        if (parameters.isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (String key : parameters.keySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(urlEncode(key)).append("=").append(urlEncode(parameters.get(key)));
        }
        return result.toString();
    }

    public boolean containsParameter(String key){

        return parameters.containsKey(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);

    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }


}
