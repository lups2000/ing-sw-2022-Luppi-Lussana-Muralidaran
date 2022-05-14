package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.Message;

import java.util.*;
import java.util.logging.Logger;


/**
 * Main server class that starts a socket server.
 * It can handle different types of connections.
 */

public class Server {

    private final MainController mainController;

    private final Map<String, ClientConnection> clientsConnected;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(MainController mainController) {
        this.mainController = mainController;
        this.clientsConnected = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    /**
     * Adds a client to be managed by the server
     *
     * @param nickname the client's nickname
     * @param clientConnection the client's connection
     */
    public void addClient(String nickname, ClientConnection clientConnection) {
        VirtualView vv = new VirtualView(clientConnection);

        /*if (!mainController.isGameStarted()) {
            if (mainController.checkLoginNickname(nickname, vv)) {
                clientsConnected.put(nickname, clientConnection);
                mainController.loginHandler(nickname, vv);
            }
        } else {
            vv.showLoginResult(true, false, null);
            clientConnection.disconnect();
        }
        */
    }

    /**
     * Removes a client given his nickname
     *
     * @param nickname the client's nickname to be removed
     * @param notifyEnabled set to {@code true} to enable a lobby disconnection message, {@code false} otherwise.
     */
    public void removeClient(String nickname, boolean notifyEnabled) {
        clientsConnected.remove(nickname);
        //mainController.removeVirtualView(nickname, notifyEnabled);
        LOGGER.info(() -> "Removed " + nickname + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the MainController.
     *
     * @param message the message to be forwarded.
     */
    public void forwardsMessage(Message message) {
        mainController.messageFromServer(message);
    }

    /**
     * Handles the disconnection of a client
     *
     * @param clientConnection the client disconnecting
     */
    public void onDisconnect(ClientConnection clientConnection) {
        synchronized (lock) {
            String nickname = getNickname(clientConnection);

            /*if (nickname != null) {

                boolean gameStarted = mainController.isGameStarted();
                removeClient(nickname, !gameStarted); // enable lobby notifications only if the game didn't start yet.

                if(mainController.getTurnController() != null &&
                        !mainController.getTurnController().getNicknameQueue().contains(nickname)) {
                    return;
                }

                // Resets server status only if the game was already started.
                // Otherwise the server will wait for a new player to connect.
                if (gameStarted) {
                    mainController.broadcastDisconnectionMessage(nickname, " disconnected from the server. GAME ENDED.");

                    mainController.endGame();
                    clientsConnected.clear();
                }
            }

             */
        }
    }


    /**
     * Returns the client's nickname given his client connection
     *
     * @param clientConnection the client connection
     * @return the corresponding nickname of a client connection
     */
    private String getNickname(ClientConnection clientConnection) {
        String nickname = null;
        for(String key : clientsConnected.keySet()){
            if(clientConnection.equals(clientsConnected.get(key))){
                nickname = key;
                break;
            }
        }
        return nickname;
    }
}