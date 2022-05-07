package it.polimi.ingsw.server;

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
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private List<String> registeredNicknames = new ArrayList<>();   //list of nicknames currently connected
    private int numConnections = 0; //number of players currently connected
    private int numPlayers = 0; //how many players will be playing the game


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }


    public List<String> getRegisteredNicknames() {
        return registeredNicknames;
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


    //Deregister connection
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(c);
        playingConnection.remove(opponent);

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
    public synchronized void lobby(ClientConnection c, String name){
        registeredNicknames.add(name);

        /*List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for(int i = 0; i < keys.size(); i++){
            ClientConnection connection = waitingConnection.get(keys.get(i));
            connection.asyncSend("Connected User: " + keys.get(i));
        }*/

        numConnections++;
        waitingConnection.put(name, c);

        //keys = new ArrayList<>(waitingConnection.keySet());

        if(numPlayers == 0 || waitingConnection.size() < numPlayers) {
            c.asyncSend("Waiting for all the players to join ...");
        }

        else if(waitingConnection.size() == numPlayers) {
            for(String key : waitingConnection.keySet()) {
                ClientConnection player = waitingConnection.get(key);
                player.asyncSend("The game can start!\n\n");
                player.asyncSend("Number of players connected: " + numPlayers);
                for (int i = 0; i < registeredNicknames.size(); i++) {
                    player.asyncSend(registeredNicknames.get(i));
                }
            }
        }

        else{
            c.asyncSend("There are already too many players playing, please retry later!");
        }

        /*if (waitingConnection.size() == 2) {
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            Player player1 = new Player(keys.get(0), Cell.X);
            Player player2 = new Player(keys.get(0), Cell.O);
            View player1View = new RemoteView(player1, keys.get(1), c1);
            View player2View = new RemoteView(player2, keys.get(0), c2);
            Model model = new Model();
            Controller controller = new Controller(model);
            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.clear();

            c1.asyncSend(model.getBoardCopy());
            c2.asyncSend(model.getBoardCopy());
            //if(model.getBoardCopy().)
            if(model.isPlayerTurn(player1)){
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
            } else {
                c2.asyncSend(gameMessage.moveMessage);
                c1.asyncSend(gameMessage.waitMessage);
            }
        }*/
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
