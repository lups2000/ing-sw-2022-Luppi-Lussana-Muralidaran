package it.polimi.ingsw.View.Cli;


import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.network.Messages.ServerSide.Islands;
import it.polimi.ingsw.observer.Observable4View;

import java.io.PrintStream;
import java.util.*;


/**
 * @author Pradeeban Muralidaran,Matteo Luppi
 */
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
        out.println("""

                \033[38;2;255;0;0m   ▄████████    ▄████████  ▄█     ▄████████ ███▄▄▄▄       ███     ▄██   ▄      ▄████████\s
                \033[38;2;255;128;0m  ███    ███   ███    ███ ███    ███    ███ ███▀▀▀██▄ ▀█████████▄ ███   ██▄   ███    ███\s
                \033[38;2;255;255;0m  ███    █▀    ███    ███ ███▌   ███    ███ ███   ███    ▀███▀▀██ ███▄▄▄███   ███    █▀ \s
                \033[38;2;0;255;0m ▄███▄▄▄      ▄███▄▄▄▄██▀ ███▌   ███    ███ ███   ███     ███   ▀ ▀▀▀▀▀▀███   ███       \s
                \033[38;2;0;255;128m▀▀███▀▀▀     ▀▀███▀▀▀▀▀   ███▌ ▀███████████ ███   ███     ███     ▄██   ███ ▀███████████\s
                \033[38;2;0;255;255m  ███    █▄  ▀███████████ ███    ███    ███ ███   ███     ███     ███   ███          ███\s
                \033[38;2;0;128;255m  ███    ███   ███    ███ ███    ███    ███ ███   ███     ███     ███   ███    ▄█    ███\s
                \033[38;2;0;0;255m  ██████████   ███    ███ █▀     ███    █▀   ▀█   █▀     ▄████▀    ▀█████▀   ▄████████▀ \s
                \033[38;2;127;0;255m               ███    ███                                                               \s
                """);
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
        String nickName;
        do {
            out.print("Enter your nickname: ");
            nickName = readLine.nextLine();

            if (nickName.isEmpty()){
                out.println(INVALID_INPUT);
                validInput = false;
            }
            else {
                validInput = true;
                clearCli();
            }

        } while (!validInput);

        String finalNickName = nickName;
        notifyObserver(obs -> obs.sendNickname(finalNickName));
    }

    @Override
    public void askNumPlayers() {
        boolean validInput;
        int numPlayers;
        do {
            out.print("Insert number of players (max 3): ");
            numPlayers = Integer.parseInt(readLine.nextLine());

            if (numPlayers>=2 && numPlayers<=3)  {
                validInput = true;
                int finalNumPlayers = numPlayers;
                notifyObserver(obs->obs.sendNumPlayers(finalNumPlayers));
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
        AssistantSeed assistantSeedChosen = null;
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
                id= Integer.parseInt(readLine.nextLine());

                if(id<=0 || id>assistantSeedAvailable.size()){
                    out.println(INVALID_INPUT);
                    validInput = false;
                }
                else{
                    assistantSeedChosen=assistantSeedAvailable.get(id-1);
                    validInput=true;
                }
            }while (!validInput);
            AssistantSeed finalAssistantSeedChosen = assistantSeedChosen;
            notifyObserver(obs -> obs.sendAssistantSeed(finalAssistantSeedChosen));
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
        int i=1;

        if(assistantCards.size()>=1){

            do {
                out.println("Please type the corresponding id to select one of the Assistant Cards: ");
                for (AssistantCard assistantCard : assistantCards) {
                    out.print(i +" ) ");
                    out.print("Value: " + assistantCard.getValue()+", ");
                    out.print("Max steps motherNature: " + assistantCard.getMaxStepsMotherNature() + "\n");
                    i++;
                }
                id= Integer.parseInt(readLine.nextLine());

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
    public void askMoveStud(List<PawnColor> pawnColors, List<Island> islands, SchoolBoard schoolBoard) throws TooManyPawnsPresent, NoPawnPresentException {
        int studentsToMove = 3;
        boolean validInput = false;
        out.println("Do you want to move some students in the dining room? Y/N");
        String playerAnswer = readLine.nextLine();
        do {
            if (playerAnswer.equals("Y")){
                askMoveStudToDining(pawnColors, schoolBoard, studentsToMove);
            }
            else if (studentsToMove > 0){
                askMoveStudToIsland(islands, schoolBoard, studentsToMove);
            }
        } while (!validInput);

    }

    @Override
    public int askMoveStudToDining(List<PawnColor> pawnColors, SchoolBoard schoolBoard, int studentsToMove) throws TooManyPawnsPresent {
        PawnColor color = null;
        String playerAnswer = null;
        boolean validInput = false;
        do {
            //fare ciclo per scegliere il colore e messaggio di errore nel caso non fosse disponibile
            schoolBoard.addStudToDining(color);
            studentsToMove--;
            if (studentsToMove > 0) {
                out.println("Do you want to move another student to the dining room? Y/N");
                playerAnswer = readLine.next();
            }
            else if (studentsToMove == 0) {
                validInput = true;
            }
        } while (playerAnswer.equals("Y") || !validInput);
        return studentsToMove;
    }

    @Override
    public void askMoveStudToIsland(List<Island> islands, SchoolBoard schoolBoard, int studentsToMove) throws NoPawnPresentException {
        PawnColor color = null;
        Island island = null;
        while (studentsToMove > 0) {
            //ciclo for con elenco e degli studenti per far selezionare lo studente e l'isola in questione
            schoolBoard.moveStudToIsland(color, island);
            studentsToMove--;
        }
    }


    @Override
    public void askMoveMotherNature(List<Island> islands, AssistantCard assistantCard) {
        boolean validInput = false;
        do {
            out.println("How many steps does Mother Nature take?");
            int inputSteps = readLine.nextInt();
            if (inputSteps <= assistantCard.getMaxStepsMotherNature() && inputSteps > 0) {
                //muovere madre natura nell'isola corrispondente
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
            }
        } while (!validInput);
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
        boolean expertVariant=false;
        out.print("Do you want to play the expert variant of the game? Y/N  ");
        String expertVariantAnswer = readLine.nextLine();
        do {
            if (expertVariantAnswer.equalsIgnoreCase("y") || expertVariantAnswer.equalsIgnoreCase("n")) {
                expertVariant= expertVariantAnswer.equalsIgnoreCase("y");
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
        boolean finalExpertVariant = expertVariant;
        notifyObserver(obs->obs.sendExpertVariant(finalExpertVariant));
    }

    @Override
    public void askPlayCharacterCard(List<CharacterCard> characterCards) {
        boolean validInput;
        int id;
        CharacterCard characterCardChosen = null;
        /*Forse si potrebbe fare un ciclo di controllo per vedere se si hanno abbastanza monete per giocare una delle 3 carte
        in modo da non far partire inutilmente la funzione a schermo*/
        out.print("Do you want to play a Character Card? Y/N ");
        String characterCardAnswer = readLine.nextLine();

        do {
            if (characterCardAnswer.equalsIgnoreCase("y")) {
                boolean validCost = false;
                do{
                    out.println("Please type the corresponding id to select one of the AssistantSeeds: ");
                    for(int i=0;i<characterCards.size();i++){
                        System.out.println((i+1)+": "+characterCards.get(i));
                    }

                    id= Integer.parseInt(readLine.nextLine());

                    if(id<=0 || id>characterCards.size()){
                        out.println(INVALID_INPUT);
                        validCost = false;
                    }
                    else{
                        characterCardChosen=characterCards.get(id-1);
                        validCost=true;
                    }

                } while (!validCost);
                validInput = true;
            }
            else if (characterCardAnswer.equalsIgnoreCase("n")) {
                characterCardChosen=null;
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
        CharacterCard finalCharacterCardChosen = characterCardChosen;
        notifyObserver(obs->obs.sendCharacterCard(finalCharacterCardChosen));
    }

    @Override
    public void showSchoolBoard(SchoolBoard schoolBoard){
        out.println("Dining Room:");
        for (PawnColor pawnColor: PawnColor.values()){
            out.print(pawnColor.getVisualColor()+pawnColor+Colors.RESET+" students: ");
            for (int i=0; i<schoolBoard.getStudentsDining().get(pawnColor); i++) {
                out.print(pawnColor.getVisualColor()+"X"+" ");
                if (schoolBoard.getProfessors().get(pawnColor)) {
                    out.print("  "+pawnColor.getVisualColor()+"P"+Colors.RESET);
                }
            }
            out.println("");
        }
        out.print("\nStudents in the Entrance: ");
        for (PawnColor pawnColor: PawnColor.values()){
            for (int i=0; i<schoolBoard.getStudentsWaiting().get(pawnColor); i++){
                out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
            }
        }
        out.println("\nNumber of coins: "+ schoolBoard.getNumCoins());
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
    public void showLoseMessage(Player winner) {
        out.println("Sorry, you have lost the game! \nThe winner is " + winner.getNickname() + "\nGame finished.");
        System.exit(0);
    }

    @Override
    public void showLoginInfo(String nickName, boolean nickNameOk, boolean connectionOk) {
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

    @Override
    public void askMoveStudToIsland(List<Island> islands) {

    }

    @Override
    public void askMoveStudToDining(List<PawnColor> pawnColors) {

    }

    @Override
    public void showIslands(List<Island> islands) {

        out.println("ISLANDS: ");

        for(Island island : islands){
            out.print("- Index: "+island.getIndex()+" ");
            out.print(" Students: ");
            for (PawnColor pawnColor: PawnColor.values()){
                for (int i=0; i<island.getStudents().get(pawnColor); i++){
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
            }
            if(island.isMotherNature()){
                out.print(" MotherNature here");
            }
            out.println("");
        }
    }

    @Override
    public void showGameBoard(List<Island> islands, List<Player> players) {
        out.println("\n"+Colors.RED_PAWN+"CURRENT GAME SITUATION: "+Colors.RESET);

        this.showIslands(islands);
        out.println("");
        for(Player player :players){
            out.println(player.getNickname()+": ");
            this.showSchoolBoard(player.getSchoolBoard());
            out.println("");
        }
        out.print("\n\n\033[38;2;255;255;0m");
    }
}
