package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Socket server that handles all the new socket connection.
 */
public class SocketServer implements Runnable {
    private final Server server;
    private final int port = 12345;
    ServerSocket serverSocket;

    public SocketServer(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            //Server.LOGGER.info(() -> "Socket server started on port " + port + ".");
        } catch (IOException e) {
            //Server.LOGGER.severe("Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();

                client.setSoTimeout(5000);

                SocketClientConnection clientConnection = new SocketClientConnection(this, client);
                Thread thread = new Thread(clientConnection, "ss_handler" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                Server.LOGGER.severe("Connection dropped");
            }
        }
    }

    /**
     * Handles the addition of a new client.
     *
     * @param nickname      the nickname of the new client.
     * @param clientConnection the ClientConnection of the new client.
     */
    public void addClient(String nickname, ClientConnection clientConnection) {
        server.addClient(nickname, clientConnection);
    }

    /**
     * Forwards a received message from the client to the Server.
     *
     * @param message the message to be forwarded.
     */
    public void onMessageReceived(Message message) {
        server.onMessageReceived(message);
    }

    /**
     * Handles a client disconnection.
     *
     * @param clientConnection the ClientConnection of the disconnecting client.
     */
    public void onDisconnect(ClientConnection clientConnection) {
        server.onDisconnect(clientConnection);
    }
}
