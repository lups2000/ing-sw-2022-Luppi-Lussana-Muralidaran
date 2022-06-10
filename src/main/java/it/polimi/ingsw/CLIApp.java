package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.network.client.Client;

import java.util.logging.Level;

/**
 * Application to launch a client with Command Line Interface
 */
public class CLIApp {

    public static void main(String[] args) {
        Cli view = new Cli();
        ClientController clientcontroller = new ClientController(view);
        view.addObserver(clientcontroller);
        view.matchStart();
    }
}