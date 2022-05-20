package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PlayerStatus;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.server.Server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the main controller
 * It receives messages from the server, reads them and responds to them after checks executed by all the controllers
 */
public class MainController {

    private final Game game;
    private transient Map<String, VirtualView> virtualViewsMap;
    private final MessageController messageController;
    private TurnController turnController;
    //private GameState gameState;  //c'è già in game del model
    private int maxNumPlayers;
    private boolean expertVariant;

    public MainController(){
        this.game = new Game();
        this.virtualViewsMap = Collections.synchronizedMap(new HashMap<>());
        this.messageController = new MessageController(this,virtualViewsMap);
        //this.gameState = GameState.LOGGING;
    }

    public Game getGame() {
        return game;
    }

    public TurnController getTurnController() {
        return turnController;
    }


    public MessageController getMessageController() {return messageController;}

    //public void setGameState(GameState gameState) {this.gameState = gameState;}

    /**
     * It receives a message from the server, who previously received it from the client
     *
     * @param message the message received from the server. The message is related to a particular client
     */
    public void messageFromServer(Message message) {

        VirtualView virtualView = virtualViewsMap.get(message.getNickName());

        switch (game.getStatus()) {

            case LOGGING:
                messageWhileLogging(message);
                break;

            case PLAYING:
                messageWhilePlaying(message);
                break;

            case ENDED:
                break;
        }
    }

    //macchina a stati qui


    /**
     * Messages received while still creating the game and waiting for all the players to log
     *
     * @param message the message received
     */
    private void messageWhileLogging(Message message) {

        switch(message.getMessageType()){

            case REPLY_PLAYER_NUM -> {
                if(messageController.checkNumPlayers(message)){ //message format ok
                    NumPlayersReply numPlayersReply = (NumPlayersReply) message;
                    maxNumPlayers = numPlayersReply.getNumPlayers(); //save the max number of players
                    game.setMaxNumPlayers(maxNumPlayers); //brutto ma se no non posso aggiungere i player prima di fare initGame
                    broadcastingMessage("Waiting for players...");        //questo mi mandava un messaggio generic null
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_EXPERT_VARIANT -> {
                if(messageController.checkExpertVariant(message)){ //message format ok
                    ExpertVariantReply expertVariantReply = (ExpertVariantReply) message;
                    expertVariant=expertVariantReply.isExpertVariant();
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_ASSISTANT_SEED -> {
                if(messageController.checkAssistantSeed(message)){ //message format ok
                    AssistantSeedReply assistantSeedReply=(AssistantSeedReply) message;
                    game.getPlayerByNickName(message.getNickName()).chooseDeck(assistantSeedReply.getAssistantSeed());
                    game.getSeedsAvailable().remove(assistantSeedReply.getAssistantSeed()); //removing the seed chosen from the list
                }
                else{
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }
            default ->             Server.LOGGER.warning("Wrong message received from client.");
        }
    }

    /**
     * Messages received while the game is currently playing
     *
     * @param message the message received
     */
    private void messageWhilePlaying(Message message){

        switch(message.getMessageType()){
            case REPLY_ASSISTANT_CARD -> {
                if(messageController.checkAssistantCard(message)){
                    AssistantCardReply assistantCardReply = (AssistantCardReply) message;
                    game.getPlayerByNickName(assistantCardReply.getNickName()).pickAssistantCard(assistantCardReply.getAssistantCard());
                    //bisogna far vedere l'assistente scelto anche a tutti gli altri
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_CLOUD_TILE -> {
                if(messageController.checkCloudTile(message)){
                    CloudTileReply cloudTileReply = (CloudTileReply) message;
                    try {
                        game.getPlayerByNickName(cloudTileReply.getNickName()).pickCloudTile(cloudTileReply.getCloudTile());
                    } catch (TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_MOTHER_NATURE -> {
                if(messageController.checkMotherNature(message)){
                    MotherNatureMoveReply motherNatureMoveReply = (MotherNatureMoveReply) message;
                    try {
                        game.moveMotherNature(motherNatureMoveReply.getIsland());
                    } catch (TooManyTowersException | NoTowersException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_STUD_DINING -> {
                if(messageController.checkStudentToDining(message)){
                    StudentToDiningReply studentToDiningReply = (StudentToDiningReply) message;
                    try {
                        game.getPlayerByNickName(studentToDiningReply.getNickName()).getSchoolBoard().moveStudToDining(studentToDiningReply.getPawnColor());
                    } catch (NoPawnPresentException | TooManyPawnsPresent e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_STUD_ISLAND -> {
                if(messageController.checkStudentToIsland(message)){
                    StudentToIslandReply studentToIslandReply = (StudentToIslandReply) message;
                    try {
                        game.getPlayerByNickName(studentToIslandReply.getNickName()).getSchoolBoard().moveStudToIsland(studentToIslandReply.getPawnColor(), studentToIslandReply.getIsland());
                    } catch (NoPawnPresentException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }
            default ->             Server.LOGGER.warning("Wrong message received from client.");
        }
    }


    /**
     * Method to handle the login to the game of a new client
     * We create his virtual view, we register the player into the model game??? non qui in realta
     * If it is the first player we also have to ask him the number of players and if he wants to play with the experts variant
     * To all the players we anyway also ask his chosen assistant seed
     *
     * @param nickname the client's nickname
     * @param virtualView the virtual view associated to the client
     */
    public void loginToTheGame(String nickname, VirtualView virtualView){   //viene invocato dalla classe Server quando si aggiunge un nuovo client, solo se non si è già raggiunto il num di players

        if(virtualViewsMap.size() == 0){    //it means it is the first player ever to connect

            virtualViewsMap.put(nickname,virtualView);
            //        game.addObserver(virtualView);
            //        game.getBoard().addObserver(virtualView);
            virtualView.showLoginInfo("SERVER",true,true);
            game.addPlayer(nickname);

            virtualView.askNumPlayers();
            virtualView.askExpertVariant();
            virtualView.askAssistantSeed(game.getSeedsAvailable());
        }
        else if(virtualViewsMap.size() < game.getMaxNumPlayers()){
            virtualViewsMap.put(nickname,virtualView);
            //        game.addObserver(virtualView);
            //        game.getBoard().addObserver(virtualView);
            game.addPlayer(nickname);

            virtualView.showLoginInfo("SERVER",true,true);
            virtualView.askAssistantSeed(game.getSeedsAvailable());

            if(game.getPlayers().size() == game.getMaxNumPlayers()){    //all the required players logged
                //the match can start
                startMatch(maxNumPlayers,expertVariant);
                //questo metodo setta gia GameState==PLAYING-->per mostrare tutto lo farei in startMatch
                //o forse direttamente PLAYING, però c'è da mostrare a tutti i player i giocatori registrati nella lobby
                // e il risultato dell'inizializzazione (scelta dei seed, tavolo da gioco imbandito dopo initGame() del model)
            }
        }
        else{
            virtualView.showLoginInfo("SERVER",true,false);
        }
    }

    /**
     * when all is ready we can start the game, initializing the model with all the previously asked initial settings
     *
     * @param maxNumPlayers the chosen number of players
     * @param experts a boolean corresponding to the choice about activating or less the experts variant
     */
    private void startMatch(int maxNumPlayers,boolean experts){

        game.initGame(maxNumPlayers,experts);
        broadcastingMessage("Match is starting... All players are connected... ");

        //poi qua forse c'è da mostrare tramite la cli le 12 isole,le scholboards e le clouds -->matchInfo

        game.changeStatus(GameState.PLAYING); //gameStatus==PLAYING set in the model
        this.turnController = new TurnController(game,virtualViewsMap);
        Player firstPlayer = turnController.getFirstPlayerToPlayAssistant();
        broadcastingMessageExceptOne(firstPlayer.getNickname() + " is now choosing his assistant card",firstPlayer.getNickname());
        VirtualView toPlay = virtualViewsMap.get(firstPlayer.getNickname());
        turnController.roundManager();
        //toPlay.askAssistantCard(firstPlayer.getDeckAssistantCard().getCards());   //questa decommentata da problemi TODO
    }


    /**
     * to send a textual generic message to all the clients connected to the server
     *
     * @param message the message to be sent to everybody
     */
    public void broadcastingMessage(String message){
        for(VirtualView virtualView : virtualViewsMap.values()){
            virtualView.showGenericMessage(message);
        }
    }

    /**
     * to send a textual generic message to all the clients connected to the server, except one
     * used to let the other players know of a move of the "excluded" player
     *
     * @param message the message to be sent
     * @param nickName the "excluded" player
     */
    public void broadcastingMessageExceptOne(String message,String nickName){
        for(String nick : virtualViewsMap.keySet()){
            if(!nick.equals(nickName)){
                virtualViewsMap.get(nick).showGenericMessage(message);
            }
        }
    }

    public boolean isGameStarted(){
        return game.getStatus()!=GameState.LOGGING;
    }

    /**
     * to remove a virtual view from this controller, because of a disconnection
     *
     * @param nickname the nickname of the player removed
     */
    public void removeVirtualView(String nickname){
        VirtualView toRemove = virtualViewsMap.remove(nickname);

        //game.removeObserver(toRemove);
        //game.getBoard().removeObserver(toRemove);
    }

    /**
     * method to call at the end of the game to show the final messages to each player (a win or a lose message) and to end the game
     */
    public void endedGame(){
        Player winningPlayer = null;    //forse possiamo evitare questo primo for per cercare il winning player
        for (Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.WINNER)){
                winningPlayer = player;
                break;
            }
        }

        for(Player player : game.getPlayers()){
            VirtualView view = virtualViewsMap.get(player.getNickname());
            if (player.equals(winningPlayer)) {
                view.showWinMessage(winningPlayer);
            }
            else{
                view.showLoseMessage(winningPlayer);
            }
        }
    }
}
