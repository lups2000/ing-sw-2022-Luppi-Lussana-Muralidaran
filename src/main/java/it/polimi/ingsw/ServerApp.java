package it.polimi.ingsw;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

/**
 * Application to launch the server
 */
public class ServerApp {

    public static void main(String[] args) {
        int serverPort = 12345; // default value


        MainController mainController = new MainController();
        Server server = new Server(mainController);

        SocketServer socketServer = new SocketServer(server);
        Thread thread = new Thread(socketServer, "socketserver_");
        thread.start();
    }
}