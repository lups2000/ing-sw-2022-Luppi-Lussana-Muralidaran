package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: during this turn, you take control of any number of professors even if you have the same number of students
as the player who currently controls them
 */

public class ControlOnProfessor extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 3251365123612045887L;

    public ControlOnProfessor(Game game){
        cost = 2;
        used = false;
        this.game = game;
        this.type = CharacterCardType.CONTROL_ON_PROFESSOR;
    }

    public void effect () {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING_ACTION)){
                player.setControlOnProfessor(true);
            }
        }
        used();
    }

    @Override
    public String toString() {
        return "ControlOnProfessor ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
