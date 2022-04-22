package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.NoNoEntryTilesException;

/**
 * @author Pradeeban Muralidaran
 */

/*
In setup, put the 4 no entry tiles on this card.
EFF: place a no entry tile on an island of your choice.
The first time mother nature ends her movement there, put the no entry tile back onto this card
DO NOT calculate influence on that island, or place any towers
*/

public class PutNoEntryTiles extends CharacterCard {

    public PutNoEntryTiles(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect(Island island) throws NoNoEntryTilesException{
        if (game.getNoEntryTilesCounter() > 0){
            island.setNoEntryTiles(1);
            game.setNoEntryTilesCounter(game.getNoEntryTilesCounter()-1);
            used();
        }
        else {throw new NoNoEntryTilesException();}
    }
}
