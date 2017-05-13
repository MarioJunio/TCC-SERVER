package br.com.service;

import br.com.model.Client;
import br.com.model.Message;
import br.com.protocol.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Gerencia a conexão com o socket do cliente
 */
public class ClientHandler extends Thread {

    public static final String TAG = "ClientHandler";

    // Cliente conectado
    private Client client;

    // gerencia conexoes abertas
    private Manager manager;

    public ClientHandler(Client client, Manager manager) throws IOException {
        this.manager = manager;
        this.client = client;
        this.client.in = new DataInputStream(client.getSocket().getInputStream());
        this.client.out = new DataOutputStream(client.getSocket().getOutputStream());
    }

    @Override
    public synchronized void start() {
        super.start();

        // coloca cliente handler na lista de conexoes abertas
        manager.put(this);
    }

    @Override
    public void run() {

        // autentica usuario
        doAuth();

        // processa mensagens do usuario
        doProcess();

    }

    private void doPerform(String message) {

        if (message != null && !message.isEmpty()) {

            // transforma texto para mensagem
            Message m = Protocol.toMessage(message);

            if (m.getToAddress().equals(Protocol.ALL)) {

                String self = manager.get(m.getFromAddress()).getClient().getUsername();

                Collection<ClientHandler> clientHandlers = manager.allClients();

                // envia para todos os clientes menos o remetente
                for (ClientHandler receiver : clientHandlers) {

                    if (!receiver.key().equals(m.getFromAddress())) {

                        Message toSubmit = new Message(self, m.getToAddress(), m.getMessage());

                        try {
                            receiver.submitMessage(toSubmit.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            } else {

                // cliente que ira receber a mensagem
                ClientHandler receiver = manager.get(m.getToAddress());

                if (receiver != null) {

                    // envia mensagem para o destinatario
                    try {
                        receiver.submitMessage(m.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else
                    Log.d(TAG, "Destinatario: " + m.getToAddress() + " nao encontrado !");
            }
        }
    }

    private void doProcess() {

        try {

            // processa mensagens de entrada desse cliente
            while (true) {

                Log.d(TAG, client.getUsername() + ": Aguardando mensagens...");

                // processa mensagem do cliente
                doPerform(readMessage());
            }
        } catch (Exception e) {
            Log.d(TAG, client.getUsername() + " " + e.getCause());
            e.printStackTrace();
        }

    }

    private void doAuth() {

        try {

            // lê entrada da rede
            String username = readMessage();

            // checa se usuario é valido
            if (username == null || username.isEmpty()) {
                client.getSocket().close();
                return;
            }

            // nome do usuario
            client.setUsername(username);

            manager.usuariosLogados();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitMessage(String message) throws IOException {
        client.out.writeUTF(message);
        client.out.flush();
    }

    private String readMessage() throws Exception {

        try {
            return client.in.readUTF();
        } catch (IOException ioe) {
            manager.remove(this);
            throw new Exception("Disconnect");
        }
    }

    public String key() {
        return client.getSocket().getRemoteSocketAddress().toString();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
