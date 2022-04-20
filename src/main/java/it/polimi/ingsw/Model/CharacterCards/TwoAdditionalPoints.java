package it.polimi.ingsw.Model.CharacterCards;

//EFF: in questo turno durante il calcolo dell'influenza hai 2 punti di influenza addizionali

import it.polimi.ingsw.Model.*;

public class TwoAdditionalPoints extends CharacterCard{

    public TwoAdditionalPoints(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect() {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING)){
                player.setTwoAdditionalPoints(true);
            }
        }
        used();
    }
}

