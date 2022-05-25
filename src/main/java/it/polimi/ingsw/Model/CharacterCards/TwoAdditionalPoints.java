package it.polimi.ingsw.Model.CharacterCards;

//EFFECT: during the influence calculation this turn, you count as having 2 more influence

import it.polimi.ingsw.Model.*;

import java.io.Serial;
import java.io.Serializable;

public class TwoAdditionalPoints extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 6875895261637393857L;

    public TwoAdditionalPoints(Game game){
        cost = 2;
        used = false;
        this.game = game;
        this.type = CharacterCardType.TWO_ADDITIONAL_POINTS;
    }

    public void effect() {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING_ACTION)){
                player.setTwoAdditionalPoints(true);
            }
        }
        used();
    }

    @Override
    public String toString() {
        return "TwoAdditionalPoints ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}

