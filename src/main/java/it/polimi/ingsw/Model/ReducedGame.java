package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class is a reduced class of the Game class.
 */
public class ReducedGame implements Serializable {

    @Serial
    private static final long serialVersionUID = -8378347837583847325L;
    private List<Player> players;
    private List<Island> islands;
    private Map<Player,AssistantCard> currentHand;
    private List<CloudTile> cloudTiles;
    private List<CharacterCard> characterCards;
    private int noEntryTilesCounter;
    private boolean expertVariant;

    public ReducedGame(Game game){
        this.players=game.getPlayers();
        this.islands=game.getIslands();
        this.currentHand=game.getCurrentHand();
        this.cloudTiles=game.getCloudTiles();
        this.characterCards=game.getCharacterCards();
        this.noEntryTilesCounter=game.getNoEntryTilesCounter();
        this.expertVariant=game.getExpertsVariant();
    }

    public List<Player> getPlayers() {return players;}
    public List<CloudTile> getCloudTiles() {return cloudTiles;}
    public List<Island> getIslands() {return islands;}
    public Map<Player, AssistantCard> getCurrentHand() {return currentHand;}
    public int getNoEntryTilesCounter() {return noEntryTilesCounter;}
    public boolean isExpertVariant() {return expertVariant;}
    public List<CharacterCard> getCharacterCards() {return characterCards;}
    public void setCharacterCards(List<CharacterCard> characterCards) {this.characterCards = characterCards;}
    public void setCloudTiles(List<CloudTile> cloudTiles) {this.cloudTiles = cloudTiles;}
    public void setCurrentHand(Map<Player, AssistantCard> currentHand) {this.currentHand = currentHand;}
    public void setIslands(List<Island> islands) {this.islands = islands;}
    public void setNoEntryTilesCounter(int noEntryTilesCounter) {this.noEntryTilesCounter = noEntryTilesCounter;}
    public void setPlayers(List<Player> players) {this.players = players;}
    public void setExpertVariant(boolean expertVariant) {this.expertVariant = expertVariant;}
}
