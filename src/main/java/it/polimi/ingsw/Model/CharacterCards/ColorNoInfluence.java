package it.polimi.ingsw.Model.CharacterCards;

//EFFECT: choose a color of student: during the influence calculation this turn, that color adds no influence

import it.polimi.ingsw.Model.*;

public class ColorNoInfluence extends CharacterCard{

    public ColorNoInfluence(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(PawnColor chosen) {
        game.setNoColorInfluence(chosen);
        used();
    }
}