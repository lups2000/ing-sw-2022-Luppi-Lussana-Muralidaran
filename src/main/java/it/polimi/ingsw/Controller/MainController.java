package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Utils.StoreGame;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.server.Server;

import javax.sound.midi.Soundbank;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents the main controller
 * It receives messages from the server, reads them and responds to them after checks executed by all the controllers
 */
public class MainController implements Serializable {

    @Serial
    private static final long serialVersionUID= 8347814763959357381L;
    private final Game game;
    private final transient Map<String, VirtualView> virtualViewsMap;
    private MessageController messageController;
    private TurnController turnController;
    private final List<String> nickNameList;
    private int maxNumPlayers;
    private boolean expertVariant;

    public MainController(){
        this.game = new Game();
        this.virtualViewsMap = Collections.synchronizedMap(new HashMap<>());
        this.messageController = new MessageController(this,virtualViewsMap);
        this.nickNameList=new ArrayList<>();
    }

    public Game getGame() {
        return game;
    }
    public TurnController getTurnController() {
        return turnController;
    }
    public List<String> getNickNameList() {return nickNameList;}
    public MessageController getMessageController() {return messageController;}


    /**
     * It receives a message from the server, who previously received it from the client
     *
     * @param message the message received from the server. The message is related to a particular client
     */
    public void messageFromServer(Message message) {

        switch (game.getStatus()) {
            case LOGGING -> messageWhileLogging(message);
            case PLAYING -> messageWhilePlaying(message);
        }
    }

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
                    game.setMaxNumPlayers(maxNumPlayers);
                    virtualViewsMap.get(message.getNickName()).askExpertVariant();
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_EXPERT_VARIANT -> {
                if(messageController.checkExpertVariant(message)){ //message format ok
                    ExpertVariantReply expertVariantReply = (ExpertVariantReply) message;
                    expertVariant=expertVariantReply.isExpertVariant();
                    virtualViewsMap.get(message.getNickName()).askAssistantSeed(game.getSeedsAvailable());
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

                    if(game.getPlayers().size() < game.getMaxNumPlayers()){
                        broadcastingMessage("Waiting for players...");
                    }
                    else if(game.getPlayers().size() == game.getMaxNumPlayers()){
                        //the match can start only when the Assistant seeds of all players are received
                        startMatch(maxNumPlayers,expertVariant);
                    }
                }
                else{
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }
            default -> Server.LOGGER.warning("Wrong message received from client.");
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
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_CHARACTER_CARD -> {
                if(messageController.checkCharacterCard(message)){
                    turnController.messageFromMainController(message);
                }
                else{
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_CLOUD_TILE -> {
                if(messageController.checkCloudTile(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_MOTHER_NATURE -> {
                if(messageController.checkMotherNature(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_STUD_DINING -> {
                if(messageController.checkStudentToDining(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_MOVE_STUD_ISLAND -> {
                if(messageController.checkStudentToIsland(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_COLOR -> {
                if(messageController.checkColor(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_ISLAND -> {
                if(messageController.checkIsland(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case REPLY_STUDENT_OR_STOP -> {
                if(messageController.checkStudentOrStop(message)){
                    turnController.messageFromMainController(message);
                }
                else {
                    Server.LOGGER.warning("The format of the message sent by the client is incorrect!");
                }
            }

            case GENERIC_MESSAGE -> turnController.messageFromMainController(message);

            default -> Server.LOGGER.warning("Wrong message received from client.");
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
    public void loginToTheGame(String nickname, VirtualView virtualView) {   //viene invocato dalla classe Server quando si aggiunge un nuovo client, solo se non si è già raggiunto il num di players

        if(virtualViewsMap.size() == 0){    //it means it is the first player ever to connect

            virtualViewsMap.put(nickname,virtualView);
            nickNameList.add(nickname);
            game.addObserver(virtualView);
            virtualView.showLoginInfo("SERVER",true,true);
            game.addPlayer(nickname); //add the player to the model

            virtualView.askNumPlayers();
            //virtualView.askExpertVariant();
            //virtualView.askAssistantSeed(game.getSeedsAvailable());

        }
        else if(virtualViewsMap.size() < game.getMaxNumPlayers()){

            virtualViewsMap.put(nickname,virtualView);
            nickNameList.add(nickname);
            game.addObserver(virtualView);
            game.addPlayer(nickname); //add the player to the model
            virtualView.showLoginInfo("SERVER",true,true);
            virtualView.askAssistantSeed(game.getSeedsAvailable());

            if(maxNumPlayers==game.getPlayers().size()){ //the lobby is full

                StoreGame storeGame =new StoreGame(this);
                MainController mainControllerPreviousMatch= storeGame.getPreviousMatch();

                if(mainControllerPreviousMatch!=null && game.getAllPlayersNickName().size() == mainControllerPreviousMatch.getNickNameList().size() && game.getAllPlayersNickName().containsAll(mainControllerPreviousMatch.getNickNameList())){

                    broadcastingMessage("\nThe server went down!Now you can continue the match...");
                    replaceMainController(mainControllerPreviousMatch);
                }
                /*
                else{
                    virtualView.askAssistantSeed(game.getSeedsAvailable());
                }*/
            }
        }
        //a questo else teoricamente non ci arrivo mai
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

        game.changeStatus(GameState.PLAYING); //gameStatus==PLAYING set in the model

        this.turnController = new TurnController(game,virtualViewsMap,this);

        Thread threadRoundManager=new Thread(() -> turnController.roundManager());
        threadRoundManager.start();
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

    public void broadcastingDisconnection(String nickName,String message){
        for(VirtualView virtualView : virtualViewsMap.values()){
            virtualView.showDisconnection(nickName,message);
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
        VirtualView toRemove = virtualViewsMap.get(nickname);
        this.game.removeObserver(toRemove);
        this.virtualViewsMap.remove(nickname);
    }

    public void endedGame(){

        StoreGame storeGame=new StoreGame(this);
        storeGame.deleteGame();

    }

    public void replaceMainController(MainController mainControllerPreviousMatch){

        List<Player> players =mainControllerPreviousMatch.getGame().getPlayers();
        int numMaxPlayers=mainControllerPreviousMatch.getGame().getMaxNumPlayers();
        boolean expertVariant=mainControllerPreviousMatch.getGame().getExpertsVariant();
        List<AssistantSeed> seedsAvailable=mainControllerPreviousMatch.getGame().getSeedsAvailable();
        List<Island> islands=mainControllerPreviousMatch.getGame().getIslands();
        List<CloudTile> cloudTiles=mainControllerPreviousMatch.getGame().getCloudTiles();
        List<CharacterCard> characterCards=mainControllerPreviousMatch.getGame().getCharacterCards();
        GameState gameState=mainControllerPreviousMatch.getGame().getStatus();
        StudentBag studentBag=mainControllerPreviousMatch.getGame().getStudentBag();
        int noEntryTilesCounter=mainControllerPreviousMatch.getGame().getNoEntryTilesCounter();
        Map<Player,AssistantCard> currentHand=mainControllerPreviousMatch.getGame().getCurrentHand();
        int motherNature=0;
        for(Island island :islands){
            if(island.isMotherNature()){
                motherNature= island.getIndex();
                break;
            }
        }

        try {
            this.game.replaceGame(players,numMaxPlayers,expertVariant,islands,cloudTiles,characterCards,gameState,studentBag,motherNature,currentHand,seedsAvailable,noEntryTilesCounter);
        } catch (NoPawnPresentException e) {
            e.printStackTrace();
        }
        Island.setNumIslands(islands.size());

        this.turnController=mainControllerPreviousMatch.getTurnController();
        this.turnController.setModel(this.game);
        this.turnController.setVirtualViewMap(this.virtualViewsMap);
        this.messageController=new MessageController(this,this.virtualViewsMap);

        Thread threadRoundManager=new Thread(() -> turnController.roundManager());
        threadRoundManager.start();
    }

}
