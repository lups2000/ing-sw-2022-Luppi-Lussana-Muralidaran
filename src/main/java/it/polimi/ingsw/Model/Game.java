package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class Game is a Singleton class
 */
public class Game {
    private static Game single_instance = null;
    private int maxNumPlayers;
    private List<Player> players; //2-4
    private boolean expertsVariant;
    //private List<CharacterCard> characterCards;
    private GameState status;
    private List<DeckAssistantCard> decks; //4
    private List<Island> islands; //initially 12
    private StudentBag studentBag;
    private MotherNature motherNature;
    private List<CloudTile> cloudTiles; //2-4

    //devo controllare bene se è così il corretto funzionamento del Singleton
    //leggevo che con il pattern Singleton non si possono fare costruttori con parametri
    //quindi qui in Game() ho messo solo le operazioni che sono uguali a prescindere dal num.giocatori e dalla variante per esperti
    //mentre ho lasciato in initGame() le cose che invece vi dipendono, metodo che andrà chiamato
    private Game(){
        this.players = new ArrayList<Player>();
        this.status = GameState.CREATING;
        this.decks = new ArrayList<DeckAssistantCard>();
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
            cloudTiles.add(i,newCloud);
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
        players.add(players.size(),newPlayer);
        DeckAssistantCard newDeck = new DeckAssistantCard(chosenSeed);
        decks.add(players.size()-1,newDeck);
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
        Map<PawnColor, Integer> available = new HashMap<>();
        available.put(PawnColor.RED, 2);
        available.put(PawnColor.BLUE, 2);
        available.put(PawnColor.YELLOW, 2);
        available.put(PawnColor.PINK, 2);
        available.put(PawnColor.GREEN, 2);

        for (int i = 0; i < 12; i++) {
            PawnColor drawn = null;
            boolean check = true;   //boolean to check if there is still a student of the drawn color
            if(i == 0 || i == 6){
                //I pass the pawn color RED but it's irrelevant since in the Island constructor if index=0 or =6 no students will be placed on the island at the beginning
                Island newIsland = new Island(i,PawnColor.RED);
                islands.add(i,newIsland);
                check = false;
            }
            while (check) {
                int rand = (int) (Math.random() * 5);

                for (PawnColor d : PawnColor.values()) {
                    if (d.ordinal() == rand) {
                        drawn = d;
                        break;
                    }
                }
                if (available.get(drawn) >= 1) {
                    available.put(drawn, available.get(drawn) - 1);
                    Island newIsland = new Island(i,drawn);
                    islands.add(i,newIsland);
                    check = false;
                }
            }
        }
    }
}