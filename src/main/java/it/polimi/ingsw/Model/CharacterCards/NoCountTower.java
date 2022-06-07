package it.polimi.ingsw.Model.CharacterCards;
import it.polimi.ingsw.Model.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: when resolving a conquering on an island, towers do not count towards influence
*/

public class NoCountTower extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 1828328347674672817L;

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
        this.type = CharacterCardType.NO_COUNT_TOWER;
    }

    public void effect(){
        game.setNoCountTower(true);
        used();
    }


    @Override
    public String toString() {
        return "NoCountTower ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
