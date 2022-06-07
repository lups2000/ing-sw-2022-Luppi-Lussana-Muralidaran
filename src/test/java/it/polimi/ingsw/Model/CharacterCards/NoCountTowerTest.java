package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCountTowerTest {

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
        game.getIslands().get(6).addStudent(PawnColor.RED);
        //teo has influence 1 on the island 6
        game.moveMotherNature(game.getIslands().get(6));
        //now teo has influence 1+1(tower)
        assertEquals(game.getIslands().get(6).getTower(), ColorTower.WHITE);

        game.getPlayers().get(1).setStatus(PlayerStatus.PLAYING_ACTION);
        game.getPlayers().get(1).getSchoolBoard().addStudToDining(PawnColor.BLUE);
        game.allocateProfessors(); //Teo Red,Paolo blue
        game.getIslands().get(6).addStudent(PawnColor.BLUE);
        game.getIslands().get(6).addStudent(PawnColor.BLUE);
        //Teo influence 2,Paolo influence 2
        assertEquals(game.getIslands().get(6).getTower(), ColorTower.WHITE); //no change

        NoCountTower noCountTower=new NoCountTower(game);
        noCountTower.effect();
        assertTrue(game.isNoCountTower());
        assertEquals(noCountTower.getCost(),4);

        game.moveMotherNature(game.getIslands().get(6));
        //Teo influence 1,Paolo influence 2 thanks to the card
        assertEquals(game.getIslands().get(6).getTower(), ColorTower.BLACK);
    }

}