package br.com.protocol;

import br.com.model.Message;
import com.google.gson.Gson;

/**
 * Created by MarioJ on 20/03/16.
 */
public class Protocol {

    public static final String ALL = "all";

    public static Gson gson;

    static {
        gson = new Gson();
    }

    public static Message toMessage(String json) {
        return gson.fromJson(json, Message.class);
    }
}
