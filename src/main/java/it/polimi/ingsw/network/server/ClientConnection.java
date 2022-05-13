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
     * Method to verify if a client is connected or not
     * @return true/false
     */
    boolean isConnected();

    /**
     * Method to diconnects the client
     */
    void disconnect();

    /**
     * Method to send a mesage to the client
     * @param message the message to be sent.
     */
    void sendMessageToClient(Message message);
}