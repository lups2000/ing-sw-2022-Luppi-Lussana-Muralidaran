package it.polimi.ingsw.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * SchoolBoardTest tests the class SchoolBoard
 * @author Matteo Luppi
 */

class SchoolBoardTest {

    @Test
    @DisplayName("setup2PlayersExpert")
    void setup2PlayersExpert() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,true);
        SchoolBoard schoolBoard=new SchoolBoard();

        assertEquals(schoolBoard.getNumMaxTowers(),8);
        assertEquals(schoolBoard.getNumTowers(),8);
        assertEquals(schoolBoard.getNumMaxStudentsWaiting(),7);

        assertFalse(schoolBoard.getProfessors().get(PawnColor.RED));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.GREEN));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.PINK));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.BLUE));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.YELLOW));

        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.RED),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.GREEN),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.PINK),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.BLUE),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.YELLOW),0);

        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.RED),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.GREEN),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.PINK),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.BLUE),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.YELLOW),0);

        //expert variant
        assertEquals(schoolBoard.getNumCoins(),1);
    }

    @Test
    @DisplayName("setup3PlayersNoExpert")
    void setup3PlayersNoExpert() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(3,false);
        SchoolBoard schoolBoard=new SchoolBoard();

        assertEquals(schoolBoard.getNumMaxTowers(),6);
        assertEquals(schoolBoard.getNumTowers(),6);
        assertEquals(schoolBoard.getNumMaxStudentsWaiting(),9);

        assertFalse(schoolBoard.getProfessors().get(PawnColor.RED));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.GREEN));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.PINK));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.BLUE));
        assertFalse(schoolBoard.getProfessors().get(PawnColor.YELLOW));

        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.RED),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.GREEN),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.PINK),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.BLUE),0);
        assertEquals(schoolBoard.getStudentsDining().get(PawnColor.YELLOW),0);

        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.RED),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.GREEN),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.PINK),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.BLUE),0);
        assertEquals(schoolBoard.getStudentsWaiting().get(PawnColor.YELLOW),0);

        //no expert variant
        assertEquals(schoolBoard.getNumCoins(),0);
    }

    @Test
    @DisplayName("moveStudToDining")
    void moveStudToDining() {

    }

    @Test
    @DisplayName("moveStudToIsland")
    void moveStudToIsland() {
    }

    @Test
    @DisplayName("addProfessorNull")
    void addProfessorNull() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.addProfessor(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("addProfessor")
    void addProfessor() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();
        PawnColor pawnColor=PawnColor.RED;
        assertFalse(schoolBoard.getProfessors().get(pawnColor));
        schoolBoard.addProfessor(pawnColor);
        assertTrue(schoolBoard.getProfessors().get(pawnColor));
        Throwable exception = assertThrows(TooManyPawnsPresent.class, () -> {
            schoolBoard.addProfessor(pawnColor);
        });
        assertEquals("Too many pawns are present!", exception.getMessage());
        assertTrue(schoolBoard.getProfessors().get(pawnColor));
    }

    @Test
    @DisplayName("removeProfessorNull")
    void removeProfessorNull() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.removeProfessor(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("removeProfessor")
    void removeProfessor() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();
        PawnColor pawnColor=PawnColor.RED;
        assertFalse(schoolBoard.getProfessors().get(pawnColor));
        schoolBoard.addProfessor(pawnColor);
        assertTrue(schoolBoard.getProfessors().get(pawnColor));
        schoolBoard.removeProfessor(pawnColor);
        assertFalse(schoolBoard.getProfessors().get(pawnColor));
        Throwable exception = assertThrows(NoPawnPresentException.class, () -> {
            schoolBoard.removeProfessor(pawnColor);
        });
        assertEquals("Pawn not present!", exception.getMessage());
    }

    @Test
    @DisplayName("updateNumberOfTowersTooMany")
    void updateNumberOfTowersTooMany() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();

        assertEquals(schoolBoard.getNumTowers(),8);
        Throwable exception = assertThrows(TooManyTowersException.class, () -> {
            schoolBoard.updateNumberOfTowers(1);
        });
        assertEquals("Too many towers are present!", exception.getMessage());
        assertEquals(schoolBoard.getNumTowers(),8);
    }

    @Test
    @DisplayName("updateNumberOfTowersNoTow")
    void updateNumberOfTowersNoTow() throws NoPawnPresentException, TooManyPawnsPresent {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();

        assertEquals(schoolBoard.getNumTowers(),8);
        Throwable exception = assertThrows(NoTowersException.class, () -> {
            schoolBoard.updateNumberOfTowers(-9);
        });
        assertEquals("No towers are present!", exception.getMessage());
        assertEquals(schoolBoard.getNumTowers(),8);
    }

    @Test
    @DisplayName("updateNumberOfTowers")
    void updateNumberOfTowers() throws NoPawnPresentException, TooManyPawnsPresent, TooManyTowersException, NoTowersException {
        Game.getInstance().initGame(2,false);
        SchoolBoard schoolBoard=new SchoolBoard();

        assertEquals(schoolBoard.getNumTowers(),8);
        schoolBoard.updateNumberOfTowers(-5);
        assertEquals(schoolBoard.getNumTowers(),3);
        schoolBoard.updateNumberOfTowers(2);
        assertEquals(schoolBoard.getNumTowers(),5);
    }

    @Test
    @DisplayName("addStudToWaiting")
    void addStudToWaiting() {
    }
}