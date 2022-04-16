package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.*;

import java.util.*;

/**
 * class Game is a Singleton class
 * @author Paolo Lussana,Matteo Luppi
 */
public class Game {
    private static Game single_instance = null;
    private int maxNumPlayers;
    private List<Player> players; //2-4
    private boolean expertsVariant;
    private List<CharacterCard> characterCards; //list with the three random character cards
    private GameState status;
    private List<AssistantSeed> seedsAvailable; //4 seeds that can be chosen by the players
    private List<Island> islands; //initially 12
    private StudentBag studentBag;
    private int motherNature;
    private List<CloudTile> cloudTiles; //2-4
    private DeckCharacterCard deckCharacterCard;
    private Player firstPlayer;


    private Game(){
        this.players = new ArrayList<>();
        this.status = GameState.CREATING;
        this.seedsAvailable=new ArrayList<>(Arrays.asList(AssistantSeed.KING,AssistantSeed.SAMURAI,AssistantSeed.WITCH,AssistantSeed.WIZARD));
        this.islands = new ArrayList<>();
        fillIslands();
        this.studentBag = new StudentBag();
        this.motherNature = 0;
        this.deckCharacterCard=new DeckCharacterCard();
    }

    /**
     * method to initialize the Game
     * @param max indicates the max number of players chosen at the very start by the first player when he creates the match
     * @param experts indicates if the first player chooses to play the game with the experts variant or not
     */
    public void initGame(int max,boolean experts) throws NoPawnPresentException{
        this.maxNumPlayers = max;
        this.expertsVariant = experts;
        if(expertsVariant){
            characterCards=deckCharacterCard.pickThreeRandomCards();
        }

        this.cloudTiles = new ArrayList<>();
        for(int i=0;i<max;i++){
            CloudTile newCloud = new CloudTile(i);
            cloudTiles.add(i,new CloudTile(i));
            fillCloudTile(newCloud);
        }
    }

    /**
     * method for the Singleton pattern
     */
    public static Game getInstance(){
        if(single_instance == null){
            single_instance = new Game();
        }
        return single_instance;
    }


    /**
     * method invoked every time a new player tries to connect with the server
     * @param nickname is the nickname the player chooses when he registers himself
     * @param chosenSeed is the wizard selected by this player for the choice of the assistant cards' deck
     */
    public void addPlayer(String nickname,AssistantSeed chosenSeed) throws NoPawnPresentException,TooManyPawnsPresent{
        if(players.size() == maxNumPlayers){
            throw new IllegalStateException("Too many players");
        }
        Player newPlayer = new Player(players.size(),nickname,chosenSeed);
        newPlayer.createDeck(chosenSeed);
        players.add(players.size(),newPlayer);
        seedsAvailable.remove(chosenSeed);
        fillBoard(newPlayer);
        if(players.size() == 1){
            //at the first round we decide by default that the first player will be the first to log in the game
            firstPlayer = newPlayer;
        }
    }

    public GameState getStatus() {return status;}
    public List<Player> getPlayers() {return players;}
    public int getMaxNumPlayers() {return maxNumPlayers;}
    public boolean getExpertsVariant(){return expertsVariant;}
    public void changeStatus(GameState status){this.status = status;}
    public StudentBag getStudentBag() {return studentBag;}
    public List<CloudTile> getCloudTiles() {return cloudTiles;}

    /**
     * method invoked one time for each player at the start of the game that fills his school board
     * @param player is the player who just entered the match, his school board will be filled
     */
    private void fillBoard(Player player) throws NoPawnPresentException,TooManyPawnsPresent{
        for(int i=0;i<player.getSchoolBoard().getNumMaxStudentsWaiting();i++){
            PawnColor sorted = studentBag.drawStudent();
            player.getSchoolBoard().addStudToWaiting(sorted);
        }
    }

    /**
     * method invoked when the cloud tiles need to be refilled
     * @param cloud is the cloud tile to be filled
     */
    public void fillCloudTile(CloudTile cloud) throws NoPawnPresentException{
        for(int i=0;i<cloud.getMaxNumStudents();i++){
            PawnColor sorted = studentBag.drawStudent();
            cloud.addStudent(sorted);
        }
    }

    /**
     * method to fill the islands at the beginning of the game
     * it's needed to distribute a total of 10 students, 2 per color, in 10 islands (n.0 and n.6 excluded)
     */
    private void fillIslands() {
        Map<PawnColor, Integer> availableStudents = new HashMap<>();
        availableStudents.put(PawnColor.RED, 2);
        availableStudents.put(PawnColor.BLUE, 2);
        availableStudents.put(PawnColor.YELLOW, 2);
        availableStudents.put(PawnColor.PINK, 2);
        availableStudents.put(PawnColor.GREEN, 2);

        for (int i = 0; i < 12; i++) {
            PawnColor pawnColor=null;
            Island newIsland=new Island(i);
            if(i!=0 && i!=6){
                for(int j=0;j<1;j++){
                    int rand=(int) (Math.random() * 5);

                    for(PawnColor p : PawnColor.values()){
                        if(p.ordinal()==rand){
                            pawnColor=p;
                            break;
                        }
                    }
                    if(availableStudents.get(pawnColor)>=1){
                        availableStudents.put(pawnColor,availableStudents.get(pawnColor)-1);
                        newIsland.addStudent(pawnColor);
                    }
                    else{
                        //if there aren't students of color 'pawncolor'
                        j--;
                    }
                }
            }
            islands.add(newIsland);
        }
    }

    /**
     * method to move mother nature
     * @param newIndex is the island's index on which mother nature will stop
     */
    public void moveMotherNature(int newIndex) throws TooManyTowersException,NoTowersException{
        this.motherNature = newIndex;
        influence(newIndex);
    }
    //e se gli passassimo l'oggetto ???
    /*
    public void moveMotherNature(Island island) throws TooManyTowersException,NoTowersException{
        this.motherNature = island.getIndex();
        island.setMotherNature(true);
        influence(newIndex);
    }
     */



    /**
     * method to compute which player has more influence on an island
     * @param islandIndex is the island's index (or group of islands) on which we are computing the influence
     */
    public void influence(int islandIndex) throws TooManyTowersException,NoTowersException{
        //if there is a no entry tile on the island the influence is not computed and one no entry tile will be removed
        if(islands.get(islandIndex).getEntryTiles() == 0) {
            int maxInfluence = 0;
            Player winner = players.get(0); //by default
            //"previousOwner" is the player who previously had tower(s) on the island (if there is one)
            Player previousOwner = null;
            //if it happens a draw between the influences of two (or more) players on an island no action is needed
            boolean drawInfluence = false;

            for (Player player : players) {
                if((islands.get(islandIndex).getTower()).equals(player.getColorTower())){
                    previousOwner = player;
                }
                if(maxInfluence < islands.get(islandIndex).computeInfluence(player)) {
                    maxInfluence = islands.get(islandIndex).computeInfluence(player);
                    winner = player;
                    drawInfluence = false;
                }
                else if(maxInfluence != 0 && maxInfluence == islands.get(islandIndex).computeInfluence(player)){
                    drawInfluence = true;
                }
            }

            //if the winner is the same player who had already the towers on this island no action is needed
            if(!drawInfluence && maxInfluence > 0 && !(winner.equals(previousOwner))){
                //if there were already some towers present on the island it means that they are supposed to return
                // to the school board of theirs owner
                if(previousOwner != null){
                    previousOwner.getSchoolBoard().addTowers(islands.get(islandIndex).getNumTowers());
                }
                //whether or not there were already towers on the island these following instructions must be done
                islands.get(islandIndex).changeTower(winner.getColorTower());
                //we remove from the school board the towers that will be placed on the island
                winner.getSchoolBoard().addTowers((islands.get(islandIndex).getNumTowers())*(-1));
                checkArchipelago(islandIndex);
            }
        }
        //da controllare se un giocatore nel costruire nuovi torri non finisce le sue presenti nella plancia
        //in caso -> FINE PARTITA E VITTORIA DI QUEL PLAYER

        else{
            islands.get(islandIndex).setEntryTiles(-1);
        }
    }
    /*
    public void influence(Island island) throws TooManyTowersException,NoTowersException{
        int islandIndex=island.getIndex();
        //if there is a no entry tile on the island the influence is not computed and one no entry tile will be removed
        if(islands.get(islandIndex).getEntryTiles() == 0) {
            int maxInfluence = 0;
            Player winner = players.get(0); //by default
            //"previousOwner" is the player who previously had tower(s) on the island (if there is one)
            Player previousOwner = null;
            //if it happens a draw between the influences of two (or more) players on an island no action is needed
            boolean drawInfluence = false;

            for (Player player : players) {
                if((islands.get(islandIndex).getTower()).equals(player.getColorTower())){
                    previousOwner = player;
                }
                if(maxInfluence < islands.get(islandIndex).computeInfluence(player)) {
                    maxInfluence = islands.get(islandIndex).computeInfluence(player);
                    winner = player;
                    drawInfluence = false;
                }
                else if(maxInfluence != 0 && maxInfluence == islands.get(islandIndex).computeInfluence(player)){
                    drawInfluence = true;
                }
            }

            //if the winner is the same player who had already the towers on this island no action is needed
            if(!drawInfluence && maxInfluence > 0 && !(winner.equals(previousOwner))){
                //if there were already some towers present on the island it means that they are supposed to return
                // to the school board of theirs owner
                if(previousOwner != null){
                    previousOwner.getSchoolBoard().addTowers(islands.get(islandIndex).getNumTowers());
                }
                //whether or not there were already towers on the island these following instructions must be done
                islands.get(islandIndex).changeTower(winner.getColorTower());
                //we remove from the school board the towers that will be placed on the island
                winner.getSchoolBoard().addTowers((islands.get(islandIndex).getNumTowers())*(-1));
                checkArchipelago(islandIndex);
            }
        }
        //da controllare se un giocatore nel costruire nuovi torri non finisce le sue presenti nella plancia
        //in caso -> FINE PARTITA E VITTORIA DI QUEL PLAYER

        else{
            islands.get(islandIndex).setEntryTiles(-1);
        }
    }
     */

    /**
     * method to check if there is a union between two islands (or two group of islands)
     * this method is invoked each time a new tower(s) is placed on an island
     * @param index is the island's index on which is placed the new tower(s)
     */
    private void checkArchipelago(int index){
        if(islands.get(index).getTower().equals(islands.get((index+1)%(Island.getNumIslands())).getTower())){
            islands.get(index).merge(islands.get((index+1)%(Island.getNumIslands())));
            updateIndexes((index+1)%(Island.getNumIslands()));
        }
        if(islands.get(index).getTower().equals(islands.get((index-1)%(Island.getNumIslands())).getTower())){
            islands.get((index-1)%(Island.getNumIslands())).merge(islands.get(index));
            updateIndexes(index);
        }

        if(Island.getNumIslands() <= 3){
            checkWinner();
        }
    }
    /* passare l'oggetto?
    private void checkArchipelago(Island island){
        int index=island.getIndex();
        if(islands.get(index).getTower().equals(islands.get((index+1)%(Island.getNumIslands())).getTower())){
            islands.get(index).merge(islands.get((index+1)%(Island.getNumIslands())));
            updateIndexes((index+1)%(Island.getNumIslands()));
        }
        if(islands.get(index).getTower().equals(islands.get((index-1)%(Island.getNumIslands())).getTower())){
            islands.get((index-1)%(Island.getNumIslands())).merge(islands.get(index));
            updateIndexes(index);
        }
        if(Island.getNumIslands() <= 3){
            checkWinner();
        }
    }
    */

    /**
     * method to update the indexes of the ArrayList islands, with a left shift of  indexes removing the island
     * that will be merged within the next one
     * this method is invoked each time there is the creation of a new archipelago
     * @param removedIndex is the index of the island that need to be merged (and so removed from the ArrayList)
     */
    private void updateIndexes(int removedIndex){
        for(int i=removedIndex;i<Island.getNumIslands();i++){
            //to update "index", attribute of the object Island
            islands.get(i+1).setIndex(i);
            //to update the index of the ArrayList "islands"
            islands.set(i,islands.get(i+1));
        }
        islands.remove(Island.getNumIslands());
    }

    /**
     * method invoked each time there are movements in the players' school boards
     * it checks if a new player has gained some professors with his recent moves
     */
    public void allocateProfessors() throws NoPawnPresentException,TooManyPawnsPresent{
        for(PawnColor color : PawnColor.values()) {
            Player winner = players.get(0); //by default
            //"previousOwner" is the player who previously had a certain professor (if there is one)
            Player previousOwner = null;
            int maxStudents = 0;
            //if there is a draw the previous owner keeps his professor, so no action is needed
            boolean draw = false;

            for (Player player : players) {
                if(player.getSchoolBoard().getProfessors().get(color)){
                    previousOwner = player;
                }
                if(player.getSchoolBoard().getStudentsDining().get(color) > maxStudents){
                    winner = player;
                    maxStudents = player.getSchoolBoard().getStudentsDining().get(color);
                    draw = false;
                }
                else if(maxStudents != 0 && player.getSchoolBoard().getStudentsDining().get(color) == maxStudents){
                    draw = true;
                }
            }
            if(previousOwner != null) {
                if (!draw && !(winner.equals(previousOwner))) {
                    previousOwner.getSchoolBoard().removeProfessor(color);
                    winner.getSchoolBoard().addProfessor(color);
                }

            }
            else {
                //if there was no previous owner there cannot be a draw
                if (maxStudents != 0) {
                    winner.getSchoolBoard().addProfessor(color);
                }
            }
        }
    }

    /**
     * method invoked when the number of islands is 3 (or lower) or when the students in the student bag are finished
     * it checks who is the winner of the just finished game by checking who has the most towers built in the islands
     * and, in case of a draw with this first check, it checks who between them has the most professors
     */
    public void checkWinner(){
        //if there is a draw in the number of towers we have to check the number of professors owned
        boolean draw = false;
        Player winner = players.get(0); //by default
        int maxTowers = 0;
        //list of players who has the same (and higher) number of towers built on the islands
        List<Player> drawPlayers = new ArrayList<>();
        drawPlayers.add(0,players.get(0));  //by default

        for(Player player : players) {
            int sum = 0;
            for (Island island : islands) {
                if((island.getTower()).equals(player.getColorTower())){
                    sum = sum + island.getNumTowers();
                }
            }
            if(sum > maxTowers){
                winner = player;
                draw = false;
                maxTowers = sum;
                drawPlayers.clear();
                drawPlayers.add(players.indexOf(player),player);
            }
            else if(sum != 0 && sum == maxTowers){
                draw = true;
                drawPlayers.add(players.indexOf(player),player);
            }
        }

        if(draw){
            int maxProfessors = 0;
            for(Player player : drawPlayers){
                int sum = 0;
                Map<PawnColor,Boolean> professors = player.getSchoolBoard().getProfessors();
                for(PawnColor color : PawnColor.values()){
                    if(professors.get(color)){
                        sum++;
                    }
                }
                if(sum > maxProfessors){
                    maxProfessors = sum;
                    winner = player;
                }
            }
        }
        winner.setStatus(PlayerStatus.WINNER);
        this.status = GameState.ENDED;
    }

    //METODO PER CALCOLARE IL PRIMO GIOCATORE AD OGNI TURNO TODO
}