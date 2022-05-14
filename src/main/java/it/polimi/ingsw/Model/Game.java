package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.*;
import it.polimi.ingsw.Model.Exceptions.*;

import java.util.*;

/**
 * class Game
 * @author Paolo Lussana,Matteo Luppi,Pradeeban Muralidaran
 */

public class Game {
    private int maxNumPlayers;
    private List<Player> players; //2-4
    private boolean expertsVariant;
    private List<CharacterCard> characterCards; //list with the three random character cards
    private GameState status;
    private List<AssistantSeed> seedsAvailable; //4 seeds that can be chosen by the players
    private List<Island> islands; //initially 12
    private StudentBag studentBag;
    private int motherNature;
    private int noEntryTilesCounter; //counter used in the PutNoEntryTiles character card
    private List<CloudTile> cloudTiles; //2-4
    private Player firstPlayer;
    private List<SchoolBoard> schoolBoards;
    private boolean noCountTower;   //flag used for the NoCountTower character card
    private PawnColor noColorInfluence; //used for the NoColorInfluence character card
    private Map<Player,AssistantCard> currentHand;    //list for the assistant cards chosen in this turn


    public Game(){
        this.players = new ArrayList<>();
        this.status = GameState.CREATING;
        this.seedsAvailable=new ArrayList<>(Arrays.asList(AssistantSeed.KING,AssistantSeed.SAMURAI,AssistantSeed.WITCH,AssistantSeed.MAGICIAN));
        this.islands = new ArrayList<>();
        fillIslands();
        this.studentBag = new StudentBag();
        this.motherNature = 0;
        this.schoolBoards=new ArrayList<>();
        this.characterCards=new ArrayList<>();
        this.currentHand = new HashMap();
    }

    /**
     * method to initialize the Game
     * @param max indicates the max number of players chosen at the very start by the first player when he creates the match
     * @param experts indicates if the first player chooses to play the game with the experts variant or not
     */
    public void initGame(int max,boolean experts) throws TooManyPawnsPresent, NoPawnPresentException {
        if(max<2 || max>4){
            throw new IllegalArgumentException("The number of players must be between 2 and 4!");
        }
        else{
            this.maxNumPlayers = max;
            this.expertsVariant = experts;
            if(expertsVariant){
                this.noEntryTilesCounter = 4; //maximum amount of "No Entry Tiles" not in use
                this.noCountTower = false;
                this.noColorInfluence = null;
                for(int i=0;i<max;i++){
                    //creating 'max' schoolBoards for the game with expert variant
                    schoolBoards.add(i,new SchoolBoard(max,true)); //a schoolboard passo il numero max di studenti e experts=true
                }
                pickThreeRandomCards();
            }
            else{
                for(int i=0;i<max;i++){
                    //creating 'max' schoolBoards for the game with no expert variant
                    schoolBoards.add(i,new SchoolBoard(max,false)); //a schoolboard passo il numero max di studenti e experts=true
                }
            }
            this.cloudTiles = new ArrayList<>();
            for(int i=0;i<max;i++){
                CloudTile newCloud = new CloudTile(i,max);
                fillCloudTile(newCloud);
                cloudTiles.add(i,newCloud);
            }
        }
    }

    /**
     * method invoked every time a new player tries to connect with the server
     * @param nickname is the nickname the player chooses when he registers himself
     * @param chosenSeed is the wizard selected by this player for the choice of the assistant cards' deck
     */
    public void addPlayer(String nickname,AssistantSeed chosenSeed) throws TooManyPawnsPresent {
        if(!seedsAvailable.contains(chosenSeed)){
            throw new IllegalArgumentException("The seed has already been chosen!");
        }
        else if(players.size() == maxNumPlayers){
            throw new IllegalStateException("Too many players!");
        }
        else{
            Player newPlayer = new Player(players.size(),nickname,chosenSeed,schoolBoards.get(players.size())); //gli passo la schoolBoard,index playersize()
            players.add(players.size(),newPlayer);
            seedsAvailable.remove(chosenSeed);
            fillBoard(newPlayer);
            if(players.size() == 1){
                //at the first round we decide by default that the first player will be the first to log in the game
                firstPlayer = newPlayer;
            }
        }
    }

    public GameState getStatus() {return status;}
    public List<Player> getPlayers() {return players;}
    public boolean getExpertsVariant(){return expertsVariant;}
    public void changeStatus(GameState status){this.status = status;}
    public StudentBag getStudentBag() {return studentBag;}
    public List<SchoolBoard> getSchoolBoards() {return schoolBoards;}
    public List<CloudTile> getCloudTiles() {return cloudTiles;}
    public void setNoCountTower(){this.noCountTower = true;}
    public void setNoColorInfluence(PawnColor picked){this.noColorInfluence = picked;}
    public int getNoEntryTilesCounter(){return noEntryTilesCounter;}
    public void setNoEntryTilesCounter(int newNoEntryTilesNumber){this.noEntryTilesCounter = newNoEntryTilesNumber;}
    public List<CharacterCard> getCharacterCards() {return characterCards;}
    public List<AssistantSeed> getSeedsAvailable() {return seedsAvailable;}
    public List<Island> getIslands() {return islands;}
    public Player getFirstPlayer() {return firstPlayer;}
    public int getMotherNature() {return motherNature;}
    public Map<Player, AssistantCard> getCurrentHand() {return currentHand;}
    public int getMaxNumPlayers() {return maxNumPlayers;}

    /**
     * method invoked one time for each player at the start of the game that fills his school board
     * @param player is the player who just entered the match, his school board will be filled
     */
    private void fillBoard(Player player) throws TooManyPawnsPresent {
        for(int i=0;i<player.getSchoolBoard().getNumMaxStudentsWaiting();i++){
            try {
                PawnColor extracted = studentBag.drawStudent();
                player.getSchoolBoard().addStudToWaiting(extracted);
            } catch(NoPawnPresentException e){
                checkWinner();
            }
        }
    }

    /**
     * method invoked when the cloud tiles need to be refilled
     * @param cloud is the cloud tile to be filled
     */
    public void fillCloudTile(CloudTile cloud) throws TooManyPawnsPresent {

        for(int i=0;i<cloud.getMaxNumStudents();i++){
            try {
                PawnColor extracted = studentBag.drawStudent();
                cloud.addStudent(extracted);
            } catch(NoPawnPresentException e){
                //if there are no students remaining in the student bag the game is ended and we have to check who is the winner
                checkWinner();
            }
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
     * Method to move MotherNature to an Island
     * @param island is the island where I want to move Mother Nature on
     */
    public void moveMotherNature(Island island) throws TooManyTowersException, NoTowersException {
        if(island==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else{
            this.motherNature = island.getIndex();
            island.setMotherNature(true);
            for(Island island1 :islands){
                if(island1.getIndex()!=island.getIndex()){
                    island1.setMotherNature(false);
                }
            }
            influence(island);
        }
    }

    /**
     * Method to compute which player has more influence on an island
     * @param island is the one we are calculating the influence on
     */
    public void influence(Island island) throws TooManyTowersException,NoTowersException{
        int islandIndex=island.getIndex();
        //if there is a no entry tile on the island the influence is not computed and one no entry tile will be removed
        if(island.getNoEntryTiles() == 0) {
            int maxInfluence = 0;
            Player winner = players.get(0); //by default the first player
            //"previousOwner" is the player who previously had tower(s) on the island (if there is one)
            Player previousOwner = null;
            //if it happens a draw between the influences of two (or more) players on an island no action is needed
            boolean drawInfluence = false;

            for (Player player : players) {
                if((player.getColorTower()).equals(island.getTower())){
                    previousOwner = player;
                }

                int playerInfluence = 0;
                //if the flag TwoAdditionalPoints is true it means that this player has two more points
                if(player.isTwoAdditionalPoints()){
                    playerInfluence = 2;
                    player.setTwoAdditionalPoints(false);
                }

                if(!noCountTower) {
                    if (noColorInfluence == null) {
                        playerInfluence = playerInfluence + island.computeTotalInfluence(player);
                    }
                    else{
                        playerInfluence = playerInfluence + island.computeNoColorInfluence(player,noColorInfluence);
                        playerInfluence = playerInfluence + island.computeTowerInfluence(player);
                        noColorInfluence = null;
                    }
                }
                //if the flag noCountTower is true it means that in the computation of the influence we don't have to calculate the towers
                else{
                    if(noColorInfluence == null) {
                        playerInfluence = playerInfluence + island.computeStudentsInfluence(player);
                    }
                    else{
                        playerInfluence = playerInfluence + island.computeNoColorInfluence(player,noColorInfluence);
                        noColorInfluence = null;
                    }
                }
                if(maxInfluence < playerInfluence) {
                    maxInfluence = playerInfluence;
                    winner = player;
                    drawInfluence = false;
                }
                else if(maxInfluence != 0 && maxInfluence == playerInfluence){
                    drawInfluence = true;
                }
            }
            this.noCountTower = false;

            //if the winner is the same player who had already the towers on this island no action is needed
            if(!drawInfluence && maxInfluence > 0 && !(winner.equals(previousOwner))){
                //if there were already some towers present on the island it means that they are supposed to return
                // to the school board of theirs owner
                if(previousOwner != null){
                    previousOwner.getSchoolBoard().updateNumberOfTowers(island.getNumTowers());
                }

                //whether or not there were already towers on the island these following instructions must be done
                island.changeTower(winner.getColorTower());
                //we remove from the school board the towers that will be placed on the island
                try {
                    winner.getSchoolBoard().updateNumberOfTowers((island.getNumTowers()) * (-1));
                } catch(NoTowersException e) {
                    //if this exception is thrown it means that the player "winner" has finished his towers, so he has won the game
                    winner.setStatus(PlayerStatus.WINNER);
                    this.status = GameState.ENDED;
                }
                checkArchipelago(island);
            }
        }
        else{
            island.setNoEntryTiles(-1);
            setNoEntryTilesCounter(getNoEntryTilesCounter()+1);
        }
    }

    /**
     * method to check if there is a union between two islands (or two group of islands)
     * @param island is the one where a new tower has just been built
     */
    private void checkArchipelago(Island island){
        int index=island.getIndex();
        if(islands.get(index).getTower().equals(islands.get((index+1)%(Island.getNumIslands())).getTower())){
            islands.get(index).merge(islands.get((index+1)%(Island.getNumIslands())));
            updateIndexes((index+1)%(Island.getNumIslands()));
        }
        if(islands.get(index).getTower().equals(islands.get((index-1+Island.getNumIslands())%(Island.getNumIslands())).getTower())){
            islands.get((index-1+Island.getNumIslands())%(Island.getNumIslands())).merge(islands.get(index));
            updateIndexes(index);
        }
        if(Island.getNumIslands() <= 3){
            checkWinner();
        }
    }


    /**
     * method to update the indexes of the ArrayList islands, with a left shift of  indexes removing the island
     * that will be merged within the next one
     * this method is invoked each time there is the creation of a new archipelago
     * @param removedIndex is the index of the island that need to be merged (and so removed from the ArrayList)
     */
    private void updateIndexes(int removedIndex){
        if(removedIndex<0 || removedIndex>Island.getNumIslands()-1){
            throw new IllegalArgumentException("The index must be between 0 and the ('NumIsland'-1)!");
        }
        else{
            for(int i=removedIndex;i<Island.getNumIslands();i++){
                //to update "index", attribute of the object Island
                islands.get(i+1).setIndex(i);
                //to update the index of the ArrayList "islands"
                islands.set(i,islands.get(i+1));
            }
            islands.remove(Island.getNumIslands());
        }
    }

    /**
     * method invoked each time there are movements in the players' school boards
     * it checks if a new player has gained some professors with his recent moves
     */
    public void allocateProfessors() throws NoPawnPresentException,TooManyPawnsPresent{
        for(PawnColor color : PawnColor.values()) {
            Player winner = players.get(0); //by default the first one
            //"previousOwner" is the player who previously had a certain professor (if there is one)
            Player previousOwner = null;
            int maxStudents = 0;
            //if there is a draw the previous owner keeps his professor, so no action is needed
            boolean draw = false;
            //flag to see if the current winner in the for is the player who has played the ControlOnProfessor character card
            boolean winningControlOnProfessor = false;

            for (Player player : players) {
                if(player.getSchoolBoard().getProfessors().get(color)){
                    previousOwner = player;
                    maxStudents=player.getSchoolBoard().getStudentsDining().get(color); //aggiunto
                }
                if(player.getSchoolBoard().getStudentsDining().get(color) > maxStudents){
                    winner = player;
                    maxStudents = player.getSchoolBoard().getStudentsDining().get(color);
                    draw = false;

                    winningControlOnProfessor = player.getControlOnProfessor();

                }
                //this "else if" branch expresses a draw but NOT in the case if the current winner is the player who has played the ControlOnProfessor character card
                //because if this was the case the winner must remain that player
                else if(maxStudents != 0 && player.getSchoolBoard().getStudentsDining().get(color) == maxStudents && !winningControlOnProfessor){
                    if (!player.getControlOnProfessor()){
                        draw = true;
                    }
                    else {
                        //if the character card ControlOnProfessor is activated, with a draw the professor is taken by the player who played it
                        winner = player;
                        draw = false;
                    }
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
        for (Player player : players){
            if (player.getControlOnProfessor()){
                player.setControlOnProfessor(false);
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
        Player winner = players.get(0); //by default the first
        int maxTowers = 0;
        //list of players who has the same (and higher) number of towers built on the islands
        List<Player> drawPlayers = new ArrayList<>();
        drawPlayers.add(0,players.get(0));  //by default

        for(Player player : players) {
            int sum = 0;
            for (Island island : islands) {
                if((player.getColorTower()).equals(island.getTower())){
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

    /**
     * method to extract three random (and different) character cards if the expert variant is chosen
     */
    private void pickThreeRandomCards() throws NoPawnPresentException{
        int[] extracted = new int[3];
        boolean duplicate = false;
        for(int i=0;i<3;i++){
            int rand=(int) (Math.random() * 12);
            extracted[i] = rand;
            //to check that there are 3 different character cards extracted
            for(int j=0;j<i;j++){
                if(rand == extracted[j]){
                    duplicate = true;
                    break;
                }
            }
            if(duplicate){
                i--;
                duplicate=false;
            }
            else {
                switch (rand) {
                    case 0 -> this.characterCards.add(i, new OneStudentToIsland(this));
                    case 1 -> this.characterCards.add(i, new ControlOnProfessor(this));
                    case 2 -> this.characterCards.add(i, new ChooseIsland(this));
                    case 3 -> this.characterCards.add(i, new MoveMoreMotherNature(this));
                    case 4 -> this.characterCards.add(i, new PutNoEntryTiles(this));
                    case 5 -> this.characterCards.add(i, new NoCountTower(this));
                    case 6 -> this.characterCards.add(i, new SwitchStudents(this));
                    case 7 -> this.characterCards.add(i, new TwoAdditionalPoints(this));
                    case 8 -> this.characterCards.add(i, new ColorNoInfluence(this));
                    case 9 -> this.characterCards.add(i, new SwitchDiningWaiting(this));
                    case 10 -> this.characterCards.add(i, new StudentToDining(this));
                    case 11 -> this.characterCards.add(i, new ColorToStudentBag(this));
                }
            }
        }
    }

    public boolean isNickNameAvailable(String nickName){
        for(Player player : players){
            if(player.getNickname().equals(nickName)){
                return false;
            }
        }
        return true;
    }

    public Player getPlayerByNickName(String nickName){
        for(Player player : players){
            if(player.getNickname().equals(nickName)){
                return player;
            }
        }
        return null;
    }

}