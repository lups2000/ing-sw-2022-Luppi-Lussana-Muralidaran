package it.polimi.ingsw.Model.CharacterCards;

//PRADEE TODO

//PRE: all'inizio della partita, mettete le 4 tessere divieto su questa carta
//EFF: piazza una tessera divieto su un'isola a tua scelta.
//  la prima volta che madre natura temina il suo movimento lì, rimettete la tessera divieto sulla carta
//  SENZA calcolare l'influenza su quell'isola né piazzare torri

import it.polimi.ingsw.Model.Game;

public class PutNoEntryTiles extends CharacterCard {

    public PutNoEntryTiles(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect(){

        used();
    }
}