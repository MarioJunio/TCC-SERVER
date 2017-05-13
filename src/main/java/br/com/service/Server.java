package br.com.service;

import br.com.model.Client;
import br.com.service.ClientHandler;
import br.com.service.Log;
import br.com.service.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by MarioJ on 20/03/16.
 */
public class Server {

    private static final String TAG = "Server";

    private ServerSocket serverSocket;
    private Manager manager;
    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.manager = new Manager();
    }

    public void start() throws IOException {

        Log.d(TAG, "Servidor iniciado na porta " + String.valueOf(port));

        while (true) {

            Log.nl();
            Log.d(TAG, "Aguardando conexão...");
            Log.nl();

            // cliente conectado
            Socket socket = serverSocket.accept();

            // instancia novo cliente para tratar o socket
            Client client = Client.create(socket);

            // manipula o client criado
            ClientHandler clientHandler = new ClientHandler(client, manager);

            // inicia cliente atual
            clientHandler.start();
        }
    }


}
