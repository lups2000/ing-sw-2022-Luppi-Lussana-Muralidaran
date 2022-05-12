package it.polimi.ingsw;

import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.network.client.Client;

import java.io.IOException;

/*
public class ClientApp
{
    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
*/

public class ClientApp {

    public static void main(String[] args) {

        //qui tramite args per vedere se lanciare CLI o GUI

            Cli view = new Cli();
            //ClientController clientcontroller = new ClientController(view);
            //view.addObserver(clientcontroller);
            view.matchStart();

    }
}