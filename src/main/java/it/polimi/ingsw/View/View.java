package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the methods that will be implemented in the CLI and in the GUI
 * This is the View according to the MVC pattern
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
     * Method to ask a player which assistant seed he wants to pick
     *
     * @param assistantSeedAvailable all the available assistant seeds
     */
    void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable);

    /**
     * Method to ask a player which of his assistant cards he wants to play
     *
     * @param assistantCards all the available assistant cards of the player
     */
    void askAssistantCard(List<AssistantCard> assistantCards);

    /**
     * Method to ask a player which cloud tile he wants to pick
     *
     * @param cloudTiles the cloud tiles of the game
     */
    void askChooseCloudTile(List<CloudTile> cloudTiles);

    /**
     * Method to ask the first player if he wants to activate the experts variant or not
     */
    void askExpertVariant();

    /**
     * Method to ask a player to activate a character card
     *
     * @param characterCards the character cards of the game
     */
    void askPlayCharacterCard(List<CharacterCard> characterCards);

    /**
     * Method to ask a user to move a student from his entrance, the user can choose between moving it to his dining room or to an island
     */
    void askMoveStud();

    /**
     * Method to show the school board of some (or all) players
     *
     * @param players the players whose school board will be shown
     */
    void showSchoolBoardPlayers(List<Player> players);

    /**
     * Method to show a generic textual message
     *
     * @param genericMessage a generic textual message
     */
    void showGenericMessage(String genericMessage);

    /**
     * Method to show an error message
     *
     * @param error the textual message of the error
     */
    void showError(String error);

    /**
     * Method to show which and how many students are actually connected to the lobby
     * it is shown in the login phase while waiting that all the required players log in
     *
     * @param players the players actually connected
     * @param numPlayers the required number of players
     */
    void showLobby(List<Player> players,int numPlayers);

    /**
     * Method to show at the end of the match to the player who won
     *
     * @param winner the winning player of the match
     */
    void showWinMessage(Player winner);

    /**
     * Method to show at the end of the match to all the players who lost
     *
     * @param winner the winning player of the match
     */
    void showLoseMessage(Player winner);

    /**
     * Method to show a message of disconnection to the disconnected player
     *
     * @param nickName the player's nickname
     * @param message the textual message to be shown
     */
    void showDisconnection(String nickName,String message);

    /**
     * Method to show a welcome message to a player who just logged in
     *
     * @param nickName the player's nickname
     * @param nickNameOk if the chosen nickname is allowed or not
     * @param connectionOk if the connection was successful or not
     */
    void showLoginInfo(String nickName,boolean nickNameOk,boolean connectionOk);

    /**
     * Method to show the initial info of the game, as soon as it starts
     *
     * @param players all the players connected
     * @param experts a boolean that indicates if the experts variant has been activated or not
     * @param numPlayers the number of players of the game
     */
    void showMatchInfo(ArrayList<Player> players,boolean experts,int numPlayers);

    /**
     * Method to ask the user which student he wants to move from his entrance to an island
     *
     * @param islands the islands of the game that can be chosen
     */
    void askMoveStudToIsland(Player player,List<Island> islands);

    /**
     * Method to ask the user which student he wants to move from his entrance to his dining room
     *
     * @param player the player
     */
    void askMoveStudToDining(Player player);

    /**
     * Method to ask a player where he wants to move mother nature
     *
     * @param islands the islands of the game
     * @param maxSteps the maximum amount of steps that mother nature can do, according to the assistant card played
     */
    void askMoveMotherNature(List<Island> islands,int maxSteps);

    /**
     * Method to show all the islands of the game
     *
     * @param islands the islands to be shown
     */
    void showIslands(List<Island> islands);

    /**
     * Method to show the current game situation
     *
     * @param reducedGame has the information about the current situation
     */
    void showGameBoard(ReducedGame reducedGame);

    /**
     * Method to show the cloud tiles of the game
     *
     * @param cloudTiles the cloud tiles to be shown
     */
    void showCloudTiles(List<CloudTile> cloudTiles);

    /**
     * Method to ask a player to choose an island
     *
     * @param islands the islands that can be chosen
     */
    void askIsland(List<Island> islands);

    /**
     * Method to ask a player to choose a student
     *
     * @param availableStudents the available students that can be chosen
     */
    void askColor(Map<PawnColor,Integer> availableStudents);

    /**
     * Method to show all the students passed as a parameter
     *
     * @param students all the students to show
     */
    void showStudents(Map<PawnColor,Integer> students);

    /**
     * Method to ask a student, with the possibility to answer stop
     * it is used for only some character cards
     *
     * @param availableStudents all the available students that can be chosen
     */
    void askStudOrStop(Map<PawnColor,Integer> availableStudents);

    /**
     * Updates FX
     *
     * @param reducedGame has the information about the current situation
     */
    void updateFX(ReducedGame reducedGame);
}
