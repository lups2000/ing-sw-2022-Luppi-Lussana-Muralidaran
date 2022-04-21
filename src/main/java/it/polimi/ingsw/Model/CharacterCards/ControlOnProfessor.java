package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;

/**
 * @author Pradeeban Muralidaran
 */

//EFFECT: during this turn, you take control of any number of professors even if you have the same number of students
//  as the player who currently controls them

public class ControlOnProfessor extends CharacterCard{

    public ControlOnProfessor(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect (Player player) {
        player.setControlOnProfessor(true);
    }
}
