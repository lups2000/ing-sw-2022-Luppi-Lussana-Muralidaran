package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.Messages.Message;


/**
 * Interface to handle the relationship with the clients
 */
public interface ClientConnection {

    /**
     * Method to send a message to the client
     * @param message the message to be sent.
     */
    void sendMessageToClient(Message message);

    /**
     * Method to verify if a client is currently connected or not
     * @return true or false
     */
    boolean isConnected();

    /**
     * Method to disconnect the client
     */
    void disconnect();

}