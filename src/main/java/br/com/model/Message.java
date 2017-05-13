package br.com.model;

import br.com.protocol.Protocol;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by MarioJ on 21/03/16.
 */
public class Message implements Serializable {

    private String fromAddress;
    private String toAddress;
    private String message;

    public Message(String fromAddress, String toAddress, String message) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.message = message;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
