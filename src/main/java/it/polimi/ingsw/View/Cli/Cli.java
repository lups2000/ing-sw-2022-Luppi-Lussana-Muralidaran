package it.polimi.ingsw.View.Cli;


import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.network.Messages.ServerSide.Islands;
import it.polimi.ingsw.observer.Observable4View;

import javax.swing.*;
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
        System.out.print("\033[H\033[2J");
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
        int numPlayers = 0;
        do {
            out.print("Insert number of players (max 3): ");
            try{
                numPlayers = Integer.parseInt(readLine.nextLine());
            }catch (NumberFormatException e){
                clearCli();
                validInput = false;
            }

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
        int id = -1;
        boolean validInput;
        if(assistantSeedAvailable.size()>=1){
            do{
                out.println("Please type the corresponding id to select one of the AssistantSeeds: "+Colors.RESET);
                System.out.print("( ");
                for(int i=0;i<assistantSeedAvailable.size();i++){
                    System.out.print((i+1)+": "+assistantSeedAvailable.get(i));
                    if(i< assistantSeedAvailable.size()-1){
                        System.out.print(", ");
                    }
                }
                System.out.println(" )");
                out.print("\033[38;2;255;255;0m");
                try{
                    id=Integer.parseInt(readLine.nextLine());
                }
                catch (NumberFormatException e){
                    validInput=false;
                }

                if(id<=0 || id>assistantSeedAvailable.size()){
                    out.println(INVALID_INPUT);
                    clearCli();
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
        boolean validInput=false;
        int id = -1;
        int i;
        if(assistantCards.size()>=1){

            do {
                i=1;
                out.println("Please type the corresponding id to select one of the Assistant Cards: ");
                out.println(Colors.RESET);
                for (AssistantCard assistantCard : assistantCards) {
                    out.print(i +" ) ");
                    out.print("Value: " + assistantCard.getValue()+", ");
                    out.print("Max steps motherNature: " + assistantCard.getMaxStepsMotherNature() + "\n");
                    i++;
                }
                out.print("\033[38;2;255;255;0m");
                try{
                    id= Integer.parseInt(readLine.nextLine());
                }
                catch (NumberFormatException e){
                    validInput=false;
                }

                if(id<=0 || id>assistantCards.size()){
                    out.println(INVALID_INPUT);
                    clearCli();
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
    public void askMoveMotherNature(List<Island> islands, int maxSteps) {
        boolean validInput = false;
        int inputSteps = 0;
        do {
            out.println("How many steps does Mother Nature take?(Max: "+maxSteps+" ):  ");
            this.showIslands(islands);
            try{
                inputSteps=Integer.parseInt(readLine.nextLine());
            }
            catch (NumberFormatException e){
                validInput=false;
            }
            if (inputSteps <= maxSteps && inputSteps > 0) {
                int finalInputSteps = inputSteps;
                notifyObserver(obs -> obs.sendMoveMotherNature(finalInputSteps));
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput=false;
            }
        } while (!validInput);
    }

    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {

        boolean validInput;
        int idCloudTile = -1;

        if(cloudTiles.size()>=1){

            do{
                out.println("Please type the corresponding id to select one of the CloudTiles: ");
                this.showCloudTiles(cloudTiles);
                out.println();
                try{
                    idCloudTile= Integer.parseInt(readLine.nextLine());
                }
                catch (NumberFormatException e){
                    validInput=false;
                }

                if(idCloudTile<0 || idCloudTile>cloudTiles.size()){
                    out.println(INVALID_INPUT);
                    clearCli();
                    validInput = false;
                }
                else{
                    validInput=true;
                    int finalIdCloudTile = idCloudTile;
                    notifyObserver(obs -> obs.sendCloudTile(finalIdCloudTile));
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

        do {
            out.print("Do you want to play the expert variant of the game? Y/N  ");
            String expertVariantAnswer = readLine.nextLine();

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
        int idCharacterCard=-1;
        CharacterCard characterCardChosen = null;

        do {
            out.print("Do you want to play a Character Card? Y/N ");
            String characterCardAnswer = readLine.nextLine();

            if (characterCardAnswer.equalsIgnoreCase("y")) {
                boolean validCost = false;
                do{
                    out.println("Please type the corresponding id to select one of the Character Cards: ");
                    for(int i=0;i<characterCards.size();i++){
                        System.out.println((i+1)+": "+characterCards.get(i));
                    }
                    try{
                        idCharacterCard=Integer.parseInt(readLine.nextLine());
                    }
                    catch (NumberFormatException e){
                        validCost=false;
                    }
                    if(idCharacterCard<=0 || idCharacterCard>characterCards.size()){
                        out.println(INVALID_INPUT);
                        clearCli();
                        validCost = false;
                    }
                    else{
                        validCost=true;
                    }

                } while (!validCost);
                validInput = true;
            }
            else if (characterCardAnswer.equalsIgnoreCase("n")) {
                idCharacterCard=-1;
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
        //qua devo mandare l'id quindi è da cambiare un po di roba
        int finalIdCharacterCard = idCharacterCard;
        notifyObserver(obs->obs.sendCharacterCard(finalIdCharacterCard));
    }

    @Override
    public void askMoveStud() {
        boolean validInput=false;
        String answer=null;

        do{
            out.print("\npress 'S' to move to your dining Room or 'I' to an island:   ");
            answer = readLine.nextLine();

            if (answer.equalsIgnoreCase("s") || answer.equalsIgnoreCase("i")) {
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        }while(!validInput);
        String finalAnswer = answer;
        notifyObserver(obs->obs.sendGenericMessage(finalAnswer));
    }

    @Override
    public void showSchoolBoardPlayers(List<Player> players){
        out.print(Colors.RESET);
        out.println("PLAYERS SCHOOLBOARDS:");
        for(Player player : players){
            out.println("\033[38;2;255;255;0m"+player.getNickname()+":"+Colors.RESET);
            out.println("Dining Room:");
            for (PawnColor pawnColor: PawnColor.values()){
                out.print(pawnColor.getVisualColor()+pawnColor+Colors.RESET+" students: ");
                for (int i=0; i<player.getSchoolBoard().getStudentsDining().get(pawnColor); i++) {
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
                if (player.getSchoolBoard().getProfessors().get(pawnColor)) {
                    out.print("  "+pawnColor.getVisualColor()+"P"+Colors.RESET);
                }
                out.println("");
            }
            out.print("\nWaiting Room/Entrance: ");
            for (PawnColor pawnColor: PawnColor.values()){
                for (int i=0; i<player.getSchoolBoard().getStudentsWaiting().get(pawnColor); i++){
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
            }
            out.println("\nNumber of coins: "+ player.getSchoolBoard().getNumCoins());
            out.println("Number of available towers: "+player.getSchoolBoard().getNumTowers()+"\n");
        }
        out.print("\033[38;2;255;255;0m");
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    @Override
    public void showError(String error) {
        out.println(Colors.RED_PAWN+error+"\n"+"EXIT...\033[38;2;255;255;0m");
        System.exit(1);
    }

    @Override
    public void showLobby(List<Player> players, int numPlayers) {
        out.println("Lobby: "+players.size()+" / "+numPlayers);
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
        out.println("\n\nCongratulations "+winner.getNickname()+" you have won the game!Game finished.");
        System.exit(0);
    }

    @Override
    public void showLoseMessage(Player winner) {
        out.println("Sorry, you have lost the game! The winner is " + winner.getNickname() + "!Game finished.");
        System.exit(0);
    }

    @Override
    public void showDisconnection(String nickName, String message) {
        out.println("\n"+Colors.RED_PAWN+nickName+message+"\033[38;2;255;255;0m");
        System.exit(1);
    }

    @Override
    public void showLoginInfo(String nickName, boolean nickNameOk, boolean connectionOk) {
        clearCli();

        if(nickNameOk && connectionOk){
            out.println(Colors.RESET+"Nice to meet you "+nickName+", now you are connected!"+"\033[38;2;255;255;0m");
        }
        else if(nickNameOk){
            out.println(Colors.RED_PAWN+"We are sorry but the connection has been refused!Try later!"+"\033[38;2;255;255;0m");
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
    public void askMoveStudToIsland(Map<PawnColor,Integer> studentsWaiting,List<Island> islands) {
        boolean validInput=false;
        boolean validIndex=false;
        int indexIsland=-1;
        PawnColor pawnColorChosen=null;
        String answerColor;
        do{
            out.print(Colors.RESET);
            out.println("Students in the Entrance... ");
            for(PawnColor pawnColor : studentsWaiting.keySet()){
                out.print(pawnColor.getVisualColor()+pawnColor+Colors.RESET+" students: ");
                for(int i=0;i<studentsWaiting.get(pawnColor);i++){
                    out.print(pawnColor.getVisualColor()+" X "+Colors.RESET);
                }
                out.println("");
            }
            out.print("\033[38;2;255;255;0m");
            out.print("Select one color(ex. red,blue...) to move the student to an Island : ");
            answerColor= readLine.nextLine();

            if(answerColor.equalsIgnoreCase("red") || answerColor.equalsIgnoreCase("blue") || answerColor.equalsIgnoreCase("pink") ||
                    answerColor.equalsIgnoreCase("green") || answerColor.equalsIgnoreCase("yellow")){

                if(answerColor.equalsIgnoreCase("red")){
                    pawnColorChosen=PawnColor.RED;
                }
                else if(answerColor.equalsIgnoreCase("blue")){
                    pawnColorChosen=PawnColor.BLUE;
                }
                else if(answerColor.equalsIgnoreCase("pink")){
                    pawnColorChosen=PawnColor.PINK;
                }
                else if(answerColor.equalsIgnoreCase("yellow")){
                    pawnColorChosen=PawnColor.YELLOW;
                }
                else{
                    pawnColorChosen=PawnColor.GREEN;
                }

                if(studentsWaiting.get(pawnColorChosen)<=0){
                    out.println(INVALID_INPUT);
                    clearCli();
                    validInput=false;
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
                validInput=false;
            }

            do{
                out.println("Please type the corresponding index to select one of the Islands: ");
                this.showIslands(islands);
                try{
                    indexIsland=Integer.parseInt(readLine.nextLine());
                }catch (NumberFormatException e){
                    validIndex=false;
                }
                if(indexIsland>=0 && indexIsland<islands.size()){ //da controllare bene qua
                    validIndex=true;
                }
                else{
                    out.println(INVALID_INPUT);
                    clearCli();
                    validIndex=false;
                }
            }while(!validIndex);

        }while(!validInput);
        out.print("\033[38;2;255;255;0m");
        PawnColor finalPawnColorChosen = pawnColorChosen;
        int finalIndexIsland = indexIsland;
        notifyObserver(obs->obs.sendStudentToIsland(finalPawnColorChosen, finalIndexIsland));

    }

    @Override
    public void askMoveStudToDining(SchoolBoard schoolBoard) {
        boolean validInput=false;
        String answerColor;
        PawnColor pawnColorChosen=null;

        do{
            out.print(Colors.RESET);
            out.println("YOUR SCHOOLBOARD:\n");
            out.println("Students in your Waiting Room/Entrance... ");
            for(PawnColor pawnColor : schoolBoard.getStudentsWaiting().keySet()){
                out.print(pawnColor.getVisualColor()+pawnColor+Colors.RESET+" students: ");
                for(int i=0;i<schoolBoard.getStudentsWaiting().get(pawnColor);i++){
                    out.print(pawnColor.getVisualColor()+" X "+Colors.RESET);
                }
                out.println("");
            }
            out.println("Students in your Dining Room... ");
            for (PawnColor pawnColor: PawnColor.values()){
                out.print(pawnColor.getVisualColor()+pawnColor+Colors.RESET+" students: ");
                for (int i=0; i<schoolBoard.getStudentsDining().get(pawnColor); i++) {
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
                if (schoolBoard.getProfessors().get(pawnColor)) {
                    out.print("  "+pawnColor.getVisualColor()+"P"+Colors.RESET);
                }
                out.println("");
            }
            out.print("\033[38;2;255;255;0m");
            out.print("Select one color(ex. red,blue...) to move the student to the Dining : ");
            answerColor= readLine.nextLine();


            if(answerColor.equalsIgnoreCase("red") || answerColor.equalsIgnoreCase("blue") || answerColor.equalsIgnoreCase("pink") ||
                answerColor.equalsIgnoreCase("green") || answerColor.equalsIgnoreCase("yellow")){

                if(answerColor.equalsIgnoreCase("red")){
                    pawnColorChosen=PawnColor.RED;
                }
                else if(answerColor.equalsIgnoreCase("blue")){
                    pawnColorChosen=PawnColor.BLUE;
                }
                else if(answerColor.equalsIgnoreCase("pink")){
                    pawnColorChosen=PawnColor.PINK;
                }
                else if(answerColor.equalsIgnoreCase("yellow")){
                    pawnColorChosen=PawnColor.YELLOW;
                }
                else{
                    pawnColorChosen=PawnColor.GREEN;
                }

                if(schoolBoard.getStudentsWaiting().get(pawnColorChosen)<=0){
                    out.println(INVALID_INPUT);
                    clearCli();
                    validInput=false;
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
                validInput=false;
            }
        }while(!validInput);
        PawnColor finalPawnColorChosen = pawnColorChosen;
        notifyObserver(obs->obs.sendStudentToDining(finalPawnColorChosen));

    }

    @Override
    public void showIslands(List<Island> islands) {

        out.print(Colors.RESET);
        out.println("ISLANDS: ");
        for(int id=0;id<islands.size();id++){
            out.print("- Index: "+islands.get(id).getIndex()+" ");
            out.print(" Students: ");
            for (PawnColor pawnColor: PawnColor.values()){
                for (int i=0; i<islands.get(id).getStudents().get(pawnColor); i++){
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
            }
            for(int i=0;i<islands.get(id).getNumTowers();i++){
                out.print(islands.get(id).getTower().getVisualColor()+" T ");
            }
            out.print(Colors.RESET);
            if(islands.get(id).isMotherNature()){
                out.print(" MotherNature here");
            }
            if(id<islands.size()-1){
                out.println("");
            }
            //Entry tiles TODO
        }
        out.println("\033[38;2;255;255;0m");
    }


    @Override
    public void showGameBoard(List<Island> islands, List<CloudTile> cloudTiles, List<Player> players) {
        this.clearCli();
        out.println("\n"+Colors.RED_PAWN+"CURRENT GAME SITUATION: "+Colors.RESET);
        this.showIslands(islands);
        out.print("");
        this.showCloudTiles(cloudTiles);
        out.println("\n");
        this.showSchoolBoardPlayers(players);

        out.println("\033[38;2;255;255;0m");
    }

    @Override
    public void showCloudTiles(List<CloudTile> cloudTiles) {
        out.print(Colors.RESET);
        out.println("\nCLOUD TILES: ");
        for(int id=0;id<cloudTiles.size();id++){
            out.print("- Index: "+cloudTiles.get(id).getId()+" ");
            out.print(" Students: ");
            for (PawnColor pawnColor: PawnColor.values()){
                for (int i=0; i<cloudTiles.get(id).getStudents().get(pawnColor); i++){
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
            }
            if(id<cloudTiles.size()-1){
                out.println("");
            }
        }
        out.print("\033[38;2;255;255;0m");
    }
}
