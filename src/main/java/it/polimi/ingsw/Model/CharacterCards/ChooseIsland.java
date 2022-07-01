package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;

import java.io.Serial;
import java.io.Serializable;


/*
EFFECT: choose an island and resolve the island as if mother nature had ended her movement there.
Mother nature will still move and the island where she ends her movement will also be resolved
 */
public class ChooseIsland extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 3453275472345232517L;

    public ChooseIsland(Game game){
        cost = 3;
        used = false;
        this.game = game;
        this.type = CharacterCardType.CHOOSE_ISLAND;
    }

    public void effect(Island island) throws NoTowersException, TooManyTowersException {
        game.influence(island);
        used();
    }

    @Override
    public String toString() {
        return "ChooseIsland ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}

