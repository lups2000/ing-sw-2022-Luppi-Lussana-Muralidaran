package it.polimi.ingsw.Model;

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
    //private List<CharacterCard> characterCards;
    private GameState status;
    private List<AssistantSeed> seedsAvailable; //4 seeds that can be chosen by the players
    private List<Island> islands; //initially 12
    private StudentBag studentBag;
    private MotherNature motherNature;
    private List<CloudTile> cloudTiles; //2-4


    private Game(){
        this.players = new ArrayList<Player>();
        this.status = GameState.CREATING;
        this.seedsAvailable=new ArrayList<>(Arrays.asList(AssistantSeed.KING,AssistantSeed.SAMURAI,AssistantSeed.WITCH,AssistantSeed.WIZARD));
        this.islands = new ArrayList<Island>();
        fillIslands();
        this.studentBag = new StudentBag();
        this.motherNature = new MotherNature();
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
            //genera 3 carte personaggio
        }

        this.cloudTiles = new ArrayList<CloudTile>();
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
    }


    public GameState getStatus() {
        return status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxNumPlayers() {
        return maxNumPlayers;
    }

    public boolean getExpertsVariant(){
        return expertsVariant;
    }

    public void changeStatus(GameState status){
        this.status = status;
    }

    /**
     * method invoked one time for each player at the start of the game that fills his school board
     * @param player is the player who just entered the match, his school board will be filled
     */
    public void fillBoard(Player player) throws NoPawnPresentException,TooManyPawnsPresent{
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
    public void fillIslands() {
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
}