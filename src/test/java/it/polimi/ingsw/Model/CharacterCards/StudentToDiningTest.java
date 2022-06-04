package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.PlayerStatus;
import it.polimi.ingsw.Model.SchoolBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentToDiningTest {

    Game game=new Game();

    @Test
    void effect() throws NoPawnPresentException, TooManyPawnsPresent {
        game.addPlayer("Teo");
        game.addPlayer("Paolo");
        game.getPlayers().get(0).setSchoolBoard(new SchoolBoard(2,true));
        game.getPlayers().get(1).setSchoolBoard(new SchoolBoard(2,true));
        game.initGame(2,true);

        game.getPlayers().get(0).getSchoolBoard().addStudToDining(PawnColor.RED);
        game.allocateProfessors(); //Teo has the red prof

        StudentToDining studentToDining=new StudentToDining(game);
        //System.out.println(studentToDining.getStudents());
        game.getPlayers().get(0).setStatus(PlayerStatus.PLAYING_ACTION);
        PawnColor chosen=null;
        for(PawnColor pawnColor : PawnColor.values()){
            if(studentToDining.getStudents().get(pawnColor)>0){
                chosen=pawnColor;
                break;
            }
        }
        studentToDining.effect(chosen);
        assertEquals(studentToDining.getCost(),3);

        //System.out.println(studentToDining.getStudents());
        //System.out.println(game.getPlayers().get(0).getSchoolBoard().getStudentsDining());


    }

}