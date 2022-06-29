package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.Messages.ServerSide.Error;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.*;
import it.polimi.ingsw.network.server.ClientConnection;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** This class represents the 'real' view to the Controller. It hides the implementation
 * of the network to the Controller-->Server Side
 * @author Matteo Luppi
 */
public class VirtualView implements View, Observer {

    private ClientConnection clientConnection;

    public VirtualView(ClientConnection clientConnection){
        this.clientConnection=clientConnection;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    @Override
    public void askNickName() {
        clientConnection.sendMessageToClient(new LoginReply(false,true));
    }

    @Override
    public void askNumPlayers() {
        clientConnection.sendMessageToClient(new NumPlayersRequest());
    }

    @Override
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
        clientConnection.sendMessageToClient(new AssistantSeedRequest(assistantSeedAvailable));
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        clientConnection.sendMessageToClient(new AssistantCardRequest(assistantCards));
    }

    @Override
    public void askMoveMotherNature(List<Island> islands, int maxSteps) {
        clientConnection.sendMessageToClient(new MotherNatureMoveRequest(islands,maxSteps));
    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {
        clientConnection.sendMessageToClient(new CloudTileRequest(cloudTiles));
    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {
        clientConnection.sendMessageToClient(new CharacterCardRequest(characterCards));
    }

    @Override
    public void askMoveStud() {
        clientConnection.sendMessageToClient(new MoveStud("It's time to move your students!"));
    }

    @Override
    public void showSchoolBoardPlayers(List<Player> players) {
        clientConnection.sendMessageToClient(new SchoolBoardPlayers(players));
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientConnection.sendMessageToClient(new Generic("SERVER",genericMessage));
    }

    @Override
    public void showError(String errorMessage) {
        clientConnection.sendMessageToClient(new Error(errorMessage));
    }

    @Override
    public void showLobby(List<Player> players, int numPlayers) {
        clientConnection.sendMessageToClient(new Lobby(players,numPlayers));
    }

    @Override
    public void showWinMessage(Player winner) {
        clientConnection.sendMessageToClient(new Win(winner));
    }

    @Override
    public void showLoseMessage(Player winner) {
        clientConnection.sendMessageToClient(new Lose(winner));
    }

    @Override
    public void showDisconnection(String nickName, String message) {
        clientConnection.sendMessageToClient(new Disconnection(nickName,message));
    }

    @Override
    public void showLoginInfo(String nickName, boolean nickNameOk, boolean connectionOk) {
        clientConnection.sendMessageToClient(new LoginReply(nickNameOk,connectionOk));
    }

    @Override
    public void showMatchInfo(ArrayList<Player> players, boolean experts, int numPlayers) {
        clientConnection.sendMessageToClient(new InfoMatch(players,experts,numPlayers));
    }

    @Override
    public void askMoveStudToIsland(Player player,List<Island> islands) {
        clientConnection.sendMessageToClient(new StudentToIslandRequest(player,islands));
    }


    @Override
    public void askMoveStudToDining(Player player) {
        clientConnection.sendMessageToClient(new StudentToDiningRequest(player));
    }

    @Override
    public void showIslands(List<Island> islands) {
        clientConnection.sendMessageToClient(new Islands(islands));
    }


    public void showCloudTiles(List<CloudTile> cloudTiles) {
        clientConnection.sendMessageToClient(new CloudTiles(cloudTiles));
    }

    @Override
    public void showGameBoard(ReducedGame reducedGame) {
        clientConnection.sendMessageToClient(new GameBoard(reducedGame));
    }

    @Override
    public void showStudents(Map<PawnColor,Integer> students){
        clientConnection.sendMessageToClient(new ShowCharacterCard(students));
    }

    @Override
    public void askExpertVariant() {
        clientConnection.sendMessageToClient(new ExpertVariantRequest());
    }

    @Override
    public void askIsland(List<Island> islands){
        clientConnection.sendMessageToClient(new IslandRequest(islands));
    }

    @Override
    public void askColor(Map<PawnColor,Integer> availableStudents){
        clientConnection.sendMessageToClient(new ColorRequest(availableStudents));
    }

    @Override
    public void askStudOrStop(Map<PawnColor,Integer> students){
        clientConnection.sendMessageToClient(new StudentOrStopRequest(students));
    }

    @Override
    public void updateFX(ReducedGame reducedGame) {
        clientConnection.sendMessageToClient(new UpdateFX(reducedGame));
    }

    @Override
    public void update(Message message) {
        clientConnection.sendMessageToClient(message);
    }


}
