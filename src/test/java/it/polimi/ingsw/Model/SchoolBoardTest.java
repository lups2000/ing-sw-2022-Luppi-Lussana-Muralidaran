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
    void setup2PlayersExpert(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);

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
    void setup3PlayersNoExpert(){
        SchoolBoard schoolBoard=new SchoolBoard(3,false);

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
    @DisplayName("moveStudToDiningNull")
    void moveStudToDiningNull(){
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.moveStudToDining(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("moveStudToDiningNoStudWaiting")
    void moveStudToDiningNoStudWaiting(){
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=PawnColor.RED;

        assertEquals(schoolBoard.getStudentsWaiting().get(pawnColor),0);
        Throwable exception = assertThrows(NoPawnPresentException.class, () -> {
            schoolBoard.moveStudToDining(pawnColor);
        });
        assertEquals("Pawn not present!", exception.getMessage());
    }

    /* Note: This Method covers also the general case */
    @Test
    @DisplayName("moveStudToDiningTooManyStudDining")
    void moveStudToDiningTooManyStudDining() throws NoPawnPresentException, TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        PawnColor pawnColor=PawnColor.RED;

        //adding 7 RED students to the Waiting Room
        for(int i=0;i<7;i++){
            schoolBoard.addStudToWaiting(pawnColor);
        }
        assertEquals(schoolBoard.getStudentsWaiting().get(pawnColor),7);
        //moving the 7 RED students to the Dining Room
        for(int i=0;i<7;i++){
            schoolBoard.moveStudToDining(pawnColor);
        }
        assertEquals(schoolBoard.getStudentsWaiting().get(pawnColor),0);
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),7);
        assertEquals(schoolBoard.getNumStudentsWaiting(),0);
        //adding another 4 RED students to the Waiting Room
        for(int i=0;i<4;i++){
            schoolBoard.addStudToWaiting(pawnColor);
        }
        assertEquals(schoolBoard.getStudentsWaiting().get(pawnColor),4);
        assertEquals(schoolBoard.getNumStudentsWaiting(),4);
        //moving the 3 RED students to the Dining Room
        for(int i=0;i<3;i++){
            schoolBoard.moveStudToDining(pawnColor);
        }
        assertEquals(schoolBoard.getStudentsWaiting().get(pawnColor),1);
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),10);
        assertEquals(schoolBoard.getNumStudentsWaiting(),1);

        //I add 10 students and the ExpertVariant is 'true'
        assertEquals(schoolBoard.getNumCoins(),4); //initially numCoins was 1

        Throwable exception = assertThrows(TooManyPawnsPresent.class, () -> {
            schoolBoard.moveStudToDining(pawnColor);
        });
        assertEquals("Too many pawns are present!", exception.getMessage());
    }


    @Test
    @DisplayName("moveStudToIslandNull")
    void moveStudToIslandNull() {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=null;
        Island island=new Island(0);

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.moveStudToIsland(pawnColor,island);
        });
        assertEquals("Parameters cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("moveStudToIslandNoStud")
    void moveStudToIslandNoStud() {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=PawnColor.RED;
        Island island=new Island(0);

        Throwable exception = assertThrows(NoPawnPresentException.class, () -> {
            schoolBoard.moveStudToIsland(pawnColor,island);
        });
        assertEquals("Pawn not present!", exception.getMessage());
    }

    @Test
    @DisplayName("moveStudToIsland")
    void moveStudToIsland() throws TooManyPawnsPresent, NoPawnPresentException {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=PawnColor.RED;
        Island island=new Island(0);

        schoolBoard.addStudToWaiting(pawnColor);
        assertEquals(schoolBoard.getNumStudentsWaiting(),1);
        schoolBoard.moveStudToIsland(pawnColor,island);
        assertEquals(schoolBoard.getNumStudentsWaiting(),0);
        assertEquals(island.getStudents().get(pawnColor),1);
    }

    @Test
    @DisplayName("addProfessorNull")
    void addProfessorNull(){
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.addProfessor(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }


    @Test
    @DisplayName("addProfessor")
    void addProfessor() throws TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
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
    void removeProfessorNull(){
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.removeProfessor(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("removeProfessor")
    void removeProfessor() throws NoPawnPresentException, TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
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
    void updateNumberOfTowersTooMany(){
        SchoolBoard schoolBoard=new SchoolBoard(2,false);

        assertEquals(schoolBoard.getNumTowers(),8);
        Throwable exception = assertThrows(TooManyTowersException.class, () -> {
            schoolBoard.updateNumberOfTowers(1);
        });
        assertEquals("Too many towers are present!", exception.getMessage());
        assertEquals(schoolBoard.getNumTowers(),8);
    }

    @Test
    @DisplayName("updateNumberOfTowersNoTow")
    void updateNumberOfTowersNoTow(){
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        assertEquals(schoolBoard.getNumTowers(),8);
        Throwable exception = assertThrows(NoTowersException.class, () -> {
            schoolBoard.updateNumberOfTowers(-9);
        });
        assertEquals("No towers are present!", exception.getMessage());
        assertEquals(schoolBoard.getNumTowers(),8);
    }

    @Test
    @DisplayName("updateNumberOfTowers")
    void updateNumberOfTowers() throws TooManyTowersException, NoTowersException {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);

        assertEquals(schoolBoard.getNumTowers(),8);
        schoolBoard.updateNumberOfTowers(-5);
        assertEquals(schoolBoard.getNumTowers(),3);
        schoolBoard.updateNumberOfTowers(2);
        assertEquals(schoolBoard.getNumTowers(),5);
    }

    @Test
    @DisplayName("addStudToWaitingNull")
    void addStudToWaitingNull() {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.addStudToWaiting(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    /*NOTE: This test covers also the general case*/
    @Test
    @DisplayName("addStudToWaitingTooMany")
    void addStudToWaitingTooMany() throws TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(2,false);
        PawnColor pawnColor=PawnColor.RED;

        for(int i=0;i<7;i++){
            schoolBoard.addStudToWaiting(pawnColor);
        }
        assertEquals(schoolBoard.getNumStudentsWaiting(),7);
        Throwable exception = assertThrows(TooManyPawnsPresent.class, () -> {
            schoolBoard.addStudToWaiting(pawnColor);
        });
        assertEquals("Too many pawns are present!", exception.getMessage());
        assertEquals(schoolBoard.getNumStudentsWaiting(),7);
    }

    @Test
    @DisplayName("decreaseNumCoinsNeg")
    void decreaseNumCoinsNeg(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        assertEquals(schoolBoard.getNumCoins(),1);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            schoolBoard.decreaseNumCoins(-9);
        });
        assertEquals("Parameter cannot be negative!", exception.getMessage());
        assertEquals(schoolBoard.getNumCoins(),1);
    }

    @Test
    @DisplayName("decreaseNumCoinsNoCoins")
    void decreaseNumCoinsNoCoins(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        assertEquals(schoolBoard.getNumCoins(),1);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            schoolBoard.decreaseNumCoins(3);
        });
        assertEquals("Number of coins insufficient!", exception.getMessage());
        assertEquals(schoolBoard.getNumCoins(),1);
    }

    @Test
    @DisplayName("decreaseNumCoins")
    void decreaseNumCoins(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        assertEquals(schoolBoard.getNumCoins(),1);
        schoolBoard.decreaseNumCoins(1);
        assertEquals(schoolBoard.getNumCoins(),0);
    }

    @Test
    @DisplayName("addStudToDiningNull")
    void addStudToDiningNull(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        PawnColor pawnColor=null;

        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.addStudToDining(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("addStudToDining")
    void addStudToDining(){
        SchoolBoard schoolBoard=new SchoolBoard(2,true);
        PawnColor pawnColor=PawnColor.RED;
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),0);
        try{
            schoolBoard.addStudToWaiting(pawnColor);
            schoolBoard.addStudToDining(pawnColor);
        }
        catch (TooManyPawnsPresent e){
            e.getMessage();
        }
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),1);
    }

    @Test
    @DisplayName("addStudToDiningTooMany")
    void addStudToDiningTooMany() throws TooManyPawnsPresent, NoPawnPresentException {
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=PawnColor.RED;

        //adding 10 RED students to the waiting room and move them to the dining room
        for(int i=0;i<10;i++){
            schoolBoard.addStudToWaiting(pawnColor);
            schoolBoard.moveStudToDining(pawnColor);
        }

        Throwable exception = assertThrows(TooManyPawnsPresent.class, () -> {
            schoolBoard.addStudToDining(pawnColor);
        });
        assertEquals("Too many pawns are present!", exception.getMessage());
    }

    @Test
    @DisplayName("removeStudentsNull")
    void removeStudentsNull(){
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=null;
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            schoolBoard.removeStudents(pawnColor);
        });
        assertEquals("Parameter cannot be null!", exception.getMessage());
    }

    @Test
    @DisplayName("removeStudentsMore3")
    void removeStudentsMore3() throws TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=PawnColor.RED;

        for(int i=0;i<4;i++){
            schoolBoard.addStudToDining(pawnColor);
        }
        int removed= schoolBoard.removeStudents(pawnColor);
        assertEquals(removed,3);
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),1);

    }

    @Test
    @DisplayName("removeStudentsLess3")
    void removeStudentsLess3() throws TooManyPawnsPresent {
        SchoolBoard schoolBoard=new SchoolBoard(3,true);
        PawnColor pawnColor=PawnColor.RED;

        for(int i=0;i<2;i++){
            schoolBoard.addStudToDining(pawnColor);
        }
        int removed= schoolBoard.removeStudents(pawnColor);
        assertEquals(removed,2);
        assertEquals(schoolBoard.getStudentsDining().get(pawnColor),0);

    }
}