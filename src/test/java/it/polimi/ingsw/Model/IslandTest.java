package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Island class is tested by IslandTest
 * @author Matteo Luppi
 */

class IslandTest {

    //set an index different from 0,because I have already create an Island with index 0 in SchoolBoardTest
    Island island=new Island(1);

    @Test
    @DisplayName("setup")
    void setup(){
        assertFalse(island.isMotherNature());
    }

    @Test
    @DisplayName("changeTower")
    void changeTower() {
        ColorTower colorTower=ColorTower.BLACK;
        assertEquals(island.getNumTowers(),0);
        island.changeTower(colorTower);
        assertEquals(island.getTower(),ColorTower.BLACK);
        assertEquals(island.getNumTowers(),1);
        ColorTower colorTower1=ColorTower.GRAY;
        island.changeTower(colorTower1);
        assertEquals(island.getNumTowers(),1);
        assertEquals(island.getTower(),ColorTower.GRAY);
    }

    @Test
    @DisplayName("addStudentNull")
    void addStudentNull() {
        PawnColor pawnColor=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            island.addStudent(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("addStudent")
    void addStudent() {
        PawnColor pawnColor=PawnColor.RED;
        assertEquals(island.getStudents().get(pawnColor),0);
        island.addStudent(pawnColor);
        assertEquals(island.getStudents().get(pawnColor),1);
    }

    @Test
    @DisplayName("computeInfluence")
    void computeTotalInfluence() throws TooManyPawnsPresent, NoPawnPresentException {
        Player player1 = new Player(0, "matteo");
        Player player2 = new Player(1, "paolo");
        player1.setSchoolBoard(new SchoolBoard(2, false));
        player2.setSchoolBoard(new SchoolBoard(2, false));

        //player1 has the RED professor,while player 2 the BLUE one
        player1.getSchoolBoard().addProfessor(PawnColor.RED);
        player2.getSchoolBoard().addProfessor(PawnColor.BLUE);

        //2 RED student and 1 BLUE student on the Island
        island.addStudent(PawnColor.RED);
        island.addStudent(PawnColor.RED);
        island.addStudent(PawnColor.BLUE);

        assertEquals(island.computeTotalInfluence(player1),2);
        assertEquals(island.computeTotalInfluence(player2),1);

        //now adding the WHITE tower(associated to player 1) to this Island
        island.changeTower(ColorTower.WHITE);
        //we compute another time the influence on this island
        assertEquals(island.computeTotalInfluence(player1),3);
        assertEquals(island.computeTotalInfluence(player2),1);

        //now the player 2 gets the RED professor
        player1.getSchoolBoard().removeProfessor(PawnColor.RED);
        player2.getSchoolBoard().addProfessor(PawnColor.RED);

        assertEquals(island.computeTotalInfluence(player1),1); //1 because there is the white tower on this Island
        assertEquals(island.computeTotalInfluence(player2),3);
    }

    @Test
    @DisplayName("computeInfluenceNull")
    void computeInfluenceNull() {
        Player player=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            island.computeTotalInfluence(player);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());

    }

    @Test
    @DisplayName("mergeNull")
    void mergeNull() {
        Island island2=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            island.merge(island2);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());

    }

    @Test
    @DisplayName("merge")
    void merge(){
        Island island1=new Island(2);
        Island island2=new Island(3);
        assertEquals(Island.getNumIslands(),12);
        //2 RED and 1 BLUE student on Island 2
        island1.addStudent(PawnColor.RED);
        island1.addStudent(PawnColor.RED);
        island1.addStudent(PawnColor.BLUE);
        island1.changeTower(ColorTower.WHITE);
        //1 BLUE student on Island 3
        island2.addStudent(PawnColor.BLUE);
        island2.changeTower(ColorTower.WHITE);
        island2.setMotherNature(true);

        island1.merge(island2);

        assertEquals(Island.getNumIslands(),11);
        assertEquals(island1.getNumTowers(),2);
        assertTrue(island1.isMotherNature());
        assertEquals(island1.getStudents().get(PawnColor.RED),2);
        assertEquals(island1.getStudents().get(PawnColor.BLUE),2);
    }

    //now I am going to add getter and setter in order to reach 100% coverage,but it is not necessary
    @Test
    @DisplayName("getIndex")
    void getIndex(){
        assertEquals(island.getIndex(),1);
    }

    @Test
    @DisplayName("setIndexOut")
    void setIndexOut(){
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            island.setIndex(14);
        });
        assertEquals("The index cannot be lower than 0 or greater than 12!", exception.getMessage());

        Throwable exception1 = assertThrows(IndexOutOfBoundsException.class, () -> {
            island.setIndex(-1);
        });
        assertEquals("The index cannot be lower than 0 or greater than 12!", exception1.getMessage());
    }

    @Test
    @DisplayName("setIndex")
    void setIndex(){
        island.setIndex(2);
        assertEquals(island.getIndex(),2);
    }

    @Test
    @DisplayName("setNumIsland")
    void setNumIsland(){
        Island.setNumIslands(12);
        assertEquals(Island.getNumIslands(),12);
    }

    @Test
    @DisplayName("getEntryTiles")
    void getEntryTiles(){
        assertEquals(island.getNoEntryTiles(),0);
    }
    @Test
    @DisplayName("setEntryTiles")
    void setEntryTiles(){
        assertEquals(island.getNoEntryTiles(),0);
        Throwable exception1 = assertThrows(IndexOutOfBoundsException.class, () -> {
            island.setNoEntryTiles(3);
        });
        assertEquals("The parameter cannot be lower than -1 or greater than 1!", exception1.getMessage());
        island.setNoEntryTiles(1);
        assertEquals(island.getNoEntryTiles(),1);
        island.setNoEntryTiles(-1);
        assertEquals(island.getNoEntryTiles(),0);
    }

}