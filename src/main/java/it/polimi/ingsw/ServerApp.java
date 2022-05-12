package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

import java.io.IOException;

/*
public class ServerApp
{
    public static void main( String[] args )
    {
        Server server;
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
*/


public class ServerApp {

    public static void main(String[] args) {
        int serverPort = 12345; // default value


        //GameController gameController = new GameController();
        Server server = new Server();

        SocketServer socketServer = new SocketServer(server);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}