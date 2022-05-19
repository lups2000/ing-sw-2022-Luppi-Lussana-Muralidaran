package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.util.ArrayList;
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

    void askMoveStud (List<PawnColor> pawnColors, List<Island> islands, SchoolBoard schoolBoard) throws TooManyPawnsPresent, NoPawnPresentException;

    int askMoveStudToDining(List<PawnColor> pawnColors, SchoolBoard schoolBoard, int studentsToMove) throws TooManyPawnsPresent;

    void askMoveStudToIsland(List<Island> islands, SchoolBoard schoolBoard, int studentsToMove) throws NoPawnPresentException;

    void askMoveMotherNature(List<Island> islands);

    void askChooseCloudTile(List<CloudTile> cloudTiles);

    void askExpertVariant();

    void askPlayCharacterCard(List<CharacterCard> characterCards);

    void showSchoolBoard(SchoolBoard schoolBoard);

    void showGenericMessage(String genericMessage);

    void showError(String error);

    void showLobby(List<Player> players,int numPlayers);

    void showWinMessage(Player winner);

    void showLoseMessage(Player looser);

    void showLoginInfo(String nickName,boolean nickNameOk,boolean connectionOk);

    void showMatchInfo(ArrayList<Player> players,boolean experts,int numPlayers);

    void askMoveStudToIsland(List<Island> islands);

    void askMoveStudToDining(List<PawnColor> pawnColors);
}
