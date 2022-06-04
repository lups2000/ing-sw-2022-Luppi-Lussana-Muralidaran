package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChooseIslandTest {

    Game game=new Game();

    @Test
    void effect() throws TooManyPawnsPresent, NoPawnPresentException, TooManyTowersException, NoTowersException {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo has the red prof
        game.getIslands().get(6).addStudent(PawnColor.RED);
        //teo has influence 1 on the island 6
        game.getPlayers().get(0).setStatus(PlayerStatus.PLAYING_ACTION);

        ChooseIsland chooseIsland=new ChooseIsland(game);
        chooseIsland.effect(game.getIslands().get(6));
        assertEquals(chooseIsland.getCost(),4);

        assertEquals(game.getIslands().get(6).getTower(), ColorTower.WHITE);
        assertEquals(game.getMotherNature(),0);
    }

}