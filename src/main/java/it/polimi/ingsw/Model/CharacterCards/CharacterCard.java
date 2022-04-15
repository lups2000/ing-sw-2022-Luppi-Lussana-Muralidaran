package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;

public abstract class CharacterCard {
    protected int cost;
    protected boolean used;

    public int getCost() {return cost;}

    //public boolean isUsed() {return used;}

    //public void setUsed(boolean used) {this.used = used;}

    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {

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
