package it.polimi.ingsw.Model.CharacterCards;

//EFFECT: choose a color of student: during the influence calculation this turn, that color adds no influence

import it.polimi.ingsw.Model.*;

import java.io.Serializable;

public class ColorNoInfluence extends CharacterCard implements Serializable {

    private static final long serialVersionUID = 2564765265484758477L;

    public ColorNoInfluence(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(PawnColor chosen) {
        game.setNoColorInfluence(chosen);
        used();
    }

    @Override
    public String toString() {
        return "ColorNoInfluence ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}