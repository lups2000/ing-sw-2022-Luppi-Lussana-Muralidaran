package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * This class represents the player.
 */
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = -921895788350304977L;
    private int id;
    private String nickname;
    private ColorTower colorTower;
    private PlayerStatus playerStatus;
    private SchoolBoard schoolBoard;
    private DeckAssistantCard deckAssistantCard;
    private boolean twoAdditionalPoints; //for the character card TwoAdditionalPoints
    private boolean controlOnProfessor; //flag used for ControlOnProfessor character card
    private AssistantCard currentAssistant; //current Assistant card

    /**
     * Constructor
     * @param id is the id given to every player (the first to register will be given id=0 and so on).
     * @param nickname is the nickname the player chooses when he registers himself.
     */
    public Player(int id,String nickname){
        this.id = id;
        this.nickname = nickname;
        switch (id) {
            case 0 -> this.colorTower = ColorTower.WHITE;
            case 1 -> this.colorTower = ColorTower.BLACK;
            case 2 -> this.colorTower = ColorTower.GRAY;
        }
        this.playerStatus = PlayerStatus.WAITING;
        this.twoAdditionalPoints = false;
        this.controlOnProfessor = false;
    }

    public String getNickname() {
        return nickname;
    }
    public int getId() {return id;}
    public PlayerStatus getStatus() {return playerStatus;}
    public ColorTower getColorTower() {return colorTower;}
    public SchoolBoard getSchoolBoard() {return schoolBoard;}
    public DeckAssistantCard getDeckAssistantCard() {return this.deckAssistantCard;}
    public void setStatus(PlayerStatus newStatus){this.playerStatus = newStatus;}
    public void setSchoolBoard(SchoolBoard schoolBoard) {this.schoolBoard = schoolBoard;}
    public void setTwoAdditionalPoints(boolean next){this.twoAdditionalPoints = next;}
    public boolean isTwoAdditionalPoints(){return this.twoAdditionalPoints;}
    public boolean getControlOnProfessor(){return this.controlOnProfessor;}
    public void setControlOnProfessor (boolean controlSwitch){this.controlOnProfessor = controlSwitch;}
    public AssistantCard getCurrentAssistant(){return this.currentAssistant;}
    public void pickAssistantCard(AssistantCard picked)throws IllegalArgumentException,IllegalStateException{currentAssistant = deckAssistantCard.pick(picked);}

    /**
     * Method to pick a cloud Tile.
     * @param cloudTile the cloud tile chosen
     * @throws TooManyPawnsPresent
     */
    public void pickCloudTile(CloudTile cloudTile) throws TooManyPawnsPresent {
        Map<PawnColor,Integer> picked=cloudTile.pickStudents();

        for(PawnColor pawnColor : PawnColor.values()){
            while(picked.get(pawnColor)>0){
                this.getSchoolBoard().addStudToWaiting(pawnColor);
                picked.put(pawnColor,picked.get(pawnColor)-1);
            }
        }
    }

    /**
     * Method to choose the initial deck of Assistant Cards.
     * @param assistantSeed is the wizard chosen initially.
     */
    public void chooseDeck(AssistantSeed assistantSeed){
        this.deckAssistantCard=new DeckAssistantCard(assistantSeed);
    }
}
