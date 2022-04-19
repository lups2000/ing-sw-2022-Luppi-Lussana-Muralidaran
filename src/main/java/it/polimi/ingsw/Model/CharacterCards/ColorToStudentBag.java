package it.polimi.ingsw.Model.CharacterCards;

//EFF: scegli un colore di studente, ogni giocatore (incluso te) deve rimettere nel sacchetto 3 studenti di quel colore presenti nella tua sala
//chi avesse meno di 3 studenti di quel colore rimetterà tutti quelli che ha

import it.polimi.ingsw.Model.*;

public class ColorToStudentBag extends CharacterCard{

    public ColorToStudentBag(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(PawnColor chosen) throws TooManyPawnsPresent,NoPawnPresentException{
        for(Player player : game.getPlayers()){
            int removed = player.getSchoolBoard().removeStudents(chosen);
            game.getStudentBag().addStudents(chosen,removed);
        }
        game.allocateProfessors();
        used();
    }
}

