package it.polimi.ingsw.observer;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.io.IOException;
import java.util.Map;

/**
 * This is a custom implementation of an observer interface implemented by the view
 */
public interface Observer4View {
    /**
     * This method creates a new connection to the server
     *
     * @param serverAddressAndPort map address-port
     */
    void createConnection(Map<String, String> serverAddressAndPort) throws IOException;

    void connectClientToServer(String ip, String port);

    /**
     * This method sends a message to the server to communicate the nickName
     *
     * @param nickName the nickname to be sent
     */
    void sendNickname(String nickName);

    /**
     * This method sends a message to the server to communicate the number of players of the match
     *
     * @param numPlayers the number of players
     */
    void sendNumPlayers(int numPlayers);

    /**
     * This method sends a message to the server to communicate which assistant card he chose
     *
     * @param chosenCard the assistant card chosen
     */
    void sendAssistantCard(AssistantCard chosenCard);

    public void sendCharacterCard(Integer idCharacterCard);

    void sendGenericMessage(String message);

    /**
     * This method sends a message to the server to communicate which assistant seed he chose
     *
     * @param chosenSeed the assistant seed chosen
     */
    void sendAssistantSeed(AssistantSeed chosenSeed);

    /**
     * This method sends a message to the server to communicate which cloud tile he chose
     *
     // @param chosenCloud the cloud tile chosen
     */
    void sendCloudTile(int idCloudTile);

    /**
     * This method sends a message to the server to communicate if the first player wants to activate or less the experts variant
     *
     * @param experts true if the first player he wants to activate the experts variant, false if not
     */
    void sendExpertVariant(boolean experts);

    /**
     * This method sends a message to the server to communicate on which island the client wants mother nature to stop
     *
     * @param chosenSteps the chosen number of steps that mother nature has to take
     */
    void sendMoveMotherNature(int chosenSteps);

    /**
     * This method sends a message to the server to communicate which student the client wants to move from his entrance room into his dining room
     *
     * @param chosenColor the color of the student to move
     */
    void sendStudentToDining(PawnColor chosenColor);

    /**
     * This method sends a message to the server to communicate which student the client wants to move from his entrance room to an island
     *
     * @param chosenColor the color of the student to move
     //* @param chosenIsland the island on which move the chosen student
     */
    void sendStudentToIsland(PawnColor chosenColor,int islandIndex);
    /*
     * Handles a disconnection wanted by the user.
     * (e.g. a click on the back button into the GUI).
     *
    void onDisconnection();*/
}
