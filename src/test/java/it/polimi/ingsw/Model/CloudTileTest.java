package it.polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CloudTile class is tested by CloudTileTest
 * @author Matteo Luppi
 */

class CloudTileTest {

    @Test
    @DisplayName("test setup 2 players")
    void setup2Players() throws NoPawnPresentException, TooManyPawnsPresent {
        //in generale non ha piu senso che il metodo initGame ritorni un 'Game'.é lecito?
        Game.getInstance().initGame(2,false);
        CloudTile cloudTile=new CloudTile(1); //1 as Index
        assertEquals(cloudTile.getMaxNumStudents(),3);
        assertEquals(cloudTile.getStudents().get(PawnColor.RED),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);
    }

    @Test
    @DisplayName("test setup 3 players")
    void setup3Players() throws NoPawnPresentException, TooManyPawnsPresent {
        //in generale non ha piu senso che il metodo initGame ritorni un 'Game'.é lecito?
        Game.getInstance().initGame(3,false);
        CloudTile cloudTile=new CloudTile(1); //1 as Index
        assertEquals(cloudTile.getMaxNumStudents(),4);
        assertEquals(cloudTile.getStudents().get(PawnColor.RED),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);
    }

    @Test
    @DisplayName("test setup 4 players")
    void setup4Players() throws NoPawnPresentException, TooManyPawnsPresent {
        //in generale non ha piu senso che il metodo initGame ritorni un 'Game'.é lecito?
        Game.getInstance().initGame(4,false);
        CloudTile cloudTile=new CloudTile(1); //1 as Index
        assertEquals(cloudTile.getMaxNumStudents(),3);
        assertEquals(cloudTile.getStudents().get(PawnColor.RED),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);
    }


    @Test
    @DisplayName("pickStudent")
    void pickStudent() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        CloudTile cloudTile=new CloudTile(1);
        PawnColor pawnColor1=PawnColor.RED;
        PawnColor pawnColor2=PawnColor.BLUE;
        cloudTile.addStudent(pawnColor1);
        cloudTile.addStudent(pawnColor2);
        cloudTile.addStudent(pawnColor1);
        assertEquals(cloudTile.getNumStudents(),3);

        Map<PawnColor,Integer> newMap=cloudTile.pickStudent();

        assertEquals(cloudTile.getNumStudents(),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.RED),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);
        assertEquals(newMap.get(PawnColor.RED),2);
        assertEquals(newMap.get(PawnColor.BLUE),1);
        assertEquals(newMap.get(PawnColor.GREEN),0);
        assertEquals(newMap.get(PawnColor.YELLOW),0);
        assertEquals(newMap.get(PawnColor.PINK),0);

    }

    @Test
    @DisplayName("addStudentNull")
    void addStudentNull() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        CloudTile cloudTile=new CloudTile(1);
        PawnColor pawnColor=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            cloudTile.addStudent(pawnColor);
        });
        assertEquals("The parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("addStudent")
    void addStudent() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        CloudTile cloudTile=new CloudTile(1);
        PawnColor pawnColor1=PawnColor.RED;
        PawnColor pawnColor2=PawnColor.BLUE;

        //before the call
        assertEquals(cloudTile.getStudents().get(PawnColor.RED),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);

        cloudTile.addStudent(pawnColor1);
        cloudTile.addStudent(pawnColor2);
        cloudTile.addStudent(pawnColor1);

        //after the call

        assertEquals(cloudTile.getStudents().get(PawnColor.RED),2);
        assertEquals(cloudTile.getStudents().get(PawnColor.GREEN),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(cloudTile.getStudents().get(PawnColor.BLUE),1);
        assertEquals(cloudTile.getStudents().get(PawnColor.PINK),0);

        //another call,but now the tile is full
        Throwable exception = assertThrows(TooManyPawnsPresent.class, () -> {
            cloudTile.addStudent(pawnColor1);
        });
        assertEquals("Too many pawns are present!", exception.getMessage());
    }

    //in general we don't have to test getter and setter
    //I add this test to reach 100% line coverage
    @Test
    void getIdTest() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        CloudTile cloudTile=new CloudTile(1);
        assertEquals(cloudTile.getId(),1);
    }

}