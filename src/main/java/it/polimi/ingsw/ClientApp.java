package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.network.client.Client;

import java.util.logging.Level;

/**
 * Application to launch a client
 */
public class ClientApp {

    public static void main(String[] args) {

        /*
        boolean cliMode = false; //by default CLI

        for (String argument : args) {
            if (argument.equals("--cli")) {
                cliMode = true;
                break;
            }
        }

        if (cliMode) {
            Client.LOGGER.setLevel(Level.WARNING);
            Cli view = new Cli();
            ClientController clientcontroller = new ClientController(view);
            view.addObserver(clientcontroller);
            view.matchStart();
        }
        else{
            //lancio GUI
        }
         */
        Cli view = new Cli();
        ClientController clientcontroller = new ClientController(view);
        view.addObserver(clientcontroller);
        view.matchStart();
    }
}