package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;

import java.io.Serial;
import java.io.Serializable;

/**
 * This is an abstract class that represents a Character Card.
 */
public abstract class CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 2564765267367312717L;
    protected int cost;
    protected boolean used;
    protected Game game;
    protected CharacterCardType type;

    public int getCost() {return cost;}

    /**
     * Method that represents the effect of the card.
     * @throws NoPawnPresentException
     * @throws TooManyPawnsPresent
     */
    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {

    }

    public CharacterCardType getType() {
        return type;
    }

    public Game getGame() {return game;}

    public void setGame(Game game) {this.game = game;}

    /**
     * Method invoked every time a character card is played.
     * The first time this will happen the cost of the character card will increment and the boolean "used" will be set = true.
     */
    public void used(){
        if(!used){
            this.used = true;
            this.cost++;
        }
    }
}
