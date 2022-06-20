package it.polimi.ingsw.View.Gui;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.Gui.Controllers.AssistantCardController;
import it.polimi.ingsw.View.Gui.Controllers.AssistantSeedController;
import it.polimi.ingsw.View.Gui.Controllers.BoardController;
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
        Platform.runLater(() -> GuiMainController.nextPane(observers,"NickName.fxml"));
    }

    @Override
    public void askNumPlayers() {
        Platform.runLater(()-> GuiMainController.nextPane(observers,"MatchCreation.fxml"));
    }

    @Override
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
        AssistantSeedController assistantSeedController = new AssistantSeedController();
        assistantSeedController.addAllObservers(observers);
        assistantSeedController.setAssistantSeedsAvailable(assistantSeedAvailable);
        Platform.runLater(()->GuiMainController.nextPane(assistantSeedController,"DeckSeedSelector.fxml"));
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        AssistantCardController assistantCardController = new AssistantCardController();
        assistantCardController.addAllObservers(observers);
        assistantCardController.setAssistantCardsAvailable(assistantCards);
        Platform.runLater(()->GuiMainController.nextPane(assistantCardController,"AssistantCardSelector.fxml"));
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
        Platform.runLater(()->GuiMainController.showAlert("Generic",genericMessage));
    }

    @Override
    public void showError(String error) {
        Platform.runLater(() -> {
            GuiMainController.showAlert("Error",error);
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
        if(!nickNameOk || !connectionOk){
            if(nickNameOk){
                Platform.runLater(()->{
                    GuiMainController.showAlert("Error","Sorry, error to reach the server!");
                    GuiMainController.nextPane(observers,"InitialScreen.fxml");
                });
            }
            else{
                if(nickName==null || nickName.isEmpty()){
                    Platform.runLater(()->{
                        GuiMainController.showAlert("Error","Missing Information!");
                        GuiMainController.nextPane(observers,"NickName.fxml");
                    });
                }
                else{
                    Platform.runLater(()->{
                        GuiMainController.showAlert("Error","Sorry, nickName already taken!");
                        GuiMainController.nextPane(observers,"NickName.fxml");
                    });
                }
            }
        }
        else{
            Platform.runLater(()->{
                GuiMainController.showAlert("Generic","Nice to meet you "+nickName+", now you are connected!");
            });
        }
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
    public void showGameBoard(Game game,List<Island> islands, List<CloudTile> cloudTiles, List<Player> players) {
        BoardController boardController = new BoardController();
        boardController.addAllObservers(observers);
        boardController.setCharacterCards(game.getCharacterCards());
        boardController.setAssistantCards(game.getCurrentHand().values().stream().toList());
        Platform.runLater(()->GuiMainController.nextPane(boardController,"Board.fxml"));
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
