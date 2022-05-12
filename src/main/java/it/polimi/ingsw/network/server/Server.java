package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.VirtualView;
import it.polimi.ingsw.network.Messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/*
public class Server {

    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private List<String> registeredNicknames = new ArrayList<>();   //list of nicknames currently connected
    private int numConnections = 0; //number of players currently connected
    private int numPlayers = 4; //how many players will be playing the game
    private boolean expertsVariant;


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }


    public List<String> getRegisteredNicknames() {
        return registeredNicknames;
    }

    public void addNumConnections(){
        this.numConnections++;
    }

    public int getNumConnections(){
        return numConnections;
    }

    public int getNumPlayers(){
        return numPlayers;
    }

    public void setNumPlayers(int num){
        this.numPlayers = num;
    }

    public void setExpertsVariant(String choice){
        if(choice.equals("Y")){
            this.expertsVariant = true;
        }
        else{
            this.expertsVariant = false;
        }
    }

    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {

        for (String key : waitingConnection.keySet()){
            if(waitingConnection.get(key).equals(c)){
                registeredNicknames.remove(key);
            }
        }

        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }

        numConnections--;
    }


    //Wait for another player
    public synchronized void lobby(ClientConnection c, String name) throws NoPawnPresentException, TooManyPawnsPresent {
        registeredNicknames.add(name);
        waitingConnection.put(name, c);

        if(waitingConnection.size() != 1 && waitingConnection.size() <= numPlayers) {
            c.asyncSend("Waiting for all the players to join ...");
        }

        else{
            if(waitingConnection.size() != 1) {
                c.asyncSend("There are already too many players playing, please retry later!");
            }
        }

    }

    public void initializeGame() throws NoPawnPresentException, TooManyPawnsPresent {
        reachedNumPlayers();
        Game game = new Game();
        game.initGame(numPlayers,expertsVariant);
        for(String key : waitingConnection.keySet()){
            game.addPlayer(key,waitingConnection.get(key).getChosenWizard());
        }
        //MatchController controller = new MatchController(game);
    }


    private void reachedNumPlayers(){
        for(String key : waitingConnection.keySet()) {
            ClientConnection player = waitingConnection.get(key);
            player.asyncSend("The game can start!\n\n");
            player.asyncSend("Number of players connected: " + numPlayers);
            for (int i = 0; i < registeredNicknames.size(); i++) {
                player.asyncSend(registeredNicknames.get(i));
            }
        }
    }

    public void run(){
        System.out.println("Server is running");
        while(true){
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Ready for the new connection - " + numConnections);
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
 */

/**
 * Main server class that starts a socket server.
 * It can handle different types of connections.
 */

public class Server {

    //private final GameController gameController;

    private final Map<String, ClientConnection> clientsConnected;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Object lock;

    public Server(/*GameController gameController*/) {
        //this.gameController = gameController;
        this.clientsConnected = Collections.synchronizedMap(new HashMap<>());
        this.lock = new Object();
    }

    /**
     * Adds a client to be managed by the server instance.
     *
     * @param nickname      the nickname associated with the client.
     * @param clientConnection the client connection associated with the client.
     */
    public void addClient(String nickname, ClientConnection clientConnection) {
        VirtualView vv = new VirtualView(clientConnection);

        /*if (!gameController.isGameStarted()) {
            if (gameController.checkLoginNickname(nickname, vv)) {
                clientsConnected.put(nickname, clientConnection);
                gameController.loginHandler(nickname, vv);
            }
        } else {
            vv.showLoginResult(true, false, null);
            clientConnection.disconnect();
        }
        */
    }

    /**
     * Removes a client given his nickname.
     *
     * @param nickname      the VirtualView to be removed.
     * @param notifyEnabled set to {@code true} to enable a lobby disconnection message, {@code false} otherwise.
     */
    public void removeClient(String nickname, boolean notifyEnabled) {
        clientsConnected.remove(nickname);
        //gameController.removeVirtualView(nickname, notifyEnabled);
        LOGGER.info(() -> "Removed " + nickname + " from the client list.");
    }

    /**
     * Forwards a received message from the client to the GameController.
     *
     * @param message the message to be forwarded.
     */
    public void onMessageReceived(Message message) {
        //gameController.onMessageReceived(message);
    }

    /**
     * Handles the disconnection of a client.
     *
     * @param clientConnection the client disconnecting.
     */
    public void onDisconnect(ClientConnection clientConnection) {
        synchronized (lock) {
            String nickname = getNickname(clientConnection);

            /*if (nickname != null) {

                boolean gameStarted = gameController.isGameStarted();
                removeClient(nickname, !gameStarted); // enable lobby notifications only if the game didn't start yet.

                if(gameController.getTurnController() != null &&
                        !gameController.getTurnController().getNicknameQueue().contains(nickname)) {
                    return;
                }

                // Resets server status only if the game was already started.
                // Otherwise the server will wait for a new player to connect.
                if (gameStarted) {
                    gameController.broadcastDisconnectionMessage(nickname, " disconnected from the server. GAME ENDED.");

                    gameController.endGame();
                    clientsConnected.clear();
                }
            }

             */
        }
    }


    /**
     * Returns the corresponding nickname of a ClientHandler.
     *
     * @param clientConnection the client handler.
     * @return the corresponding nickname of a ClientHandler.
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