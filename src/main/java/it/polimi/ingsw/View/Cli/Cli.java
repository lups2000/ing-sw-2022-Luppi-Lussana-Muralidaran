package it.polimi.ingsw.View.Cli;


import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.View.View;
import it.polimi.ingsw.observer.Observable4View;

import java.awt.*;
import java.io.PrintStream;
import java.util.*;
import java.util.List;


/**
 * @author Pradeeban Muralidaran,Matteo Luppi
 */
public class Cli extends Observable4View implements View {
    private final PrintStream out;
    private static final String INVALID_INPUT = Colors.RED_PAWN+"The entered input is not valid!"+"\033[38;2;255;255;0m";
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
        //welcomeMessage += new String(Character.toChars(0x1F604));
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
        boolean validInput = false;

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

    /**
     * method to ask the first player the number of players
     */
    @Override
    public void askNumPlayers() {
        boolean validInput = false;
        int numPlayers = 0;
        do {
            out.print("Insert number of players (max 3): ");
            try{
                numPlayers = Integer.parseInt(readLine.nextLine());
            }catch (NumberFormatException e){
                clearCli();
            }

            if (numPlayers>=2 && numPlayers<=3)  {
                validInput = true;
                int finalNumPlayers = numPlayers;
                notifyObserver(obs->obs.sendNumPlayers(finalNumPlayers));
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
            }

        } while (!validInput);
    }

    /**
     * method to ask a player which assistant seed he wants to pick
     *
     * @param assistantSeedAvailable all the available assistant seeds
     */
    @Override
    public void askAssistantSeed(List<AssistantSeed> assistantSeedAvailable) {
        AssistantSeed assistantSeedChosen = null;
        int id = -1;
        boolean validInput = false;
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
                    id=-1;
                    clearCli();
                }

                if(id<=0 || id>assistantSeedAvailable.size()){
                    out.println(INVALID_INPUT);
                    clearCli();
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

    /**
     * method to ask a player which of his assistant cards he wants to play
     *
     * @param assistantCards all the available assistant cards of the player
     */
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
                    id=-1;
                    clearCli();
                }

                if(id<=0 || id>assistantCards.size()){
                    out.println(INVALID_INPUT);
                    clearCli();
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

    /**
     * method to ask a player where he wants to move mother nature
     *
     * @param islands the islands of the game
     * @param maxSteps the maximum amount of steps that mother nature can do, according to the assistant card played
     */
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
                inputSteps=-1;
                clearCli();
            }
            if (inputSteps <= maxSteps && inputSteps > 0) {
                int finalInputSteps = inputSteps;
                notifyObserver(obs -> obs.sendMoveMotherNature(finalInputSteps));
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
            }
        } while (!validInput);
    }

    /**
     * method to ask a player which cloud tile he wants to pick
     *
     * @param cloudTiles the cloud tiles of the game
     */
    @Override
    public void askChooseCloudTile(List<CloudTile> cloudTiles) {

        boolean validInput = false;
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
                    idCloudTile=-1;
                    clearCli();
                }

                if(idCloudTile<0 || idCloudTile>cloudTiles.size()){
                    out.println(INVALID_INPUT);
                    clearCli();

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

    /**
     * method to ask the first player if he wants to activate the experts variant or not
     */
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

    /**
     * method to ask a player to activate a character card
     *
     * @param characterCards the character cards of the game
     */
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
                        idCharacterCard=-1;
                        clearCli();
                    }
                    if(idCharacterCard<=0 || idCharacterCard>characterCards.size()){
                        out.println(INVALID_INPUT);
                        clearCli();
                    }
                    else{
                        validCost=true;
                    }

                } while (!validCost);
                validInput = true;
            }
            else if (characterCardAnswer.equalsIgnoreCase("n")) {
                validInput = true;
            }
            else {
                out.println(INVALID_INPUT);
                clearCli();
                validInput = false;
            }
        } while (!validInput);
        int finalIdCharacterCard = idCharacterCard;
        notifyObserver(obs->obs.sendCharacterCard(finalIdCharacterCard));
    }

    /**
     * method to ask a user to move a student from his entrance, the user can choose between moving it to his dining room or to an island
     */
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
            }
        }while(!validInput);
        String finalAnswer = answer;
        notifyObserver(obs->obs.sendGenericMessage(finalAnswer));
    }

    /**
     * method to show the school board of some (or all) players
     *
     * @param players the players whose school board will be shown
     */
    @Override
    public void showSchoolBoardPlayers(List<Player> players){
        out.print(Colors.RESET);
        out.println("\nSCHOOLBOARDS:\n");
        for(Player player : players){
            out.println("\033[38;2;255;255;0m"+player.getNickname()+"'s school board:"+Colors.RESET);
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

    /**
     * method to show a generic textual message
     *
     * @param genericMessage a generic textual message
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(Colors.RESET+genericMessage+"\033[38;2;255;255;0m");
    }

    /**
     * method to show an error message
     *
     * @param error the textual message of the error
     */
    @Override
    public void showError(String error) {
        out.println(Colors.RED_PAWN+error+"\n"+"EXIT...\033[38;2;255;255;0m");
        System.exit(1);
    }

    /**
     * method to show which and how many students are actually connected to the lobby
     * it is shown in the login phase while waiting that all the required players log in
     *
     * @param players the players actually connected
     * @param numPlayers the required number of players
     */
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

    /**
     * method to show at the end of the match to the player who won
     *
     * @param winner the winning player of the match
     */
    @Override
    public void showWinMessage(Player winner) {
        out.println("\n\nCongratulations "+winner.getNickname()+" you have won the game!Game finished.");
        System.exit(0);
    }

    /**
     * method to show at the end of the match to all the players who lost
     *
     * @param winner the winning player of the match
     */
    @Override
    public void showLoseMessage(Player winner) {
        out.println("\n\nSorry but you have lost! The winner is " + winner.getNickname() + "!Game finished.");
        System.exit(0);
    }

    /**
     * method to show a message of disconnection to the disconnected player
     *
     * @param nickName the player's nickname
     * @param message the textual message to be shown
     */
    @Override
    public void showDisconnection(String nickName, String message) {
        out.println("\n"+Colors.RED_PAWN+nickName+message+"\033[38;2;255;255;0m");
        System.exit(1);
    }

    /**
     * method to show a welcome message to a player who just logged in
     *
     * @param nickName the player's nickname
     * @param nickNameOk if the chosen nickname is allowed or not
     * @param connectionOk if the connection was successful or not
     */
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
            if(nickName==null){
                out.println(Colors.RESET+"Missing Information!"+"\033[38;2;255;255;0m");
            }
            else{
                out.println(Colors.RESET+"Sorry, nickName already taken!"+"\033[38;2;255;255;0m");
            }
            askNickName();
        }
        else{
            showError("Error reaching the Server!");
        }

    }

    /**
     * method to show the initial info of the game, as soon as it starts
     *
     * @param players all the players connected
     * @param experts a boolean that indicates if the experts variant has been activated or not
     * @param numPlayers the number of players of the game
     */
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

    /**
     * method to ask the user which student he wants to move from his entrance to an island
     *
     * @param studentsWaiting the player's students in his waiting room
     * @param islands the islands of the game that can be chosen
     */
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
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
            }
            if (validInput) {
                do{
                    out.println("Please type the corresponding index to select one of the Islands: ");
                    this.showIslands(islands);
                    try{
                        indexIsland=Integer.parseInt(readLine.nextLine());
                    }catch (NumberFormatException e){
                        indexIsland=-1;
                        clearCli();
                    }
                    if(indexIsland>=0 && indexIsland<islands.size()){ //da controllare bene qua
                        validIndex=true;
                    }
                    else{
                        out.println(INVALID_INPUT);
                        clearCli();
                    }
                }while(!validIndex);
            }
        }while(!validInput);
        out.print("\033[38;2;255;255;0m");
        PawnColor finalPawnColorChosen = pawnColorChosen;
        int finalIndexIsland = indexIsland;
        notifyObserver(obs->obs.sendStudentToIsland(finalPawnColorChosen, finalIndexIsland));

    }

    /**
     * method to ask a player to choose an island
     *
     * @param islands the islands that can be chosen
     */
    @Override
    public void askIsland(List<Island> islands){
        int indexIsland=-1;
        boolean validIndex=false;

        do{
            out.println("Please type the corresponding index to select one of the Islands: ");
            this.showIslands(islands);
            try{
                indexIsland=Integer.parseInt(readLine.nextLine());
            }catch (NumberFormatException e){
                indexIsland=-1;
                clearCli();
            }
            if(indexIsland>=0 && indexIsland<islands.size()){ //da controllare bene qua
                validIndex=true;
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
            }
        }while(!validIndex);
        //out.print("\033[38;2;255;255;0m");
        int finalIndexIsland = indexIsland;
        notifyObserver(obs->obs.sendChosenIsland(finalIndexIsland));
    }

    /**
     * method to ask a player to choose a student
     *
     * @param availableStudents the available students that can be chosen
     */
    @Override
    public void askColor(Map<PawnColor,Integer> availableStudents){
        boolean validInput=false;
        PawnColor pawnColorChosen=null;
        String answerColor;
        do{
            out.print(Colors.RESET);
            this.showStudents(availableStudents);
            out.print("\033[38;2;255;255;0m");
            out.print("\nSelect one color(ex. red,blue...): ");
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
                if(availableStudents.get(pawnColorChosen)<=0){
                    out.println(INVALID_INPUT);
                    clearCli();
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
            }
        }while(!validInput);

        //out.print("\033[38;2;255;255;0m");
        PawnColor finalPawnColorChosen = pawnColorChosen;
        notifyObserver(obs->obs.sendChosenColor(finalPawnColorChosen));
    }

    /**
     * method to ask the user which student he wants to move from his entrance to his dining room
     *
     * @param schoolBoard the player's school board
     */
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
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
            }
        }while(!validInput);
        PawnColor finalPawnColorChosen = pawnColorChosen;
        notifyObserver(obs->obs.sendStudentToDining(finalPawnColorChosen));

    }

    /**
     * method to show all the islands of the game
     *
     * @param islands the islands to be shown
     */
    @Override
    public void showIslands(List<Island> islands) {

        out.print(Colors.RESET);
        out.println("ISLANDS: ");
        for(int id=0;id<islands.size();id++){
            Island currentIsland = islands.get(id);
            out.print("- Index: "+currentIsland.getIndex()+" ");
            out.print(" Students: ");
            for (PawnColor pawnColor: PawnColor.values()){
                for (int i=0; i<currentIsland.getStudents().get(pawnColor); i++){
                    out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
                }
            }
            for(int i=0;i<currentIsland.getNumTowers();i++){
                out.print(currentIsland.getTower().getVisualColor()+" T ");
            }
            out.print(Colors.RESET);
            if(currentIsland.getNoEntryTiles()>0){
                for(int j=0;j<currentIsland.getNoEntryTiles();j++){
                    out.print(Colors.RED_PAWN+" NET"+Colors.RESET);
                }
            }
            if(currentIsland.isMotherNature()){
                out.print(" MotherNature here");
            }
            if(id<islands.size()-1){
                out.println("");
            }
        }
        out.println("\033[38;2;255;255;0m");
    }


    @Override
    public void showGameBoard(ReducedGame reducedGame) {
        this.clearCli();
        out.println("\n"+Colors.RED_PAWN+"CURRENT GAME SITUATION: "+Colors.RESET);
        this.showIslands(reducedGame.getIslands());
        out.print("");
        this.showCloudTiles(reducedGame.getCloudTiles());
        out.println("\n");
        this.showSchoolBoardPlayers(reducedGame.getPlayers());

        out.println("\033[38;2;255;255;0m");
    }

    /**
     * method to show the cloud tiles of the game
     *
     * @param cloudTiles the cloud tiles to be shown
     */
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

    /**
     * method to show all the students passed as a parameter
     *
     * @param students all the students to show
     */
    @Override
    public void showStudents(Map<PawnColor,Integer> students){
        out.print(Colors.RESET);
        out.println("\nSTUDENTS to pick: ");
        for (PawnColor pawnColor: PawnColor.values()){
            for (int i=0; i<students.get(pawnColor); i++){
                out.print(pawnColor.getVisualColor()+"X "+Colors.RESET);
            }
        }
        out.print("\033[38;2;255;255;0m");
    }

    /**
     * method to ask a student, with the possibility to answer stop
     * it is used for some of the character cards
     *
     * @param availableStudents all the available students that can be chosen
     */
    @Override
    public void askStudOrStop(Map<PawnColor,Integer> availableStudents){
        boolean validInput=false;
        PawnColor pawnColorChosen=null;
        String answerColor;
        boolean stop = false;
        do{
            //out.print(Colors.RESET);
            this.showStudents(availableStudents);
            //out.print("\033[38;2;255;255;0m");
            out.print("\nSelect one color(ex. red,blue...) or press 'x' to stop: ");
            answerColor= readLine.nextLine();

            if(answerColor.equalsIgnoreCase("red") || answerColor.equalsIgnoreCase("blue") || answerColor.equalsIgnoreCase("pink") ||
                    answerColor.equalsIgnoreCase("green") || answerColor.equalsIgnoreCase("yellow") || answerColor.equalsIgnoreCase("x")){

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
                else if(answerColor.equalsIgnoreCase("green")){
                    pawnColorChosen=PawnColor.GREEN;
                }
                else{
                    stop = true;
                }

                if(pawnColorChosen != null && availableStudents.get(pawnColorChosen)<=0){
                    out.println(INVALID_INPUT);
                    clearCli();
                }
                else{
                    validInput=true;
                }
            }
            else{
                out.println(INVALID_INPUT);
                clearCli();
            }
        }while(!validInput);

        //out.print("\033[38;2;255;255;0m");
        PawnColor finalPawnColorChosen = pawnColorChosen;
        boolean finalStop = stop;
        notifyObserver(obs->obs.sendChosenColorOrStop(finalPawnColorChosen, finalStop));
    }

    @Override
    public void updateFX(ReducedGame reducedGame) {

    }
}
