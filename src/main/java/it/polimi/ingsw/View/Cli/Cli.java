package it.polimi.ingsw.View.Cli;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Cli /*extends observable ecc....*/{
    private final PrintStream out;
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
            out.print("Enter the server address [" + defaultAddress + "]: ");
            //da finire
        } while (!validInput);

    }
}
