package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.GuiMainStage;
import javafx.application.Application;

public class ClientApp {

    public static void main(String[] args) {

        boolean flag=false;

        for(String arg : args){
            if(arg.equals("--cli")){
                flag=true;
                break;
            }
        }

        if(flag){
            Cli view = new Cli();
            ClientController clientcontroller = new ClientController(view);
            view.addObserver(clientcontroller);
            view.matchStart();
        }
        else{
            Application.launch(GuiMainStage.class);
        }
    }
}
