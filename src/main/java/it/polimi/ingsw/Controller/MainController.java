package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.ClientSide.AssistantSeedReply;
import it.polimi.ingsw.network.Messages.ClientSide.ExpertVariantReply;
import it.polimi.ingsw.network.Messages.ClientSide.NumPlayersReply;
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
    private GameState gameState;
    private int maxNumPlayers;
    private boolean expertVariant;

    public MainController(){
        this.game = new Game();
        this.virtualViewsMap = Collections.synchronizedMap(new HashMap<>());
        this.messageController = new MessageController(this,virtualViewsMap);
        this.gameState = GameState.LOGGING;
    }

    public Game getGame() {
        return game;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public GameState getGameState() {
        return gameState;
    }

    public MessageController getMessageController() {return messageController;}

    public void setGameState(GameState gameState) {this.gameState = gameState;}

    /**
     * It receives a message from the server
     *
     * @param message the message received from the server. The message is related to a particular client
     */
    public void messageFromServer(Message message) {

        VirtualView virtualView = virtualViewsMap.get(message.getNickName());

        switch (game.getStatus()){

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
                    //game.setMaxNumPlayers(maxNumPlayers); //brutto ma se no non posso aggiungere i player prima di fare initGame
                    broadcastingMessage("Waiting for players...");
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
        }
    }

    /**
     * Messages received while the game is currently playing
     *
     * @param message the message received
     */
    private void messageWhilePlaying(Message message){

        switch(message.getMessageType()){

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
            //addPlayerToMatch(nickname,assistantSeedChosen); //errore qua che gli passo null come seed
        }
        else if(virtualViewsMap.size() < game.getMaxNumPlayers()){
            virtualViewsMap.put(nickname,virtualView);
            //        game.addObserver(virtualView);
            //        game.getBoard().addObserver(virtualView);
            game.addPlayer(nickname);

            virtualView.showLoginInfo("SERVER",true,true);
            virtualView.askAssistantSeed(game.getSeedsAvailable());
            //addPlayerToMatch(nickname,assistantSeedChosen);

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

    private void startMatch(int maxNumPlayers,boolean experts){

        game.initGame(maxNumPlayers,experts);
        broadcastingMessage("Match is starting... All players are connected... ");
        //poi qua forse c'è da mostrare tramite la cli le 12 isole,le scholboards e le clouds -->matchInfo
        this.setGameState(game.getStatus()); //gameStatus==PLAYING set in the model
    }
    /*
    private void addPlayerToMatch(String nickName,AssistantSeed assistantSeedChosen){

        game.addPlayer(nickName,assistantSeedChosen);
        //I do not have to remove the seed from the list because the model do it
        virtualViewsMap.get(nickName).showGenericMessage("You have chosen "+ assistantSeedChosen+" as AssistantSeed");
        broadcastingMessageExceptOne(nickName+" has chosen "+assistantSeedChosen+" as AssistantSeed",nickName);
    }*/

    public void broadcastingMessage(String message){
        for(VirtualView virtualView : virtualViewsMap.values()){
            virtualView.showGenericMessage(message);
        }
    }

    public void broadcastingMessageExceptOne(String message,String nickName){
        for(String nick : virtualViewsMap.keySet()){
            if(!nick.equals(nickName)){
                virtualViewsMap.get(nick).showGenericMessage(message);
            }
        }
    }

    public boolean isGameStarted(){
        return this.gameState!=GameState.LOGGING;
    }
}
