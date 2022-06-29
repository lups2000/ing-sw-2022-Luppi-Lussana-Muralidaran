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
    private final ExecutorService executorService;

    public ClientController(View view){
        this.view = view;
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * This method receives the message from the server, identifies the type of message and then forwards it to client
     *
     * @param message is the message received from the server
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()) {
            case REPLY_LOGIN -> {
                LoginReply loginReply = (LoginReply) message;
                executorService.execute(() -> view.showLoginInfo(nickname, loginReply.isNickAccepted(), loginReply.isConnAccepted()));
            }

            case REQUEST_PLAYER_NUM -> executorService.execute(view::askNumPlayers);


            case LOBBY -> {
                Lobby lobbyMessage = (Lobby) message;
                executorService.execute(() -> view.showLobby(lobbyMessage.getPlayersLobby(), lobbyMessage.getNumMaxPlayersLobby()));
            }

            case GAME_BOARD -> {
                GameBoard gameBoard=(GameBoard) message;
                executorService.execute(()->view.showGameBoard(gameBoard.getReducedGame()));
            }

            case SCHOOLBOARD -> {
                SchoolBoardPlayers schoolBoardPlayers =(SchoolBoardPlayers) message;
                executorService.execute(()->view.showSchoolBoardPlayers(schoolBoardPlayers.getPlayers()));
            }

            case REQUEST_EXPERT_VARIANT -> executorService.execute(view::askExpertVariant);

            case REQUEST_ASSISTANT_SEED -> {
                AssistantSeedRequest assistantSeedRequest = (AssistantSeedRequest) message;
                executorService.execute(() -> view.askAssistantSeed(assistantSeedRequest.getAssistantSeedAvailable()));
            }

            case REQUEST_ASSISTANT_CARD -> {
                AssistantCardRequest assistantCardRequest = (AssistantCardRequest) message;
                executorService.execute(() -> view.askAssistantCard(assistantCardRequest.getAssistantCards()));
            }

            case REQUEST_CHARACTER_CARD -> {
                CharacterCardRequest characterCardRequest=(CharacterCardRequest) message;
                executorService.execute(()->view.askPlayCharacterCard(characterCardRequest.getCharacterCards()));
            }

            case REQUEST_MOVE_MOTHER_NATURE -> {
                MotherNatureMoveRequest motherNatureMoveRequest = (MotherNatureMoveRequest) message;
                executorService.execute(() -> view.askMoveMotherNature(motherNatureMoveRequest.getIslands(), motherNatureMoveRequest.getMaxSteps()));
            }

            case REQUEST_STUDENT_OR_STOP -> {
                StudentOrStopRequest studentOrStopRequest = (StudentOrStopRequest) message;
                executorService.execute(() -> view.askStudOrStop(studentOrStopRequest.getStudents()));
            }

            case ERROR -> {
                Error errorMessage = (Error) message;
                executorService.execute(() -> view.showError(errorMessage.getMessageError()));
            }
            case GENERIC_MESSAGE -> {
                Generic genericMessage = (Generic) message;
                executorService.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
            }

            case MOVE_STUD -> {
                MoveStud moveStud=(MoveStud) message;
                executorService.execute(view::askMoveStud);
            }

            case WIN -> {
                Win winMessage = (Win) message;
                client.disconnect();
                executorService.execute(() -> view.showWinMessage(winMessage.getWinner()));
            }

            case LOSE -> {
                Lose loseMessage = (Lose) message;
                client.disconnect();
                executorService.execute(() -> view.showLoseMessage(loseMessage.getWinner()));
            }

            case REQUEST_ISLAND -> {
                IslandRequest islandRequest = (IslandRequest) message;
                executorService.execute(() -> view.askIsland(islandRequest.getIslands()));
            }

            case REQUEST_COLOR -> {
                ColorRequest colorRequest = (ColorRequest) message;
                executorService.execute(() -> view.askColor(colorRequest.getAvailableStudents()));
            }

            case REQUEST_CLOUD_TILE -> {
                CloudTileRequest cloudTileRequest = (CloudTileRequest) message;
                executorService.execute(() -> view.askChooseCloudTile(cloudTileRequest.getCloudTiles()));
            }

            case REQUEST_MOVE_STUD_DINING -> {
                StudentToDiningRequest studentToDiningRequest = (StudentToDiningRequest) message;
                executorService.execute(() -> view.askMoveStudToDining(studentToDiningRequest.getPlayer()));
            }

            case REQUEST_MOVE_STUD_ISLAND -> {
                StudentToIslandRequest studentToIslandRequest = (StudentToIslandRequest) message;
                executorService.execute(() -> view.askMoveStudToIsland(studentToIslandRequest.getStudentsWaiting(),studentToIslandRequest.getIslands()));
            }

            case INFO_MATCH -> {
                InfoMatch infoMatch = (InfoMatch) message;
                executorService.execute(() -> view.showMatchInfo(infoMatch.getPlayers(), infoMatch.isExperts(), infoMatch.getNumPlayers()));
            }

            case INFO_ISLANDS -> {
                Islands islandsMessage=(Islands) message;
                executorService.execute(()->view.showIslands(islandsMessage.getIslands()));
            }
            case DISCONNECTION -> {
                Disconnection disconnectionMessage=(Disconnection) message;
                executorService.execute(()->view.showDisconnection(disconnectionMessage.getNickName(), disconnectionMessage.getMessage()));
            }
            case UPDATEFX -> {
                UpdateFX updateFX =(UpdateFX) message;
                executorService.execute(()->view.updateFX(updateFX.getReducedGame()));
            }

        }
    }

    /**
     * Method that creates a connection with the IP Address and the port number given by the client
     *
     * @param serverAddressAndPort map address-port
     */
    @Override
    public void createConnection(Map<String, String> serverAddressAndPort) {
        try {
            client = new Client(serverAddressAndPort.get("address"), Integer.parseInt(serverAddressAndPort.get("port")));
            client.addObserver(this);
            client.readMessage();
            client.sendPingMessage(true);
            executorService.execute(view::askNickName);
        } catch (IOException e){
            executorService.execute(() -> view.showLoginInfo(null,false,false));
        }
    }

    /**
     * Method which checks if the given IP Address is valid
     * The IP Address is something like X.X.X.X, where every X is a number between 0 and 255
     *
     * @param ip the ip inserted by the client
     * @return true if the IP address is valid, false if it's not
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
     * Method which checks if the given port number is valid
     * The port number has to be a number between 1 and 65535
     *
     * @param port the port number inserted by the client
     * @return true if the port number is valid, false if it's not
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
            client.sendPingMessage(true);
            executorService.execute(view::askNickName);
        } catch (IOException e){
            executorService.execute(() -> view.showLoginInfo(null,false,false));
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
     * The first player who accesses the match has to select the number of players
     *
     * @param numPlayers the number of players
     */
    @Override
    public void sendNumPlayers(int numPlayers) {client.sendMessage(new NumPlayersReply(this.nickname,numPlayers));
    }

    /**
     * This method sends a message to the server to communicate which assistant card the player has chosen
     *
     * @param chosenCard the assistant card chosen
     */
    @Override
    public void sendAssistantCard(AssistantCard chosenCard) {
        client.sendMessage(new AssistantCardReply(this.nickname,chosenCard));
    }

    /**
     * This method sends a message to the server to communicate which character card the player has chosen
     *
     * @param idCharacterCard the chosen assistant card
     */
    @Override
    public void sendCharacterCard(Integer idCharacterCard) {
        client.sendMessage(new CharacterCardReply(this.nickname,idCharacterCard));
    }

    /**
     * This method sends a generic message to the server in order to communicate with it
     *
     * @param message the chosen assistant card
     */
    @Override
    public void sendGenericMessage(String message) {
        client.sendMessage(new Generic(this.nickname,message));
    }

    /**
     * This method sends a message to the server to communicate which assistant seed the player has chosen
     *
     * @param chosenSeed the chosen assistant seed
     */
    @Override
    public void sendAssistantSeed(AssistantSeed chosenSeed) {
        client.sendMessage(new AssistantSeedReply(this.nickname,chosenSeed));
    }

    /**
     * This method sends a message to the server to communicate which cloud tile he chose
     *
     * @param idCloudTile the chosen cloud tile
     */
    @Override
    public void sendCloudTile(int idCloudTile) {
        client.sendMessage(new CloudTileReply(this.nickname,idCloudTile));
    }

    /**
     * This method sends a message to the server to communicate if the first player wants to activate or not the experts variant
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
     * @param chosenSteps the chosen number of steps that mother nature has to take
     */
    @Override
    public void sendMoveMotherNature(int chosenSteps) {
        client.sendMessage(new MotherNatureMoveReply(this.nickname,chosenSteps));
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
     * @param islandIndex index of the island on which move the chosen student
     */
    @Override
    public void sendStudentToIsland(PawnColor chosenColor,int islandIndex) {
        client.sendMessage(new StudentToIslandReply(this.nickname,chosenColor,islandIndex));
    }

    /**
     * This method sends a message to the server to communicate which island the client has chosen
     *
     * @param islandIndex the index of the chosen island
     */
    @Override
    public void sendChosenIsland(int islandIndex) {
        client.sendMessage(new IslandReply(this.nickname,islandIndex));
    }

    /**
     * This method sends a message to the server to communicate which color the client has chosen
     *
     * @param chosenColor the color of the student to move
     */
    @Override
    public void sendChosenColor(PawnColor chosenColor) {
        client.sendMessage(new ColorReply(this.nickname,chosenColor));
    }

    /**
     * This method sends a message to the server to communicate which pawn color the client has chosen
     *
     * @param chosenColor the chosen color, null if the user decided to stop to switch students
     * @param stop a boolean to indicate if the user wants to stop switching students
     */
    @Override
    public void sendChosenColorOrStop(PawnColor chosenColor,boolean stop){
        client.sendMessage(new StudentOrStopReply(this.nickname,chosenColor,stop));
    }

}
