package it.polimi.ingsw.Model.CharacterCards;

//EFFECT: during the influence calculation this turn, you count as having 2 more influence

import it.polimi.ingsw.Model.*;

public class TwoAdditionalPoints extends CharacterCard{

    public TwoAdditionalPoints(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect() {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING)){
                player.setTwoAdditionalPoints(true);
            }
        }
        used();
    }
}

