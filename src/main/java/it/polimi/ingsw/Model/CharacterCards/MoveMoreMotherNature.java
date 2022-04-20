package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;

//PRADEE TODO

//EFF: puoi muovere madre natura fino a 2 isole addizionali rispetto a quanto indicato sulla carta assistente che hai giocato

public class MoveMoreMotherNature extends CharacterCard{
    private int maxSteps;

    public MoveMoreMotherNature(Game game){
        this.maxSteps = 2;
        cost = 1;
        used = false;
        this.game = game;
    }

    @Override
    public void effect() throws NoPawnPresentException {

        used();
    }
}
