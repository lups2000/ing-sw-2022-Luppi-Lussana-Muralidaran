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
    public void askMoveStud(List<PawnColor> pawnColors, List<Island> islands) {

    }

    @Override
    public void askMoveStudToDining(List<PawnColor> pawnColors) {
        clientConnection.sendMessageToClient(new StudentToDiningRequest(pawnColors));
    }

    @Override
    public void askMoveStudToIsland(List<Island> islands) {
        clientConnection.sendMessageToClient(new StudentToIslandRequest(islands));
    }

    @Override
    public void askMoveMotherNature(List<Island> islands) {
        clientConnection.sendMessageToClient(new MotherNatureMoveRequest(islands));
    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {
        clientConnection.sendMessageToClient(new CloudTileRequest(cloudTiles));
    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {

    }

    @Override
    public void showSchoolBoard(SchoolBoard schoolBoard) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientConnection.sendMessageToClient(new Generic(genericMessage));
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
    public void showLoseMessage(Player loser) {
        clientConnection.sendMessageToClient(new Lose(loser));
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
    public void askExpertVariant() {
        clientConnection.sendMessageToClient(new ExpertVariantRequest());
    }

    @Override
    public void update(Message message) {
        clientConnection.sendMessageToClient(message);
    }
}
