package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;

import java.io.Serializable;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: during this turn, you take control of any number of professors even if you have the same number of students
as the player who currently controls them
 */

public class ControlOnProfessor extends CharacterCard implements Serializable {

    private static final long serialVersionUID = 3251365123612045887L;

    public ControlOnProfessor(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect (Player player) {
        player.setControlOnProfessor(true);
    }

    @Override
    public String toString() {
        return "ControlOnProfessor ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
