package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.PlayerStatus;

import java.io.Serial;
import java.io.Serializable;

/*
EFFECT: you may move mother nature up to 2 additional islands than is indicated by the assistant card you've played
 */

public class MoveMoreMotherNature extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 3419483743784718717L;
    private final int maxSteps;

    public MoveMoreMotherNature(Game game){
        this.maxSteps = 2;
        cost = 1;
        used = false;
        this.game = game;
        this.type = CharacterCardType.MOVE_MORE_MOTHER_NATURE;
    }

    //This void imports the current active assistant card's maximum number of steps
    //and returns it increased by two
    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING_ACTION)){
                player.getCurrentAssistant().updateMaxStepsMotherNature(maxSteps);
            }
        }
        used();
    }

    @Override
    public String toString() {
        return "MoveMoreMotherNature ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
