package it.polimi.ingsw;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

import java.util.Scanner;

/**
 * Application to launch the server
 */
public class ServerApp {

    public static void main(String[] args) {
        int serverPort = 12345; // default value
        Scanner scanner=new Scanner(System.in);
        System.out.print("Please enter the port (Default Port: " + serverPort + "): ");
        try{
            serverPort=Integer.parseInt(scanner.nextLine());
        }
        catch (IllegalArgumentException e){
            Server.LOGGER.warning("Invalid port!Server available at the default one!");
        }
        System.out.println("The server is running...");
        MainController mainController = new MainController();
        Server server = new Server(mainController);

        SocketServer socketServer = new SocketServer(server,serverPort);
        Thread thread = new Thread(socketServer, "SocketServer");
        thread.start();

    }
}