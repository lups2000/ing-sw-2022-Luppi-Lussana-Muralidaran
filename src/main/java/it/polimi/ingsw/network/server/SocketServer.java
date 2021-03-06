package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * This class handles the connection with a new client
 */
public class SocketServer implements Runnable {

    private final Server server;
    private final int port;
    private ServerSocket serverSocket;

    public SocketServer(Server server,int port) {
        this.port=port;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Server.LOGGER.info(() -> "The socket of the server started on port " + port + ".");
        } catch (IOException e) {
            Server.LOGGER.severe("Server could not start!");
            return;
        }

        while (!Thread.currentThread().isInterrupted()) {
            try {
                //wait from a connection of the client
                Socket client = serverSocket.accept();

                //if data do not arrive from the client, SocketTimeoutException will be thrown
                client.setSoTimeout(6000);

                SocketClientConnection socketClientConnection = new SocketClientConnection(client,this);
                Thread thread = new Thread(socketClientConnection, "SocketServerThread:" + client.getInetAddress());
                thread.start();

            } catch (IOException e ) {
                Server.LOGGER.severe("Unfortunately the Connection has dropped!");
            }
        }
    }

    /**
     * Handles the addition of a new client
     *
     * @param nickname the nickname of the new client
     * @param clientConnection the ClientConnection of the new client
     */
    public void addClient(String nickname, ClientConnection clientConnection) {
        server.newClientManager(nickname, clientConnection);
    }

    /**
     * Forwards a received message from the client to the Server
     *
     * @param message the message to be forwarded
     */
    public void forwardsMessage(Message message) {
        server.forwardsMessage(message);
    }

    /**
     * Handles a client disconnection
     *
     * @param clientConnection the ClientConnection of the disconnecting client
     */
    public void onDisconnect(ClientConnection clientConnection) {
        server.disconnectionManager(clientConnection);
    }
}
