package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
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

    public MainController(){
        this.game = new Game();
        this.virtualViewsMap = Collections.synchronizedMap(new HashMap<>());
        this.messageController = new MessageController(this,virtualViewsMap);
    }

    public Game getGame() {
        return game;
    }

    public TurnController getTurnController() {
        return turnController;
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
}
