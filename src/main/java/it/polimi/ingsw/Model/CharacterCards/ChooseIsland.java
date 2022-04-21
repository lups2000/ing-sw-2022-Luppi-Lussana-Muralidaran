package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;

/**
 * @author Pradeeban Muralidaran
 */

//EFF: choose an island and resolve the island as if mother nature had ended her movement there.
//  Mother nature will still move and the island where she ends her movement will also be resolved

public class ChooseIsland extends CharacterCard{

    public ChooseIsland(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(Island island) throws NoTowersException, TooManyTowersException {
        game.influence(island);
        used();
    }

}

