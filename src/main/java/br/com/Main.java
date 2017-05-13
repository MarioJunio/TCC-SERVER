package br.com;

import br.com.service.Server;

import java.io.IOException;

/**
 * Created by MarioJ on 21/03/16.
 */
public class Main {

    static Server server;

    public static void main(String[] args) {

        try {

            server = new Server(9897);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
