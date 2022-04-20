package it.polimi.ingsw.Model.CharacterCards;
import it.polimi.ingsw.Model.*;

//PRADEE TODO

//EFF: durante il conteggio dell'influenza su un'isola (o su un gruppo di isole), le torri presenti non vengono calcolate


public class NoCountTower extends CharacterCard{

    public NoCountTower(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    //This void calculates the influence of an island just by the students' number
    public void effect(Island island, Player player) throws NoPawnPresentException, TooManyPawnsPresent {
        int influence = 0;
        island.computeStudentsInfluence(player, influence);
        used();
    }
}
