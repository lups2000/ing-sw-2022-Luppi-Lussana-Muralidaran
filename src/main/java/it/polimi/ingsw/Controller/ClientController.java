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
     * it forwards to the client the messages received from the server
     *
     * @param message is the message received from the server
     */
    @Override
    public void update(Message message) {
        switch(message.getMessageType()) {
            case REPLY_LOGIN:
                LoginReply loginReply = (LoginReply) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showLoginPlayers(nickname,loginReply.isNickAccepted(),loginReply.isConnAccepted());
                    }
                });
                //taskQueue.execute(() -> view.showLoginPlayers(this.nickname,loginReply.isNickAccepted(),loginReply.isConnAccepted()));
                break;

            case REQUEST_PLAYER_NUM:
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askNumPlayers();
                    }
                });
                //taskQueue.execute(view::askNumPlayers);
                break;

            case LOBBY:
                Lobby lobbyMessage = (Lobby) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showLobby(lobbyMessage.getPlayersLobby(), lobbyMessage.getNumMaxPlayersLobby());
                    }
                });
                //taskQueue.execute(() -> view.showLobby(lobbyMessage.getPlayersLobby(), lobbyMessage.getNumMaxPlayersLobby()));
                break;

            case REQUEST_EXPERT_VARIANT:
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askExpertVariant();
                    }
                });
                //taskQueue.execute(view::askExpertVariant);
                break;

            case REQUEST_ASSISTANT_SEED:
                AssistantSeedRequest assistantSeedRequest = (AssistantSeedRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askAssistantSeed(assistantSeedRequest.getAssistantSeedAvailable());
                    }
                });
                //taskQueue.execute(() -> view.askAssistantSeed(assistantSeedRequest.getAssistantSeedAvailable()));
                break;

            case REQUEST_ASSISTANT_CARD:
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askAssistantCard(assistantCardRequest.getAssistantCards());
                    }
                });
                //taskQueue.execute(() -> view.askAssistantCard(assistantCardRequest.getAssistantCards()));
                break;

            case REQUEST_MOVE_MOTHER_NATURE:
                MotherNatureMoveRequest motherNatureMoveRequest = (MotherNatureMoveRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askMoveMotherNature(motherNatureMoveRequest.getAvailableIslands());
                    }
                });
                //taskQueue.execute(() -> view.askMoveMotherNature(motherNatureMoveRequest.getIslands()));
                break;

            case ERROR:
                Error errorMessage = (Error) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showError(errorMessage.getMessageError());
                    }
                });
                break;

            case GENERIC_MESSAGE:
                Generic genericMessage = (Generic) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showGenericMessage(genericMessage.getMessage());
                    }
                });
                //taskQueue.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
                break;

            case WIN:
                Win winMessage = (Win) message;
                client.disconnect();
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showWinMessage(winMessage.getWinner());
                    }
                });
                //taskQueue.execute(() -> view.showWinMessage(winMessage.getWinner()));
                break;

            case LOSE:
                Lose loseMessage = (Lose) message;
                client.disconnect();
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.showLoseMessage(loseMessage.getLoser());
                    }
                });
                //taskQueue.execute(() -> view.showLoseMessage(loseMessage.getLoser()));
                break;

            //il PING non penso sia da mettere qui (?), devo controllare TODO

            case REQUEST_CLOUD_TILE:
                CloudTileRequest cloudTileRequest = (CloudTileRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askChooseCloudTile(cloudTileRequest.getCloudTiles());
                    }
                });
                //taskQueue.execute(() -> view.askChooseCloudTile(cloudTileRequest.getCloudTiles()));
                break;

            case REQUEST_MOVE_STUD_DINING:
                StudentToDiningRequest studentToDiningRequest = (StudentToDiningRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askMoveStudToDining(studentToDiningRequest.getPawnColor());
                    }
                });
                //taskQueue.execute(() -> view.askMoveStudToDining(studentToDiningRequest.getPawnColor()));
                break;

            case REQUEST_MOVE_STUD_ISLAND:
                StudentToIslandRequest studentToIslandRequest = (StudentToIslandRequest) message;
                taskQueue.execute(new Runnable() {
                    public void run() {
                        view.askMoveStudToIsland(studentToIslandRequest.getPawnColor(),studentToIslandRequest.getIslands());
                    }
                });
                //taskQueue.execute(() -> view.askMoveStudToIsland(studentToIslandRequest.getPawnColor(),studentToIslandRequest.getIslands()));
                break;
        }
    }

    @Override
    public void createConnection(Map<String, String> serverAddressAndPort) throws IOException {
        try {
            client = new Client(serverAddressAndPort.get("address"), Integer.parseInt(serverAddressAndPort.get("port")));
            client.addObserver(this);
            client.readMessage();
            client.activatePing(true);
            taskQueue.execute(view::askNickName);
        } catch (IOException e){
            taskQueue.execute(() -> view.showLoginPlayers(null,false,false));
        }
    }

    /**
     * Method to check that the ip address inserted by the client is valid
     * In order to be valid the ip needs to be composed by 4 numbers between 0 and 255, each separated by a dot
     *
     * @param ip the ip inserted by the client
     * @return true if the IP address is legit, false if not
     */
    public boolean okIpAddress(String ip){
        int counter = 0;
        for(int i=0;i<ip.length();i++){
            int k=0;
            int partialIp = 0;
            while(ip.charAt(i) != '.'){
                partialIp = partialIp + Character.getNumericValue(ip.charAt(i)) * 10^k;
                k++;
            }
            if(partialIp < 0 || partialIp > 255){
                return false;
            }
            else {
                counter++;
            }
        }
        return counter == 4;
    }

    /**
     * Method to check that the port number inserted by the client is valid
     * In order to be valid the port number must be a number between 1 and 65535
     *
     * @param port the port number inserted by the client
     * @return true if th port number is legit, false if not
     */
    public boolean okPortNumber(String port){
        try {
            int portNumber = Integer.parseInt(port);
            if (portNumber >= 1 && portNumber <= 65535) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e){
            return false;
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
