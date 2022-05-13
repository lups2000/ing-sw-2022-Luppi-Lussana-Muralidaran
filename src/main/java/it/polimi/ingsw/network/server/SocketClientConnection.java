package it.polimi.ingsw.network.server;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;
    private AssistantSeed chosenWizard;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public AssistantSeed getChosenWizard() {
        return chosenWizard;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }

    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        Scanner in;
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;

            boolean nicknameOK = true;
            for(int i=0;i<server.getRegisteredNicknames().size();i++) {
                if (name.equals(server.getRegisteredNicknames().get(i))) {
                    nicknameOK = false;
                }
            }

            while(!nicknameOK){
                send("Nickname already in use, please enter a new nickname: ");
                read = in.nextLine();
                name = read;

                nicknameOK = true;
                for(int i=0;i<server.getRegisteredNicknames().size();i++) {
                    if (name.equals(server.getRegisteredNicknames().get(i))) {
                        nicknameOK = false;
                    }
                }
            }

            server.lobby(this, name);


            //the first player to enter his nickname chooses the initial settings of the game
            if(server.getRegisteredNicknames().size() == 1){
                send("2 or 3 players? ");
                int numPlayers = in.nextInt();
                in.nextLine();

                while(numPlayers != 2 && numPlayers != 3){
                    send("Input non valid, please retry: ");
                    numPlayers = in.nextInt();
                    in.nextLine();
                }

                if (numPlayers == 2){
                    send("You chose to play with 2 players\n");
                }
                else {
                    send("You chose to play with 3 players\n");
                }

                server.setNumPlayers(numPlayers);

                send("Do you want to activate the experts variant? \nPress Y for yes or N for no: ");
                read = in.nextLine();

                while(!read.equals("Y") && !read.equals("N")){
                    send("Input non valid, please retry: ");
                    read = in.nextLine();
                }

                if (read.equals("Y")){
                    send("Experts variant activated correctly!");
                }
                else {
                    send("You chose to NOT activate the experts variant!");
                }

                server.setExpertsVariant(read);
            }

            send("Please select your wizard, press S for for SAMURAI, W for WITCH, K for KING, M for MAGICIAN: ");
            read = in.nextLine();


            while(!read.equals("S") && !read.equals("W") && !read.equals("K") && !read.equals("M")){
                send("Input non valid, please retry: ");
                read = in.nextLine();
            }

            //controllare che non sia già scelto da altri

            switch (read){
                case "S": this.chosenWizard = AssistantSeed.SAMURAI;
                    break;
                case "W": this.chosenWizard = AssistantSeed.WITCH;
                    break;
                case "K": this.chosenWizard = AssistantSeed.KING;
                    break;
                case "M": this.chosenWizard = AssistantSeed.MAGICIAN;
                    break;
            }

            server.addNumConnections();
            send("fin qui tutto bene!");

            if(server.getNumConnections() == server.getNumPlayers()){
                server.initializeGame();
            }

            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException | NoPawnPresentException | TooManyPawnsPresent e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }
}
*/

/**
 * Socket implementation of the {@link ClientConnection} interface.
 */

public class SocketClientConnection implements ClientConnection, Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Default constructor.
     *
     * @param socketServer the socket of the server.
     * @param client       the client connecting.
     */
    public SocketClientConnection(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.inputLock = new Object();
        this.outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            Server.LOGGER.severe("Client " + client.getInetAddress() + " connection dropped.");
            disconnect();
        }
    }

    /**
     * Handles the connection of a new client and keep listening to the socket for new messages.
     *
     * @throws IOException any of the usual Input/Output related exceptions.
     */
    private void handleClientConnection() throws IOException {
        Server.LOGGER.info("Client connected from " + client.getInetAddress());

        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                    if (message != null && message.getMessageType() != MessageType.PING) {
                        if (message.getMessageType() == MessageType.REQUEST_LOGIN) {
                            socketServer.addClient(message.getNickName(), this);
                        } else {
                            Server.LOGGER.info(() -> "Received: " + message);
                            socketServer.onMessageReceived(message);
                        }
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            Server.LOGGER.severe("Invalid stream from client");
        }
        client.close();
    }

    /**
     * Returns the current status of the connection.
     *
     * @return {@code true} if the connection is still active, {@code false} otherwise.
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * Disconnect the socket.
     */
    @Override
    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                Server.LOGGER.severe(e.getMessage());
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }

    @Override
    public void sendMessageToClient(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + message);
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }

    /*
     * Sends a message to the client via socket.
     *
     * @param message the message to be sent.

    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
                Server.LOGGER.info(() -> "Sent: " + message);
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
            disconnect();
        }
    }

     */
}