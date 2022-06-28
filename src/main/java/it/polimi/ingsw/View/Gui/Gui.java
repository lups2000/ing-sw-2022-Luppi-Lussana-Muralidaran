package it.polimi.ingsw.View.Gui;

import com.sun.source.tree.IfTree;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.Gui.Controllers.*;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.network.Messages.ServerSide.InfoMatch;
import it.polimi.ingsw.observer.Observable4View;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * class that implements the methods of View interface, used for GUI
 */
public class Gui extends Observable4View implements View {

    private Game game;
    private BoardController boardController;

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
        //empty method because we ask the expertVariant when we ask the number of players during the creation of the match
    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {
        CharacterCardController characterCardController = new CharacterCardController(characterCards);
        characterCardController.addAllObservers(observers);
        Platform.runLater(()->GuiMainController.nextPane(characterCardController,"CharacterCardSelector.fxml"));
    }

    @Override
    public void askMoveStud() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->GuiMainController.showAskMoveStud(observers));
    }

    @Override
    public void showSchoolBoardPlayers(List<Player> players) {

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->
        {
            this.boardController.displayEntireSchoolBoards(players);
        });
        this.boardController.setPlayers(players);
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
        Platform.runLater(()-> {
            GuiMainController.showAlert("End of the match!", "Congratulations, you're the winner!");
        });
    }

    @Override
    public void showLoseMessage(Player winner) {
        Platform.runLater(()-> {
            GuiMainController.showAlert("End of the match!", "Unfortunately you lost the match...");
        });
    }

    @Override
    public void showDisconnection(String nickName, String message) {
        Platform.runLater(()-> {
            GuiMainController.showAlert("Disconnection","You have been disconnected from the match");
            GuiMainController.nextPane(observers,"InitialScreen.fxml");
        });
    }

    @Override
    public void showLoginInfo(String nickName, boolean nickNameOk, boolean connectionOk) {

        if(nickNameOk && connectionOk ){
            Platform.runLater(()->{
                GuiMainController.showAlert("Generic","Nice to meet you "+nickName+", you are now connected!");
            });
        }
        else if(nickNameOk){
            Platform.runLater(()->{
                GuiMainController.showAlert("Error","We are sorry but the connection has been refused! Try later!");
                GuiMainController.nextPane(observers,"InitialScreen.fxml");
            });
        }
        else if(connectionOk){
            if(nickName==null){
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
        else{
            Platform.runLater(()->{
                GuiMainController.showAlert("Error","Sorry, error to reach the server!");
                GuiMainController.nextPane(observers,"InitialScreen.fxml");
            });
        }
    }

    @Override
    public void showMatchInfo(ArrayList<Player> players, boolean experts, int numPlayers) {
        InfoMatch infoMatch = new InfoMatch(players, experts, numPlayers);
        //da finire
    }

    @Override
    public void askMoveStudToIsland(Map<PawnColor, Integer> studentsWaiting, List<Island> islands) {

    }

    @Override
    public void askMoveStudToDining(Player player) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->{
            this.boardController.moveStudToDining(player);
        });
    }

    @Override
    public void askMoveMotherNature(List<Island> islands, int maxSteps) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->{
            this.boardController.moveMotherNature(islands,maxSteps);
        });
    }

    @Override
    public void showIslands(List<Island> islands) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->
        {
            this.boardController.displayIslands(islands);
        }
        );
    }

    @Override
    public void showGameBoard(ReducedGame reducedGame) {

        BoardController boardController = getBoardController();
        this.boardController=boardController;
        Platform.runLater(()->
        {
            boardController.initialDisplay(reducedGame);
        });
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

    @Override
    public void updateFX(ReducedGame reducedGame) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(()->showGameBoard(reducedGame));
    }

    private BoardController getBoardController(){
        BoardController boardController;
        try {
            boardController = (BoardController) GuiMainController.getCurrentController();
        } catch (ClassCastException e) {
            boardController = new BoardController();
            boardController.addAllObservers(observers);
            BoardController finalBoardController = boardController;
            Platform.runLater(() -> GuiMainController.nextPane(finalBoardController, "Board.fxml"));
        }
        this.boardController=boardController;
        return boardController;
    }
}