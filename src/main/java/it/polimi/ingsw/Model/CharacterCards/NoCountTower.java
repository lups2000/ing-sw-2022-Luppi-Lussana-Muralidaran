package it.polimi.ingsw.Model.CharacterCards;

//PRADEE TODO

//EFF: durante il conteggio dell'influenza su un'isola (o su un gruppo di isole), le torri presenti non vengono calcolate

import it.polimi.ingsw.Model.Game;

public class NoCountTower extends CharacterCard{

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(){

        used();
    }
}
