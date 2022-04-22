package it.polimi.ingsw.Model.CharacterCards;
import it.polimi.ingsw.Model.*;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: when resolving a conquering on an island, towers do not count towards influence
*/

public class NoCountTower extends CharacterCard{

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(){
        game.setNoCountTower();
        used();
    }
}
