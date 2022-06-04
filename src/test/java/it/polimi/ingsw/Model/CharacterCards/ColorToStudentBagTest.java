package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.PlayerStatus;
import it.polimi.ingsw.Model.SchoolBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorToStudentBagTest {

    Game game=new Game();

    @Test
    void effect() throws TooManyPawnsPresent, NoPawnPresentException {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);

        //Teo has 1 Red,Paolo has 4 RED
        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.RED);

        game.getPlayers().get(0).setStatus(PlayerStatus.PLAYING_ACTION);
        ColorToStudentBag colorToStudentBag=new ColorToStudentBag(game);
        colorToStudentBag.effect(PawnColor.RED);
        assertEquals(colorToStudentBag.getCost(),4);

        assertEquals(game.getPlayers().get(0).getSchoolBoard().getStudentsDining().get(PawnColor.RED),0);
        assertEquals(game.getPlayers().get(1).getSchoolBoard().getStudentsDining().get(PawnColor.RED),1);


    }

}