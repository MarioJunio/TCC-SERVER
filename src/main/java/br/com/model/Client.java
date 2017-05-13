package br.com.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by MarioJ on 20/03/16.
 */
public class Client {

    private String username;
    private Socket socket;
    public DataInputStream in;
    public DataOutputStream out;

    public static Client create(Socket socket) throws IOException {

        Client client = new Client();
        client.setSocket(socket);
        client.in = new DataInputStream(socket.getInputStream());
        client.out = new DataOutputStream(socket.getOutputStream());

        return client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
