package it.polimi.ingsw.Model.CharacterCards;

//EFF: scegli un colore di studente, in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PawnColor;

public class ColorNoInfluence extends CharacterCard{

    public ColorNoInfluence(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(PawnColor chosen) {

        used();
    }
}

