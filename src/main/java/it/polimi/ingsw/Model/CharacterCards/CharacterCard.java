package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;

public abstract class CharacterCard {
    protected int cost;
    protected boolean used;

    public int getCost() {return cost;}

    public boolean isUsed() {return used;}

    public void setUsed(boolean used) {this.used = used;}

    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {

    }
}
