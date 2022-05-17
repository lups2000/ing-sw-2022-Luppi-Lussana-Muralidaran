package it.polimi.ingsw.View.Cli;

/**
@author Pradeeban Muralidaran
 */

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.observer.Observable4View;

import java.io.PrintStream;
import java.util.*;


public class Cli extends Observable4View implements View {
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

    public void clearCli(){
        out.flush();
    }

    /**
     * Method to ask the ip and port number to the client and try to estabilish a connection to the server,
     * after having checked that the client's input are valid with the CilentController's methods okIpAddress and okPortNumber
     */
    public void connectToServer(){
        final String correctIp;
        final String correctPort;
        String inputAddress;
        String inputPort;
        String defaultAddress = "localhost";
        String defaultPort = "12345";
        boolean validInput;

        out.println("The value between the brackets is the default value");

        do {
            out.print("Please enter the server address (Default address: " + defaultAddress + "): ");
            inputAddress = readLine.nextLine();

            if (inputAddress.isEmpty()){ //empty string-->default value
                validInput = true;
            }
            else if(ClientController.okIpAddress(inputAddress)){ //input ok
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);

        if(inputAddress.isEmpty()){
            correctIp=defaultAddress;
        }
        else {
            correctIp = inputAddress;
        }

        do {
            out.print("Please enter the server port (Default Port: " + defaultPort + "): ");
            inputPort = readLine.nextLine();

            if (inputPort.isEmpty()){ //empty string-->default value
                validInput =true;
            }
            else if(ClientController.okPortNumber(inputPort)){ //input ok
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);

        if(inputPort.isEmpty()){
            correctPort=defaultPort;
        }
        else{
            correctPort = inputPort;
        }

        notifyObserver(obs -> obs.connectClientToServer(correctIp,correctPort));
    }


    @Override
    public void askNickName() {
        boolean validInput;
        out.print("Enter your nickname: ");
        String nickname = readLine.nextLine();
        do {
            if (nickname.isEmpty()){
                out.println(INVALID_INPUT);
                validInput = false;
            }
            else {
                notifyObserver(obs -> obs.sendNickname(nickname));
                validInput = true;
                clearCli();
            }

        } while (!validInput);
    }

    @Override
    public void askNumPlayers() {
        boolean validInput;
        out.print("Insert number of players: ");
        int playersNumber = readLine.nextInt();
        do {
            if (playersNumber == 2 || playersNumber == 3)  {
                notifyObserver(obs -> obs.sendNumPlayers(playersNumber));
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
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
        AssistantSeed assistantSeedChosen;
        int id;
        boolean validInput;
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
                    out.println(INVALID_INPUT);
                    validInput = false;
                }
                else{
                    assistantSeedChosen=assistantSeedAvailable.get(id-1);
                    validInput=true;
                    AssistantSeed finalAssistantSeedChosen = assistantSeedChosen;
                    notifyObserver(obs -> obs.sendAssistantSeed(finalAssistantSeedChosen));
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
        boolean validInput;
        int id;

        if(assistantCards.size()>=1){

            do {
                out.println("Please type the corresponding id to select one of the AssistantCards: ");
                out.print("( ");
                for(int i=0;i<assistantCards.size();i++){
                    out.print((i+1)+": "+assistantCards.get(i));
                    if(i< assistantCards.size()-1){
                        out.print(", ");
                    }
                }
                out.println(" )");
                id= readLine.nextInt();

                if(id<=0 || id>assistantCards.size()){
                    out.println(INVALID_INPUT);
                    validInput = false;
                }
                else{
                    assistantCardChosen=assistantCards.get(id-1);
                    validInput=true;
                    AssistantCard finalAssistantCardChosen = assistantCardChosen;
                    notifyObserver(obs -> obs.sendAssistantCard(finalAssistantCardChosen));
                }
            }while (!validInput);
        }
        else {
            showError("No assistantCards available!");
        }
    }

    @Override
    public void askMoveStud(List<PawnColor> pawnColors, List<Island> islands){

    }

    @Override
    public void askMoveStudToDining(List<PawnColor> pawnColors) {
        int studentsToMove = 3;
        boolean validInput = false;
        out.println("Do you want to move some students in the dining room?");
        String playerAnswer = readLine.next();

    }

    @Override
    public void askMoveStudToIsland(List<Island> islands) {

    }

    @Override
    public void askMoveMotherNature(List<Island> islands) {

    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {

        CloudTile cloudTileChosen;
        boolean validInput;
        int id;

        if(cloudTiles.size()>=1){

            do{
                out.println("Please type the corresponding id to select one of the CloudTiles: ");
                out.print("( ");
                for(int i=0;i<cloudTiles.size();i++){
                    System.out.print((i+1)+": "+cloudTiles.get(i));
                    if(i< cloudTiles.size()-1){
                        System.out.print(", ");
                    }
                }
                System.out.println(" )");
                id= readLine.nextInt();

                if(id<=0 || id>cloudTiles.size()){
                    out.println(INVALID_INPUT);
                    validInput = false;
                }
                else{
                    cloudTileChosen=cloudTiles.get(id-1);
                    validInput=true;
                    //send the cloudTile to the server
                }
            }while(!validInput);
        }
        else{
            showError("No cloudTiles available!");
        }
    }

    @Override
    public void askExpertVariant() {
        boolean validInput;
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
        boolean validInput;
        /*Forse si potrebbe fare un ciclo di controllo per vedere se si hanno abbastanza monete per giocare una delle 3 carte
        in modo da non far partire inutilmente la funzione a schermo*/
        out.println("Do you want to use a Character Card?");
        String characterCardAnswer = readLine.next();
        do {
            if (characterCardAnswer.equals("Y")) {
                boolean validCost = false;
                do {
                    //Manda risposta a server e scegli carta da utilizzare e attivane l'effetto
                    out.println("Select one of the three cards (type index):");
                    int characterCardIndex = readLine.nextInt();
                    /*
                    if (choosenCard.cost <= schoolBoard.getNumCoins()) {
                        activate effect of the card;
                        schoolBoard.decreaseNumCoins(choosenCard.cost);
                        validCost = true;
                    }
                    else {
                        out.println("Can't play this card");
                    }
                    */

                } while (!validCost);
                validInput = true;
            }
            else if (characterCardAnswer.equals("N")) {
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
    public void showSchoolBoard(SchoolBoard schoolBoard){
        out.println("CURRENT SCHOOL BOARD\n\nDining Room:\n\n");
        for (PawnColor pawnColor: PawnColor.values()){
            out.print(pawnColor+" students: ");
            for (int i=0; i<schoolBoard.getStudentsDining().get(pawnColor); i++) {
                out.print("X");
                if (schoolBoard.getProfessors().get(pawnColor)) {
                    out.print("\nYou have this color professor");
                }
            }
            out.println("");
        }
        out.println("\nStudents waiting outside");
        for (PawnColor pawnColor: PawnColor.values()){
            for (int i=0; i<schoolBoard.getStudentsWaiting().get(pawnColor); i++){
                out.println("X");
            }
        }
        out.println("Number of coins: "+ schoolBoard.getNumCoins());
        out.println("Number of available towers: "+schoolBoard.getNumTowers());
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
