package it.polimi.ingsw.Model.CharacterCards;
import it.polimi.ingsw.Model.*;

import java.io.Serializable;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: when resolving a conquering on an island, towers do not count towards influence
*/

public class NoCountTower extends CharacterCard implements Serializable {

    private static final long serialVersionUID = 1872733231237478717L;

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(){
        game.setNoCountTower();
        used();
    }

    @Override
    public String toString() {
        return "NoCountTower ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
