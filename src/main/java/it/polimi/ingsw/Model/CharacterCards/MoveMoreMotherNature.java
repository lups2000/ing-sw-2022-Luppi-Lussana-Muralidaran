package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;

public class MoveMoreMotherNature extends CharacterCard{
    private int maxSteps;

    public MoveMoreMotherNature(){
        this.maxSteps=2;
        cost=1;
        used=false;
    }

    @Override
    public void effect() throws NoPawnPresentException {

    }
}
