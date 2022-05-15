package it.polimi.ingsw.View.Cli;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.View;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import it.polimi.ingsw.Model.Exceptions.*;

public class Cli implements View/*extends observable ecc....*/ {
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
        connectToServer();
    }

    public void connectToServer(){
        Map<String, String> serverDetails = new HashMap<>();
        String defaultAddress = "socketserver_ ";
        String defaultPort = "12345";
        boolean validInput = false;

        out.println("The value between the brackets is the default value");

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
            out.print("Please enter the server port (Default Port: " + defaultPort + "): ");
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

    public void clearCli(){
        out.flush();
    }

    @Override
    public void askNickName() {
        boolean validInput = false;
        out.print("Enter your nickname: ");
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

    @Override
    public void askNumPlayers() {
        boolean validInput = false;
        out.print("Insert number of players: ");
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

    @Override
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
        AssistantSeed assistantSeedChosen;
        Integer id;
        boolean validInput=false;
        if(assistantSeedAvailable.size()>=1){
            do{
                out.println("Please type the corresponding id to select one of the AssistantSeeds: ");
                System.out.print("( ");
                for(int i=0;i<assistantSeedAvailable.size();i++){
                    System.out.print((i+1)+": "+assistantSeedAvailable.get(i));
                    if(i< assistantSeedAvailable.size()-1){
                        System.out.print(", ");
                    }
                }
                System.out.println(" )");
                id= readLine.nextInt();

                if(id<=0 || id>assistantSeedAvailable.size()){
                    out.println("INVALID_INPUT");
                    validInput = false;
                }
                else{
                    assistantSeedChosen=assistantSeedAvailable.get(id-1);
                    validInput=true;
                    //send the seed to the server
                }
            }while (!validInput);
        }
        else{
            showError("No seeds available!");
        }
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        AssistantCard assistantCardChosen;
        boolean validInput=false;
        Integer id;

        if(assistantCards.size()>=1){

            do {
                out.println("Please type the corresponding id to select one of the AssistantCards: ");
                System.out.print("( ");
                for(int i=0;i<assistantCards.size();i++){
                    System.out.print((i+1)+": "+assistantCards.get(i));
                    if(i< assistantCards.size()-1){
                        System.out.print(", ");
                    }
                }
                System.out.println(" )");
                id= readLine.nextInt();

                if(id<=0 || id>assistantCards.size()){
                    out.println("INVALID_INPUT");
                    validInput = false;
                }
                else{
                    assistantCardChosen=assistantCards.get(id-1);
                    validInput=true;
                    //send the card to the server
                }
            }while (!validInput);
        }
        else {
            showError("No assistantCards available!");
        }
    }

    @Override
    public void askMoveStudToDining(PawnColor pawnColor) {

    }

    @Override
    public void askMoveStudToIsland(PawnColor pawnColor, List<Island> islands) {

    }

    @Override
    public void askMoveMotherNature(List<Island> islands) {

    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {

    }

    @Override
    public void askExpertVariant() {
        boolean validInput = false;
        out.println("Do you want to play the expert variant of the game? Y/N");
        String expertVariantAnswer = readLine.next();
        do {
            if (expertVariantAnswer.equals("Y") || expertVariantAnswer.equals("N")) {
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

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    @Override
    public void showError(String error) {
        out.println("Error: "+error);
    }

    @Override
    public void showLobby(List<Player> players, int numPlayers) {
        out.println("At the moment there are the the following players in the Lobby: "+players.size()+" / "+numPlayers);
        out.print("( ");
        for(int i=0;i<players.size();i++){
            out.print(players.get(i).getNickname());
            if(i<players.size()-1){
                out.print(", ");
            }
        }
        out.println(" )");
    }

    @Override
    public void showWinMessage(Player winner) {
        out.println("Congratulations "+winner.getNickname()+" you have won the game!Game finished.");
        System.exit(0);
    }

    @Override
    public void showLoseMessage(Player looser) {
        out.println("Sorry "+looser.getNickname()+" you have lost the game!Game finished.");
        System.exit(0);
    }

    @Override
    public void showLoginPlayers(String nickName, boolean nickNameOk, boolean connectionOk) {
        clearCli();

        if(nickNameOk && connectionOk){
            out.println("Nice to meet you "+nickName+", now you are connected!");
        }
        else if(nickNameOk){
            out.println("We are sorry but the connection has been refused!Try later!");
            System.exit(1);
        }
        else if(connectionOk){
            askNickName();
        }
        else{
            showError("Error reaching the Server!");
        }

    }

    @Override
    public void showMatchInfo(ArrayList<Player> players, boolean experts, int numPlayers) {

        out.println("MATCH INFO: ");
        out.print("#Players connected: ( ");
        for(int i=0;i<players.size();i++){
            out.print(players.get(i).getNickname());
            if(i<players.size()-1){
                out.println(", ");
            }
        }
        out.println(" ) "+players.size()+" / "+numPlayers);
        out.println("#Expert Variant: "+experts);
    }
}
