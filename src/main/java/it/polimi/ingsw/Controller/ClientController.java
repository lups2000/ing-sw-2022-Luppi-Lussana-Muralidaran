package it.polimi.ingsw.Controller;

import it.polimi.ingsw.View.View;
import it.polimi.ingsw.network.Messages.ClientSide.NumPlayersReply;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.Observer4View;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This class is part of the client side.
 * It is an interpreter between the network and a generic view (which in this case can be CLI or GUI).
 * It receives the messages, wraps/unwraps and pass them to the network/view.
 */
public class ClientController implements Observer4View, Observer {

    private final View view;

    private Client client;
    private String nickname;

    private final ExecutorService taskQueue;

    //public static final int UNDO_TIME = 5000;

    public ClientController(View view){
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }


    /**
     * it forwards the messages received via the server to the client
     *
     * @param message is the message received from the server
     */
    @Override
    public void update(Message message) {
        switch(message.getMessageType()) {
            case REPLY_LOGIN:
                LoginReply loginReply = (LoginReply) message;
                taskQueue.execute(() -> view.showLoginPlayers(this.nickname,loginReply.isNickAccepted(),loginReply.isConnAccepted()));
                break;

            case REQUEST_PLAYER_NUM:
                NumPlayersRequest numPlayersRequest = (NumPlayersRequest) message;
                taskQueue.execute(view::askNumPlayers);
                break;

            case LOBBY:
                Lobby lobbyMessage = (Lobby) message;
                taskQueue.execute(() -> view.showLobby(lobbyMessage.getPlayersLobby(), lobbyMessage.getNumMaxPlayersLobby()));
                break;

            case REQUEST_EXPERT_VARIANT:
                ExpertVariantRequest expertVariantRequest = (ExpertVariantRequest) message;
                taskQueue.execute(view::askExpertVariant);
                break;

            case REQUEST_ASSISTANT_SEED:
                AssistantSeedRequest assistantSeedRequest = (AssistantSeedRequest) message;
                taskQueue.execute(() -> view.askAssistantSeed(assistantSeedRequest.getAssistantSeedAvailable()));
                break;

            case REQUEST_ASSISTANT_CARD:
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                taskQueue.execute(() -> view.askAssistantCard(assistantCardRequest.getAssistantCards()));
                break;

            case REQUEST_MOVE_MOTHER_NATURE:
                MotherNatureMoveRequest motherNatureMoveRequest = (MotherNatureMoveRequest) message;
                taskQueue.execute(() -> view.askMoveMotherNature(motherNatureMoveRequest.getIslands()));
                break;

            case GENERIC_MESSAGE:
                Generic genericMessage = (Generic) message;
                taskQueue.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
                break;

            case WIN:
                Win winMessage = (Win) message;
                client.disconnect();
                taskQueue.execute(() -> view.showWinMessage(winMessage.getWinner()));
                break;

            case LOSE:
                Lose loseMessage = (Lose) message;
                client.disconnect();
                taskQueue.execute(() -> view.showLoseMessage(loseMessage.getLoser()));
                break;

            //il PING non penso sia da mettere qui (?), devo controllare TODO

            case REQUEST_CLOUD_TILE:
                CloudTileRequest cloudTileRequest = (CloudTileRequest) message;
                taskQueue.execute(() -> view.askChooseCloudTile(cloudTileRequest.getCloudTiles()));
                break;

            case REQUEST_MOVE_STUD_DINING:
                StudentToDiningRequest studentToDiningRequest = (StudentToDiningRequest) message;
                taskQueue.execute(() -> view.askMoveStudToDining(studentToDiningRequest.getPawnColor()));
                break;

            case REQUEST_MOVE_STUD_ISLAND:
                StudentToIslandRequest studentToIslandRequest = (StudentToIslandRequest) message;
                taskQueue.execute(() -> view.askMoveStudToIsland(studentToIslandRequest.getPawnColor(),studentToIslandRequest.getIslands()));
                break;
        }
    }

    @Override
    public void createConnection(Map<String, String> serverAddressAndPort) {

    }

    @Override
    public void sendNickname(String nickName) {

    }

    @Override
    public void sendNumPlayers(int numPlayers) {

    }
}
