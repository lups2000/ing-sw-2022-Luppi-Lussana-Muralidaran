package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Implementation ClientConnection Interface
 */
public class SocketClientConnection implements ClientConnection, Runnable {

    private final Socket client;
    private final SocketServer socketServer;
    private boolean isConnected;
    private final Object inputLock;
    private final Object outputLock;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    public SocketClientConnection(Socket client,SocketServer socketServer) {
        this.socketServer = socketServer;
        this.client = client;
        this.isConnected = true;

        this.inputLock = new Object(); // serve?
        this.outputLock = new Object(); // serve?

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        }
        catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {

            handleClientConnection();

        } catch (IOException e) {
            Server.LOGGER.severe("The connection of the Client " + client.getInetAddress() + " has dropped.");
            disconnect();
            //e.printStackTrace();
        }
    }

    /**
     * Handles the connection of a new client and keep listening to the socket for new messages
     *
     * @throws IOException any of the usual Input/Output related exceptions.
     */
    private void handleClientConnection() throws IOException {

        Server.LOGGER.info("Client's address is: " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {

                //a che serve il lock?
                synchronized (inputLock) {

                    //readObject method is used to deserialize the message
                    //this message is received from the client-->method askNickName() in the CLI/GUI
                    Message message = (Message) input.readObject();

                    if (message != null && message.getMessageType() != MessageType.PING) {
                        if (message.getMessageType() == MessageType.REQUEST_LOGIN) {
                            socketServer.addClient(message.getNickName(), this);
                        }
                        else {
                            Server.LOGGER.info(() -> "Message Received: " + message);
                            socketServer.forwardsMessage(message);
                        }
                    }
                }
            }
        }
        catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream from client");
        }
        client.close();
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * This method disconnects the socket
     */
    @Override
    public void disconnect() {
        if (isConnected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            isConnected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }

     /**
     * This method sends a message to a client
      *
     * @param message the message to be sent
      */
    @Override
    public void sendMessageToClient(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Message Sent: " + message);
            }
        }
        catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }
}