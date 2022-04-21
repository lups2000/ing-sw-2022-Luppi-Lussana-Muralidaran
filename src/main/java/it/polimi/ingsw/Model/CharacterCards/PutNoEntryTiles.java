package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Exceptions.NoNoEntryTilesException;

/**
 * @author Pradeeban Muralidaran
 */

//PRE: all'inizio della partita, mettete le 4 tessere divieto su questa carta
//EFF: piazza una tessera divieto su un'isola a tua scelta.
//  la prima volta che madre natura termina il suo movimento lì, rimettete la tessera divieto sulla carta
//  SENZA calcolare l'influenza su quell'isola né piazzare torri


public class PutNoEntryTiles extends CharacterCard {

    public PutNoEntryTiles(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect(Island island) throws NoNoEntryTilesException {
        if (game.getNoEntryTilesCounter() > 0){
            island.setNoEntryTiles(1);
            game.setNoEntryTilesCounter(game.getNoEntryTilesCounter()-1);
            used();
        }
        else {throw new NoNoEntryTilesException();}
    }
}
