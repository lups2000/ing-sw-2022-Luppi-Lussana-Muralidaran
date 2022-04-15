package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;

//PRADEE TODO

//EFF: scegli un'isola e calcola la maggioranza come se madre natura avesse terminato il suo movimento lì
//  in questo turno madre natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata

public class ChooseIsland extends CharacterCard{

    public ChooseIsland(){
        cost = 3;
        used = false;
    }

    public void effect(Island island) {

        used();
    }
}

