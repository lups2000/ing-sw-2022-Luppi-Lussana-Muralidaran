package it.polimi.ingsw.observer;

import java.util.Map;

/**
 * This is a custom implementation of an observer interface implemented by the view
 */
public interface Observer4View {
    /**
     * This method creates a new connection to the server
     * @param serverAddressAndPort map address-port
     */
    void createConnection(Map<String, String> serverAddressAndPort);

    /**
     * This method sends a message to the server to communicate the nickName
     * @param nickName to be sent
     */
    void sendNickname(String nickName);

    /**
     * This method sends a message to the server to communicate the number of players of the match
     * @param numPlayers the number of players
     */
    void sendNumPlayers(int numPlayers);

    /*
     * Handles a disconnection wanted by the user.
     * (e.g. a click on the back button into the GUI).

    void onDisconnection();*/
}
