package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection, Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
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
            if(server.getNumConnections() == 1){
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
            }



            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.getMessage());
        }finally{
            close();
        }
    }
}
