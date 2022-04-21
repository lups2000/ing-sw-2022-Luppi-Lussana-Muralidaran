package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Exceptions.*;
import it.polimi.ingsw.Model.*;

//EFF: durante questo turno, prendi il controllo dei professori anche se nella tua sala hai lo stesso numero
//  di studenti del giocatore che li controlla in quel momento

/**
 * @author Pradeeban Muralidaran
 */

public class ControlOnProfessor extends CharacterCard{

    public ControlOnProfessor(Game game){
        cost = 2;
        used = false;
        this.game = game;
    }

    public void effect (Player player) {
        player.setControlOnProfessor(true);
    }
}
