package no.kristiania.pgr200.core;

import java.util.ArrayList;
import java.util.List;

public class HttpPath {

    private String path;
    private HttpQuery query = new HttpQuery();

    public HttpPath(String fullPath) {

        int questionPos = fullPath.indexOf("?");
        if (questionPos > 0) {
            this.path = fullPath.substring(0, questionPos);
            this.query = new HttpQuery(fullPath.substring(questionPos+1));
        } else {
            this.path = fullPath;
        }

    }

    public String getPath() {

       return path;

    }

    public void setParameter(String key, String value) {

        query.setParameter(key, value);
    }

    public String getParameter(String parameter) {

        return query.getParameter(parameter);
    }

    public HttpQuery getQuery() {

        return query;
    }

    public String toString() {
        return getPath() + "?" + getQuery();

    }
}
