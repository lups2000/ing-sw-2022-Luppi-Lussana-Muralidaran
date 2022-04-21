package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

//EFFECT: choose a type of student: every player (including yourself) must return 3 students of that type from their dining room to the bag.
//  If any player has fewer than 3 students of that type, return as many students as they have

public class ColorToStudentBag extends CharacterCard{

    public ColorToStudentBag(Game game){
        cost = 3;
        used = false;
        this.game = game;
    }

    public void effect(PawnColor chosen) throws TooManyPawnsPresent, NoPawnPresentException {
        for(Player player : game.getPlayers()){
            int removed = player.getSchoolBoard().removeStudents(chosen);
            game.getStudentBag().addStudents(chosen,removed);
        }
        game.allocateProfessors();
        used();
    }
}

