package br.com.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MarioJ on 20/03/16.
 */
public class Manager {

    private static final String TAG = "Manager";

    private Map<String, ClientHandler> clients;

    public Manager() {
        clients = new HashMap<String, ClientHandler>();
    }

    public void put(ClientHandler handler) {
        clients.put(handler.key(), handler);
    }

    public void remove(ClientHandler handler) {

        // interrompe a execuao da thread
        handler.interrupt();

        // remove handler da lista
        clients.remove(handler.key());
    }

    public ClientHandler get(String toAddress) {
        return clients.get(toAddress);
    }

    public void usuariosLogados() {

        for (Map.Entry<String, ClientHandler> entry : clients.entrySet())
            Log.d(TAG, String.format("%s - %s", entry.getKey(), entry.getValue().getClient().getUsername()));

    }

    public Collection<ClientHandler> allClients() {
        return clients.values();
    }
}
