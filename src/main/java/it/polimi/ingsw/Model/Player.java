package it.polimi.ingsw.Model;

public class Player {
    private String nickName;
    private int id;
    private ColorTower colorTower;
    private int numCoins;
    private PlayerStatus playerStatus;
    private SchoolBoard schoolBoard;
    private DeckAssistantCard deckAssistantCard;

    //constructor
    public Player(){
        //TODO
    }
    public String getNickName() {
        return nickName;
    }
    public int getId() {
        return id;
    }
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
    public int getNumCoins() {
        return numCoins;
    }
    public ColorTower getColorTower() {
        return colorTower;
    }
    public boolean isFirst(){
        //TODO
        return true;
    }
    public void addCoin(){
        //TODO
    }
    public void pickAssistant(){
        //TODO
    }
    public void chooseDeck(){
        //TODO
    }


}
