package no.kristiania.pgr200;

import no.kristiania.pgr200.core.HttpRequest;
import no.kristiania.pgr200.core.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpClientTest {

    @Test
    public void shouldGetStatus200() {

        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);

    }

    @Test
    public void shouldReadHeaders() {

        String bodyToTest = "Hello,+Kristiania";

        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?body=" + bodyToTest);
        HttpResponse response = request.execute();

        String connection = response.getValueForHeader("Connection");
        String server = response.getValueForHeader("Server");
        String contentType = response.getValueForHeader("Content-Type");

        assertThat(connection).isEqualTo("close");
        assertThat(contentType).isEqualTo("text/html; charset=utf-8");
        assertThat(response.getBody()).isEqualTo("Hello, Kristiania");
        assertThat(server).isEqualTo("Google Frontend");

    }

    @Test
    public void shouldReadBody() {

        String bodyToTest = "Hello%20Kristiania";

        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?body=" + bodyToTest);
        HttpResponse response = request.execute();

        String body = response.getBody();

        assertThat(body).isEqualTo("Hello Kristiania");

    }

    @Test
    public void bodyMatchesContentLength() {

        String bodyToTest = "Hello&20Kristiania";

        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?body=" + bodyToTest);
        HttpResponse response = request.execute();

        String body = response.getBody();

        assertThat(body.length()).isEqualTo(response.getContentLength());

    }

}
