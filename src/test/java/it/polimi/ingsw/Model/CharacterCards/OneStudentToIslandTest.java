package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.PlayerStatus;
import it.polimi.ingsw.Model.SchoolBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OneStudentToIslandTest {

    Game game =new Game();

    @Test
    void effect() throws NoPawnPresentException {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);

        game.getPlayers().get(0).setStatus(PlayerStatus.PLAYING_ACTION);
        OneStudentToIsland oneStudentToIsland=new OneStudentToIsland(game);
        PawnColor chosen=null;
        for(PawnColor pawnColor : PawnColor.values()){
            if(oneStudentToIsland.getStudents().get(pawnColor)>0){
                chosen=pawnColor;
                break;
            }
        }
        oneStudentToIsland.effect(game.getIslands().get(0),chosen);
        assertEquals(game.getIslands().get(0).getStudents().get(chosen),1);
        assertEquals(oneStudentToIsland.getCost(),2);
    }

}