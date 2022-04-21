package it.polimi.ingsw.Model.CharacterCards;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;


//EFF: durante il conteggio dell'influenza su un'isola (o su un gruppo di isole), le torri presenti non vengono calcolate


public class NoCountTower extends CharacterCard{

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(){
        game.setNoCountTower();
        used();
    }
}
