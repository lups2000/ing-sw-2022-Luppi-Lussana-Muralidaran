package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.TooManyPawnsPresent;
import it.polimi.ingsw.Model.AssistantCard;

//EFF: puoi muovere madre natura fino a 2 isole addizionali rispetto a quanto indicato sulla carta assistente che hai giocato

public class MoveMoreMotherNature extends CharacterCard{
    private int maxSteps;

    public MoveMoreMotherNature(Game game){
        this.maxSteps = 2;
        cost = 1;
        used = false;
        this.game = game;
    }

    //This void imports the current active assistant card's maximum number of steps
    //and returns it increased by two
    public void effect(AssistantCard assistantCard) throws NoPawnPresentException, TooManyPawnsPresent {
        assistantCard.updateMaxStepsMotherNature(assistantCard.getMaxStepsMotherNature()+maxSteps);
        used();
    }
}
