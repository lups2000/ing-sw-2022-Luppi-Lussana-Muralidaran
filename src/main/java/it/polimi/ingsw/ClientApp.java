package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.Cli.Cli;
import it.polimi.ingsw.View.Gui.GuiMainStage;
import javafx.application.Application;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {


        System.out.println("Choose the modality: 'c' for CLI / 'g' for GUI");
        Scanner scanner = new Scanner(System.in);
        String answer;
        answer=scanner.nextLine();

        if(answer.equalsIgnoreCase("c")){
            Cli view = new Cli();
            ClientController clientcontroller = new ClientController(view);
            view.addObserver(clientcontroller);
            view.matchStart();
        }
        else if(answer.equalsIgnoreCase("g")){
            Application.launch(GuiMainStage.class);
        }
        else{
            System.err.println("Invalid Argument! Please run the executable again!");
        }

    }
}
