package no.kristiania.pgr200.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import no.kristiania.pgr200.core.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class HttpClient {

    public List<Talk> listTalksFromServer() throws IOException {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, Utils.DEFAULT_PORT, "/api/list-talks");
        HttpResponse response = request.execute();

        String jsonBody = response.getBody();

        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Talk>>(){}.getType();
        List<Talk> talks = gson.fromJson(jsonBody, collectionType);

        return talks;

    }

    public List<Talk> listTalksByTopicFromServer(String topic) throws IOException {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, Utils.DEFAULT_PORT, "/api/list-talks?topic=" + topic);
        HttpResponse response = request.execute();

        String jsonBody = response.getBody();

        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Talk>>(){}.getType();
        List<Talk> talks = gson.fromJson(jsonBody, collectionType);

        return talks;

    }

    public Talk getTalkById(int id) throws IOException {

        HttpRequest request = new HttpRequest(Utils.SERVER_HOSTNAME, Utils.DEFAULT_PORT, "/api/view-talk?id=" + id);
        HttpResponse response = request.execute();

        String jsonBody = response.getBody();

        Gson gson = new Gson();
        Talk talk = gson.fromJson(jsonBody, Talk.class);

        return talk;

    }

    // TODO: IMPLEMENT
    public void updateTalkById(int id) throws IOException {



    }

    public static void addTalkToServer(Talk talk) throws IOException {

        HttpRequest request = new HttpRequest("localhost", Utils.DEFAULT_PORT, "/api/add-talk");
        request.setMethod("POST");

        String talkBody = JSONConverter.convertTalkToJSON(talk);
        request.setBody(talkBody);

        HttpResponse response = request.execute();

        System.out.println(response.getHttpResponseMessage());

        System.out.println("Body:");
        System.out.println(response.getBody());


    }

}
