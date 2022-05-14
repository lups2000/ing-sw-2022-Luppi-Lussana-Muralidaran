package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.View.View;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import it.polimi.ingsw.Model.Exceptions.*;

public class Cli /*extends observable ecc....*/ {
    private final PrintStream out;
    private static final String CANCEL_INPUT = "User input has been canceled";
    private static final String INVALID_INPUT = "The entered input is not valid!";
    private String welcomeMessage = "\n\n\033[38;2;255;255;0m Hey there! Welcome to Eriantys! ";


    public Cli() {
        out = System.out;
    }

    public Scanner readLine = new Scanner(System.in);

    //Starts the interface
    public void matchStart(){
        out.println("\n" +
                "\033[38;2;255;0;0m   ▄████████    ▄████████  ▄█     ▄████████ ███▄▄▄▄       ███     ▄██   ▄      ▄████████ \n" +
                "\033[38;2;255;128;0m  ███    ███   ███    ███ ███    ███    ███ ███▀▀▀██▄ ▀█████████▄ ███   ██▄   ███    ███ \n" +
                "\033[38;2;255;255;0m  ███    █▀    ███    ███ ███▌   ███    ███ ███   ███    ▀███▀▀██ ███▄▄▄███   ███    █▀  \n" +
                "\033[38;2;0;255;0m ▄███▄▄▄      ▄███▄▄▄▄██▀ ███▌   ███    ███ ███   ███     ███   ▀ ▀▀▀▀▀▀███   ███        \n" +
                "\033[38;2;0;255;128m▀▀███▀▀▀     ▀▀███▀▀▀▀▀   ███▌ ▀███████████ ███   ███     ███     ▄██   ███ ▀███████████ \n" +
                "\033[38;2;0;255;255m  ███    █▄  ▀███████████ ███    ███    ███ ███   ███     ███     ███   ███          ███ \n" +
                "\033[38;2;0;128;255m  ███    ███   ███    ███ ███    ███    ███ ███   ███     ███     ███   ███    ▄█    ███ \n" +
                "\033[38;2;0;0;255m  ██████████   ███    ███ █▀     ███    █▀   ▀█   █▀     ▄████▀    ▀█████▀   ▄████████▀  \n" +
                "\033[38;2;127;0;255m               ███    ███                                                                \n");
        welcomeMessage += new String(Character.toChars(0x1F604));
        out.println(welcomeMessage);
    }

    public void connectToServer(){
        Map<String, String> serverDetails = new HashMap<>();
        String defaultAddress = "socketserver_ ";
        String defaultPort = "12345";
        boolean validInput = false;

        out.println("The value between the brackets if the default value");

        do {
            out.print("Please enter the server address (Default address: " + defaultAddress + "): ");
            String inputAddress = readLine.next();

            if (inputAddress.equals("")){
                serverDetails.put("address", defaultAddress);
                validInput = true;
            }
            //else if (inputAddress.equals(INSERIRE CONTROLLO DEL CONTROLLER)){serverDetails.put("address", inputAddress);validInput = true;}
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);

        do {
            out.println("Please enter the server port (Default Port: " + defaultPort + "): ");
            String inputPort = readLine.next();

            if (inputPort.equals("")){
                serverDetails.put("port", defaultPort);
                validInput = true;
            }
            //else if (INSERIRE CONTROLLO DEL CONTROLLER){serverDetails.put("port", inputPort);validInput = false}
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
    }

    public void askPlayersNumber() {
        boolean validInput = false;
        out.println("Insert number of players:");
        int playersNumber = readLine.nextInt();
        do {
            if (playersNumber == 2 || playersNumber == 3)  {
                //Mandare il numero di giocatori al server
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
            }

        } while (!validInput);
    }

    public void askExpertsVariant () {
        boolean validInput = false;
        out.println("Do you want to play the expert variant of the game? Y/N");
        String expertVariantAnswer = readLine.next();
        do {
            if (!expertVariantAnswer.equals("Y") || !expertVariantAnswer.equals("N")) {
                //Manda risposta a server
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
    }

    public void askNickname() {
        boolean validInput = false;
        out.println("Enter your nickname:");
        String nickname = readLine.next();
        do {
            if (nickname.equals("") /*|| ERRORE*/){
                out.println(INVALID_INPUT);
                validInput = false;
            }
            else {
                //Manda nickname al server
                validInput = true;
                clearCli();
            }

        } while (!validInput);
    }

    public void clearCli(){
        out.flush();
    }


}
