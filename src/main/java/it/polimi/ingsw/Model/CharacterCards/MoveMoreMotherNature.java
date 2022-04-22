package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.AssistantCard;

/**
 * @author Pradeeban Muralidaran
 */

/*
EFFECT: you may move mother nature up to 2 additional islands than is indicated by the assistant card you've played
 */

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
    public void effect(AssistantCard assistantcard) throws NoPawnPresentException, TooManyPawnsPresent {
        assistantcard.updateMaxStepsMotherNature(assistantcard.getMaxStepsMotherNature()+maxSteps);
        used();
    }
}
