package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.Gui.Controllers.CreateMatchController;
import it.polimi.ingsw.View.Gui.Controllers.GuiMainController;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.observer.Observable4View;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class that implements the methods of View interface, used for GUI
 */
public class Gui extends Observable4View implements View {

    @Override
    public void askNickName() {
        Platform.runLater(() -> GuiMainController.nextPane(observers,"Nickname.fxml"));
    }

    @Override
    public void askNumPlayers() {

        CreateMatchController createMatchController = new CreateMatchController();
        createMatchController.addAllObservers(observers);
        createMatchController.setMinMaxPlayersNumber(2,3);
        Platform.runLater(()-> GuiMainController.nextPane(createMatchController,"MatchCreation.fxml"));
    }

    @Override
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {

    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {

    }

    @Override
    public void askExpertVariant() {
        //empty method because we ask the expertVariant when we ask the number of players
    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {

    }

    @Override
    public void askMoveStud() {

    }

    @Override
    public void showSchoolBoardPlayers(List<Player> players) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showError(String error) {
        Platform.runLater(() -> {
            GuiMainController.showAlert(error);
            GuiMainController.nextPane(observers, "InitialScreen.fxml");
        });
    }

    @Override
    public void showLobby(List<Player> players, int numPlayers) {

    }

    @Override
    public void showWinMessage(Player winner) {

    }

    @Override
    public void showLoseMessage(Player winner) {

    }

    @Override
    public void showDisconnection(String nickName, String message) {

    }

    @Override
    public void showLoginInfo(String nickName, boolean nickNameOk, boolean connectionOk) {

    }

    @Override
    public void showMatchInfo(ArrayList<Player> players, boolean experts, int numPlayers) {

    }

    @Override
    public void askMoveStudToIsland(Map<PawnColor, Integer> studentsWaiting, List<Island> islands) {

    }

    @Override
    public void askMoveStudToDining(SchoolBoard schoolBoard) {

    }

    @Override
    public void askMoveMotherNature(List<Island> islands, int maxSteps) {

    }

    @Override
    public void showIslands(List<Island> islands) {

    }

    @Override
    public void showGameBoard(List<Island> islands, List<CloudTile> cloudTiles, List<Player> players) {

    }

    @Override
    public void showCloudTiles(List<CloudTile> cloudTiles) {

    }

    @Override
    public void askIsland(List<Island> islands) {

    }

    @Override
    public void askColor(Map<PawnColor, Integer> availableStudents) {

    }

    @Override
    public void showStudents(Map<PawnColor, Integer> students) {

    }

    @Override
    public void askStudOrStop(Map<PawnColor, Integer> students) {

    }
}
