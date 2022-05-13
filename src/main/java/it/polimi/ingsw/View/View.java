package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.util.List;

/**
 * This interface defines the methods that will be implemented in the CLI and in the GUI
 * This is the View according to the MVC pattern
 * @author Matteo Luppi
 */
public interface View {

    /**
     * Method to ask the nickName to the user
     */
    void askNickName();

    /**
     * Method to ask the number of player to the first player who joins the match
     */
    void askNumPlayers();

    /**
     * Method to ask the assistant seed to each player
     */
    void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable);

    void askAssistantCard(List<AssistantCard> assistantCards);

    void askMoveStudToDining(PawnColor pawnColor);

    void askMoveStudToIsland(PawnColor pawnColor,List<Island> islands);

    void askMoveMotherNature(List<Island> islands);

    void askChooseCloudTile(List<CloudTile> cloudTiles);

    void askExpertVariant();

    void askPlayCharacterCard(List<CharacterCard> characterCards);

    void showGenericMessage(String genericMessage);

    void showError(String error);

    void showLobby(List<Player> players,int numPlayers);

    void showVictoryMessage(Player winner);

    void showLoseMessage(Player looser);

    void showLoginPlayers(String nickName,boolean nickNameOk,boolean connectionOk);

}
