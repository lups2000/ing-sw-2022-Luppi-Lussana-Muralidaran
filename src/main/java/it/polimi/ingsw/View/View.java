package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    void askChooseCloudTile(List<CloudTile> cloudTiles);

    void askExpertVariant();

    void askPlayCharacterCard(List<CharacterCard> characterCards);

    void askMoveStud();

    void showSchoolBoardPlayers(List<Player> players);

    void showGenericMessage(String genericMessage);

    void showError(String error);

    void showLobby(List<Player> players,int numPlayers);

    void showWinMessage(Player winner);

    void showLoseMessage(Player winner);

    void showDisconnection(String nickName,String message);

    void showLoginInfo(String nickName,boolean nickNameOk,boolean connectionOk);

    void showMatchInfo(ArrayList<Player> players,boolean experts,int numPlayers);

    void askMoveStudToIsland(Map<PawnColor,Integer> studentsWaiting,List<Island> islands);

    void askMoveStudToDining(SchoolBoard schoolBoard);

    void askMoveMotherNature(List<Island> islands,int maxSteps);

    void showIslands(List<Island> islands);

    void showGameBoard(List<Island> islands,List<CloudTile> cloudTiles,List<Player> players);

    void showCloudTiles(List<CloudTile> cloudTiles);

    void askIsland(List<Island> islands);

    void askColor(Map<PawnColor,Integer> availableStudents);

    void showStudents(Map<PawnColor,Integer> students);

    void askStudOrStop(Map<PawnColor,Integer> students);
}
