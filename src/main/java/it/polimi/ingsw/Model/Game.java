package it.polimi.ingsw.Model;

import java.util.List;

public class Game {
    private int id;
    private int numPlayers;
    private List<Player> players; //2-4
    private boolean expertVariant;
    private GameState status;
    private List<DeckAssistantCard> decks; //4
    private List<Island> islands; //initially 12
    private StudentBag studentBag;
    private MotherNature motherNature;
    private List<CloudTile> cloudTiles; //2-4

    //constructor
    public Game(){
        //TODO
    }

    public int getId() {
        return id;
    }

    public GameState getStatus() {
        return status;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isExpertVariant() {
        return expertVariant;
    }
    public void changeStatus(GameState status){
        //TODO
    }
    public void fillBoard(){
        //TODO
    }
}
