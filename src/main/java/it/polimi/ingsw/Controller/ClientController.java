package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.ServerSide.*;
import it.polimi.ingsw.network.Messages.ServerSide.Error;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.Observer4View;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * This controller is Client Side
 * This class handles all type of messages:
 * - it forwards to the server the ones sent by the client side
 * - it receives the ones arriving from the server side and it forwards them to the client's view
 */
public class ClientController implements Observer4View, Observer {

    private final View view;

    private Client client;
    private String nickname;

    private final ExecutorService taskQueue;

    public static final int UNDO_TIME = 5000;

    public ClientController(View view){
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }


    /**
     * it forwards to the client the messages received from the server
     *
     * @param message is the message received from the server
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()) {
            case REPLY_LOGIN -> {
                LoginReply loginReply = (LoginReply) message;
                taskQueue.execute(() -> view.showLoginInfo(nickname, loginReply.isNickAccepted(), loginReply.isConnAccepted()));
            }

            case REQUEST_PLAYER_NUM -> taskQueue.execute(view::askNumPlayers);


            case LOBBY -> {
                Lobby lobbyMessage = (Lobby) message;
                taskQueue.execute(() -> view.showLobby(lobbyMessage.getPlayersLobby(), lobbyMessage.getNumMaxPlayersLobby()));
            }

            case REQUEST_EXPERT_VARIANT -> taskQueue.execute(view::askExpertVariant);

            case REQUEST_ASSISTANT_SEED -> {
                AssistantSeedRequest assistantSeedRequest = (AssistantSeedRequest) message;
                taskQueue.execute(() -> view.askAssistantSeed(assistantSeedRequest.getAssistantSeedAvailable()));
            }

            case REQUEST_ASSISTANT_CARD -> {
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                taskQueue.execute(() -> view.askAssistantCard(assistantCardRequest.getAssistantCards()));
            }

            case REQUEST_MOVE_MOTHER_NATURE -> {
                MotherNatureMoveRequest motherNatureMoveRequest = (MotherNatureMoveRequest) message;
                taskQueue.execute(() -> view.askMoveMotherNature(motherNatureMoveRequest.getAvailableIslands()));
            }

            case ERROR -> {
                Error errorMessage = (Error) message;
                taskQueue.execute(() -> view.showError(errorMessage.getMessageError()));
            }
            case GENERIC_MESSAGE -> {
                Generic genericMessage = (Generic) message;
                taskQueue.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
            }

            case WIN -> {
                Win winMessage = (Win) message;
                client.disconnect();
                taskQueue.execute(() -> view.showWinMessage(winMessage.getWinner()));
            }

            case LOSE -> {
                Lose loseMessage = (Lose) message;
                client.disconnect();
                taskQueue.execute(() -> view.showLoseMessage(loseMessage.getLoser()));
            }

            //il PING non penso sia da mettere qui (?), devo controllare TODO

            case REQUEST_CLOUD_TILE -> {
                CloudTileRequest cloudTileRequest = (CloudTileRequest) message;
                taskQueue.execute(() -> view.askChooseCloudTile(cloudTileRequest.getCloudTiles()));
            }

            case REQUEST_MOVE_STUD_DINING -> {
                StudentToDiningRequest studentToDiningRequest = (StudentToDiningRequest) message;
                taskQueue.execute(() -> view.askMoveStudToDining(studentToDiningRequest.getPawnColors()));
            }

            case REQUEST_MOVE_STUD_ISLAND -> {
                StudentToIslandRequest studentToIslandRequest = (StudentToIslandRequest) message;
                taskQueue.execute(() -> view.askMoveStudToIsland(studentToIslandRequest.getIslands()));
            }

            case INFO_MATCH -> {
                InfoMatch infoMatch = (InfoMatch) message;
                taskQueue.execute(() -> view.showMatchInfo(infoMatch.getPlayers(), infoMatch.isExperts(), infoMatch.getNumPlayers()));
            }
        }
    }

    /**
     * Method to create a connection with the ip address and the port number communicated by the client
     *
     * @param serverAddressAndPort map address-port
     */
    @Override
    public void createConnection(Map<String, String> serverAddressAndPort) {
        try {
            client = new Client(serverAddressAndPort.get("address"), Integer.parseInt(serverAddressAndPort.get("port")));
            client.addObserver(this);
            client.readMessage();
            //client.activatePing(true);
            taskQueue.execute(view::askNickName);
        } catch (IOException e){
            taskQueue.execute(() -> view.showLoginInfo(null,false,false));
        }
    }

    /**
     * Method to check that the ip address inserted by the client is valid
     * In order to be valid the ip needs to be composed by 4 numbers between 0 and 255, each separated by a dot
     *
     * @param ip the ip inserted by the client
     * @return true if the IP address is legit, false if not
     */
    public static boolean okIpAddress(String ip){
        String[] subStrings=ip.split("\\.");

        if(subStrings.length==4){
            for(int i=0;i<4;i++){
                if(Integer.parseInt(subStrings[i])<0 || Integer.parseInt(subStrings[i])>255){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Method to check that the port number inserted by the client is valid
     * In order to be valid the port number must be a number between 1 and 65535
     *
     * @param port the port number inserted by the client
     * @return true if th port number is legit, false if not
     */
    public static boolean okPortNumber(String port){
        try {
            int portNumber = Integer.parseInt(port);
            return portNumber >= 1 && portNumber <= 65535;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Method to create the client's socket and connect it to the server, if the ip and port matches with the server's ones
     * The parameters ip and port are already previously checked
     *
     * @param ip the ip address
     * @param port the port number (to be converted in an Integer)
     */
    @Override
    public void connectClientToServer(String ip, String port){
        try {
            client = new Client(ip, Integer.parseInt(port)); //no NumberFormatException checks needed because we already did them
            client.addObserver(this);
            client.readMessage();
            //client.activatePing(true);
            taskQueue.execute(view::askNickName);
        } catch (IOException e){
            taskQueue.execute(() -> view.showLoginInfo(null,false,false));
        }
    }

    /**
     * The client sends to the server his nickname
     * Here it's also firstly stored in this class the attribute nickname
     *
     * @param nickName the nickname to be sent
     */
    @Override
    public void sendNickname(String nickName) {
        this.nickname = nickName;
        client.sendMessage(new LoginRequest(this.nickname));
    }

    /**
     * The first client sends to the server the chosen number of players
     *
     * @param numPlayers the number of players
     */
    @Override
    public void sendNumPlayers(int numPlayers) {
        client.sendMessage(new NumPlayersReply(this.nickname,numPlayers));
    }

    /**
     * This method sends a message to the server to communicate which assistant card he chose
     *
     * @param chosenCard the assistant card chosen
     */
    @Override
    public void sendAssistantCard(AssistantCard chosenCard) {
        client.sendMessage(new AssistantCardReply(this.nickname,chosenCard));
    }

    /**
     * This method sends a message to the server to communicate which assistant seed he chose
     *
     * @param chosenSeed the assistant seed chosen
     */
    @Override
    public void sendAssistantSeed(AssistantSeed chosenSeed) {
        client.sendMessage(new AssistantSeedReply(this.nickname,chosenSeed));
    }

    /**
     * This method sends a message to the server to communicate which cloud tile he chose
     *
     * @param chosenCloud the cloud tile chosen
     */
    @Override
    public void sendCloudTile(CloudTile chosenCloud) {
        client.sendMessage(new CloudTileReply(this.nickname,chosenCloud));
    }

    /**
     * This method sends a message to the server to communicate if the first player wants to activate or less the experts variant
     *
     * @param experts true if the first player he wants to activate the experts variant, false if not
     */
    @Override
    public void sendExpertVariant(boolean experts) {
        client.sendMessage(new ExpertVariantReply(this.nickname,experts));
    }

    /**
     * This method sends a message to the server to communicate on which island the client wants mother nature to stop
     *
     * @param chosenIsland the island on which the client wants mother nature to stop
     */
    @Override
    public void sendMoveMotherNature(Island chosenIsland) {
        client.sendMessage(new MotherNatureMoveReply(this.nickname,chosenIsland));
    }

    /**
     * This method sends a message to the server to communicate which student the client wants to move from his entrance room into his dining room
     *
     * @param chosenColor the color of the student to move
     */
    @Override
    public void sendStudentToDining(PawnColor chosenColor) {
        client.sendMessage(new StudentToDiningReply(this.nickname,chosenColor));
    }

    /**
     * This method sends a message to the server to communicate which student the client wants to move from his entrance room to an island
     *
     * @param chosenColor the color of the student to move
     * @param chosenIsland the island on which move the chosen student
     */
    @Override
    public void sendStudentToIsland(PawnColor chosenColor, Island chosenIsland) {
        client.sendMessage(new StudentToIslandReply(this.nickname,chosenColor,chosenIsland));
    }
}
