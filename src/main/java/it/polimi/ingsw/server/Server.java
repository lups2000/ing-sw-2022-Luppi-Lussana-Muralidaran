package it.polimi.ingsw.server;

import it.polimi.ingsw.Controller.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
