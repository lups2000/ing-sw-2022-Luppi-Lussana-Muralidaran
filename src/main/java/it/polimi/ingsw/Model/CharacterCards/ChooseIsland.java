package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;

//EFF: scegli un'isola e calcola la maggioranza come se madre natura avesse terminato il suo movimento lì
//  in questo turno madre natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento
//  la maggioranza verrà normalmente calcolata

public class ChooseIsland extends CharacterCard{

    public ChooseIsland(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    //Void function which calculates the influence on any island selected by the current player
    public void effect(Island island, Player player) {
        island.computeTotalInfluence(player);
        used();
    }

}

