package it.polimi.ingsw.Model.CharacterCards;

//PRADEE TODO

//EFF: durante il conteggio dell'influenza su un'isola (o su un gruppo di isole), le torri presenti non vengono calcolate

public class NoCountTower extends CharacterCard{

    public NoCountTower(){
        cost = 3;
        used = false;
    }

    public void effect(){

        used();
    }
}
