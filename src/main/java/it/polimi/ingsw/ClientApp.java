package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli.Cli;

/**
 * Application to launch a client
 */
public class ClientApp {

    public static void main(String[] args) {

        //qui tramite args per vedere se lanciare CLI o GUI

            Cli view = new Cli();
            ClientController clientcontroller = new ClientController(view);
            view.addObserver(clientcontroller);
            view.matchStart();

    }
}