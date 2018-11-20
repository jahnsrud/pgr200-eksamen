package no.kristiania.pgr200.core;

import com.google.gson.Gson;
import java.util.List;

public class JSONConverter {

    public static String convertTalkToJSON(Talk talk) {

        Gson gson = new Gson();
        return gson.toJson(talk);

    }

    public static String convertTalkArrayToJSON(List<Talk> talks) {
        return new Gson().toJson(talks);

    }

}
