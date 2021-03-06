package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.Message;

import java.util.*;
import java.util.logging.Logger;


/**
 * This class represents the main Server
 */
public class Server {

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private final MainController mainController;
    private final Map<String, ClientConnection> clientsConnected;
    private final Object locker;

    public Server(MainController mainController) {
        this.mainController = mainController;
        this.clientsConnected = Collections.synchronizedMap(new HashMap<>());
        this.locker = new Object();
    }


    /**
     * This method adds a new Client to the server
     *
     * @param nickname of the client
     * @param clientConnection associated to the client
     */
    public void newClientManager(String nickname, ClientConnection clientConnection) {
        //instantiate a new virtual view that will be associated to the client
        VirtualView virtualView = new VirtualView(clientConnection);

        if(mainController.isGameStarted()){ //if the match has already started-->the client must disconnect
            virtualView.showError("Sorry but the Game has already started!Connection Refused!");
            //virtualView.showLoginInfo(null,true,false);
            clientConnection.disconnect();
        }
        else { //the client has possibilities to connect to the match
            if(mainController.getMessageController().checkNickName(nickname,virtualView)){ //nickName ok
                clientsConnected.put(nickname, clientConnection);
                mainController.loginToTheGame(nickname,virtualView);
            }
        }
    }


    /**
     * This method returns the nickName of the client that is associated at his client connection
     *
     * @param clientConnection of the client
     * @return nickName of the client
     */
    private String getNickname(ClientConnection clientConnection) {
        String nickname = null;
        for(String nick : clientsConnected.keySet()){
            if(clientConnection.equals(clientsConnected.get(nick))){
                nickname = nick;
                break;
            }
        }
        return nickname;
    }


    /**
     * This method removes a client from the server
     *
     * @param nickname the client's nickname to be removed
     */
    public void unregisterClientFromServer(String nickname) {
        //removing the client from the server
        clientsConnected.remove(nickname);
        mainController.removeVirtualView(nickname);
        LOGGER.info(nickname + " has been removed from the list of connected players!");
    }


    /**
     * This method forwards a message coming from the client to the MainController
     *
     * @param message the message to be forwarded
     */
    public void forwardsMessage(Message message) {
        mainController.messageFromServer(message);
    }


    /**
     * This class manages the case of disconnection of the client
     *
     * @param clientConnection associated to the client disconnected
     */

    public void disconnectionManager(ClientConnection clientConnection) {
        synchronized (locker) {
            String nickname = getNickname(clientConnection);

            if (nickname != null) {

                unregisterClientFromServer(nickname);

                // Resets server status only if the game was already started.
                // Otherwise the server will wait for a new player to connect.
                if (mainController.isGameStarted()) {
                    mainController.broadcastingDisconnection(nickname, " disconnected from the server. GAME ENDED.");
                    clientsConnected.clear();
                }
            }
        }
    }

}