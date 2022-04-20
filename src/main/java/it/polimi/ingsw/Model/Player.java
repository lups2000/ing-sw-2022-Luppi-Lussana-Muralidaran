package it.polimi.ingsw.Model;

import java.util.List;

/**
 * @author Matteo Luppi,Paolo Lussana
 */
public class Player {
    private int id;
    private String nickname;
    private ColorTower colorTower;
    private PlayerStatus playerStatus;
    private SchoolBoard schoolBoard;
    private DeckAssistantCard deckAssistantCard;
    private boolean twoAdditionalPoints;  //for the character card TwoAdditionalPoints

    /**
     * constructor of the Player class
     * @param id is the id given to every player (the first to register will be given id=0 and so on)
     * @param nickname is the nickname the player chooses when he registers himself
     * @param seed is the wizard selected by this player for the choice of the assistant cards' deck
     */
    public Player(int id,String nickname,AssistantSeed seed,SchoolBoard schoolBoard){
        this.id = id;
        this.nickname = nickname;
        switch (id) {
            case 0 -> this.colorTower = ColorTower.WHITE;
            case 1 -> this.colorTower = ColorTower.BLACK;
            case 2 -> this.colorTower = ColorTower.GRAY;
        }
        this.playerStatus = PlayerStatus.WAITING;
        this.schoolBoard = schoolBoard;
        this.deckAssistantCard = new DeckAssistantCard(seed);
        this.twoAdditionalPoints = false;
    }

    public String getNickname() {
        return nickname;
    }
    public int getId() {return id;}
    public PlayerStatus getStatus() {return playerStatus;}
    public ColorTower getColorTower() {return colorTower;}
    public SchoolBoard getSchoolBoard() {return schoolBoard;}
    public void createDeck(AssistantSeed seed){this.deckAssistantCard=new DeckAssistantCard(seed);}
    public DeckAssistantCard getDeckAssistantCard() {return deckAssistantCard;}
    public void setStatus(PlayerStatus newStatus){this.playerStatus = newStatus;}
    public void setTwoAdditionalPoints(boolean next){this.twoAdditionalPoints = next;}
    public boolean isTwoAdditionalPoints(){return this.twoAdditionalPoints;}
}
