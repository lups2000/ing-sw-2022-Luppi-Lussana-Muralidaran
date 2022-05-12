package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.observer.Observer;

/*
public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);

    AssistantSeed getChosenWizard();
}
*/


/**
 * Interface to handle clients. Every type of connection must implement this interface.
 */
public interface ClientConnection {

    /**
     * Returns the connection status.
     *
     * @return {@code true} if the client is still connected and reachable, {@code false} otherwise.
     */
    boolean isConnected();

    /**
     * Disconnects from the client.
     */
    void disconnect();

    /**
     * Sends a message to the client.
     *
     * @param message the message to be sent.
     */
    void sendMessage(Message message);
}