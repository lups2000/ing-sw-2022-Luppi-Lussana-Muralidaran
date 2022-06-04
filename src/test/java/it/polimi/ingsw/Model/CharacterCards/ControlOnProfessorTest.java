package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlOnProfessorTest {

    Game game=new Game();

    @Test
    void effect() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo has the red prof
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED),true);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED),false);

        game.getPlayers().get(1).setStatus(PlayerStatus.PLAYING_ACTION);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //no change
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED),true);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED),false);

        ControlOnProfessor controlOnProfessor=new ControlOnProfessor(game);
        controlOnProfessor.effect();
        assertEquals(controlOnProfessor.getCost(),3);

        game.allocateProfessors(); //Paolo takes the Red
        assertEquals(game.getPlayers().get(0).getSchoolBoard().getProfessors().get(PawnColor.RED),false);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getProfessors().get(PawnColor.RED),true);

    }

}