package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;

public class ChooseIsland extends CharacterCard{

    public ChooseIsland(){
        cost=3;
        used=false;
    }

    public void effect(Island island) {
        
    }
}

