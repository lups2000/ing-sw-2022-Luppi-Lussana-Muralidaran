package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;

/**
 * @author Pradeeban Muralidaran
 */

//EFF: scegli un'isola e calcola la maggioranza come se madre natura avesse terminato il suo movimento lì
//  in questo turno madre natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento
//  la maggioranza verrà normalmente calcolata

public class ChooseIsland extends CharacterCard{

    public ChooseIsland(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(Island island) throws NoTowersException, TooManyTowersException {
        game.influence(island);
        used();
    }

}

