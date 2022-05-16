package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.Message;

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

    /**
     * It receives a message from the server
     *
     * @param message the message received from the server
     */
    public void messageFromServer(Message message){

        VirtualView virtualView = virtualViewsMap.get(message.getNickName());

        switch (game.getStatus()){

            case CREATING:
                break;

            case PLAYING:
                break;

            //case DISCONNECTED se vorremo implementare la funzionalità aggiuntiva, altrimenti ha senso?

            case ENDED:
                break;
        }
    }

    //macchina a stati qui? TODO
    //i 3 stati qui sono LOGIN, (INIT), INGAME


    //nei 3 sopracitati stati fai le chiamate ai vari effetti

    /**
     * Method to handle the login to the game of a new client
     * We create his virtual view, we register the player into the model game
     * If it is the first player we also have to ask him the number of players and if he wants to play with the experts variant
     * To all the players we anyway also ask his chosen assistant seed
     *
     * @param nickname the client's nickname
     * @param virtualView the virtual view associated to the client
     */
    public void loginToTheGame(String nickname, VirtualView virtualView){   //viene invocato dalla classe Server quando si aggiunge un nuovo client, solo se non si è già raggiunto il num di players

        if(virtualViewsMap.size() == 0){    //it means it is the first player
            virtualViewsMap.put(nickname,virtualView);
            //        game.addObserver(virtualView);
            //        game.getBoard().addObserver(virtualView);
            virtualView.showLoginPlayers(nickname,true,true);   //passare il nickname del player ?
            //game.addPlayer(nickname,);    sistemare qualcosa nel model qui TODO
            virtualView.askNumPlayers();
            virtualView.askExpertVariant();
            virtualView.askAssistantSeed(game.getSeedsAvailable());
        }

        else if(virtualViewsMap.size() <= game.getMaxNumPlayers()){
            virtualViewsMap.put(nickname,virtualView);
            //        game.addObserver(virtualView);
            //        game.getBoard().addObserver(virtualView);
            //game.addplayer(nickname, );
            virtualView.showLoginPlayers(nickname,true,true);
            virtualView.askAssistantSeed(game.getSeedsAvailable());

            if(game.getPlayers().size() == game.getMaxNumPlayers()){    //all the required players logged and the game can start
                this.gameState = GameState.CREATING;
                //o forse direttamente PLAYING, però c'è da mostrare a tutti i player i giocatori registrati nella lobby
                // e il risultato dell'inizializzazione (scelta dei seed, tavolo da gioco imbandito doppo initGame() del model)

                turnController = new TurnController(this.game,virtualViewsMap);
            }
        }

        else{
            virtualView.showLoginPlayers(nickname,true,false);
        }
    }
}
