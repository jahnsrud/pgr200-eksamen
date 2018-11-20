import no.kristiania.pgr200.core.HttpRequest;
import no.kristiania.pgr200.core.HttpResponse;

import no.kristiania.pgr200.core.Utils;
import no.kristiania.pgr200.server.HttpServer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

    private static HttpServer server;

    @BeforeClass
    public static void createServer() throws IOException {
        server = new HttpServer(Utils.DEFAULT_PORT);
    }

    @Test
    public void shouldReturnHeaders() {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, server.getPort(), "/api");
        HttpResponse response = request.execute();

        /*
        System.out.println("------------------");
        System.out.println("DETAILS");
        System.out.println("------------------");

        System.out.println(response.getStatusCode());
        response.printAllHeaders();
        */

        assertThat(response.getValueForHeader("X-Server-Name")).isEqualTo("Websterdals Server");
        assertThat(response.getValueForHeader("Content-Type")).isEqualTo("text/plain; charset=utf-8");
        assertThat(response.getValueForHeader("Connection")).isEqualTo("close");
        assertThat(response.getStatusCode()).isEqualTo(200);

    }

    @Test
    public void shouldRespondDefaultBody() {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, server.getPort(), "/api/");
        HttpResponse response = request.execute();

        System.out.println(response.getBody());

        assertThat(response.getBody()).isEqualTo("Kristiania!");

    }

    @Test
    public void shouldReadPostRequest() {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, server.getPort(), "/api/add-talk");
        request.setMethod("POST");
        request.setBody("postreq");

        System.out.println("REQUEST:");

        System.out.println("POST body:");
        System.out.println(request.getBody());
        System.out.println();

        System.out.println("---------");
        System.out.println("RESPONSE:");

        HttpResponse response = request.execute();
        response.printAllHeaders();


        System.out.println("Response.body:");
        System.out.println(response.getBody());
        System.out.println();


        assertThat(response.getBody()).isEqualTo(request.getBody());

    }


}