package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.io.Serializable;

public abstract class CharacterCard implements Serializable {

    private static final long serialVersionUID = 2564765267367312717L;
    protected int cost;
    protected boolean used;
    protected Game game;
    protected CharacterCardType type;

    public int getCost() {return cost;}

    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {

    }

    public CharacterCardType getType() {
        return type;
    }

    /**
     * method invoked every time a character card is invoked
     * the first time this will happen the cost of the character card will increment and the boolean "used" will be set = true
     */
    public void used(){
        if(!used){
            this.used = true;
            this.cost++;
        }
    }
}
