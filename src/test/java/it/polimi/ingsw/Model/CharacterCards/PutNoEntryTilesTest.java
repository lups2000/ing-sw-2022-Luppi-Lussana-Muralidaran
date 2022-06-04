package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutNoEntryTilesTest {

    Game game=new Game();

    @Test
    void effect() throws TooManyPawnsPresent, NoPawnPresentException, TooManyTowersException, NoTowersException, NoNoEntryTilesException {
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
        //teo has influence 1+1 on the island 6
        assertEquals(game.getIslands().get(6).getTower(), ColorTower.WHITE);

        game.getPlayers().get(1).setStatus(PlayerStatus.PLAYING_ACTION);
        PutNoEntryTiles putNoEntryTiles=new PutNoEntryTiles(game);
        putNoEntryTiles.effect(game.getIslands().get(6));
        assertEquals(putNoEntryTiles.getCost(),3);

        assertEquals(game.getIslands().get(6).getNoEntryTiles(),1);
        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.BLUE);
        game.allocateProfessors(); //Paolo has the blue prof
        game.getIslands().get(6).addStudent(PawnColor.BLUE);
        game.influence(game.getIslands().get(6));
        assertEquals(game.getIslands().get(6).getTower(), ColorTower.WHITE);
        assertEquals(game.getIslands().get(6).getNoEntryTiles(),0);
    }

}