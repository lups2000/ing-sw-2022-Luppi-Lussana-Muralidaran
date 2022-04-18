package it.polimi.ingsw.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Matteo Luppi
 */
class StudentBagTest {
    StudentBag studentBag=new StudentBag();

    @Test
    @DisplayName("test setup")
    void setup() {
        //we simply test that the constructor works well
        assertEquals(studentBag.getNumStudents(),120);
        assertEquals(studentBag.getStudents().get(PawnColor.RED),24);
        assertEquals(studentBag.getStudents().get(PawnColor.GREEN),24);
        assertEquals(studentBag.getStudents().get(PawnColor.YELLOW),24);
        assertEquals(studentBag.getStudents().get(PawnColor.PINK),24);
        assertEquals(studentBag.getStudents().get(PawnColor.BLUE),24);
    }

    @Test
    @DisplayName("addPositiveNumberOfStudents")
    void addPositiveNumberOfStudents() {
        //in this case I choose RED as PawnColor and 2 as num
        setup();

        studentBag.addStudents(PawnColor.RED,2);

        assertEquals(studentBag.getNumStudents(),122);
        assertEquals(studentBag.getStudents().get(PawnColor.RED),26);
        assertEquals(studentBag.getStudents().get(PawnColor.GREEN),24);
        assertEquals(studentBag.getStudents().get(PawnColor.YELLOW),24);
        assertEquals(studentBag.getStudents().get(PawnColor.PINK),24);
        assertEquals(studentBag.getStudents().get(PawnColor.BLUE),24);
    }

    @Test
    @DisplayName("addNegativeNumberOfStudents")
    void addNegativeNumberOfStudents() {
        //in this case I choose RED as PawnColor and -2 as num
        setup();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            studentBag.addStudents(PawnColor.RED,-2);
        });
        assertEquals("You cannot add a number of student which is negative!", exception.getMessage());

        assertEquals(studentBag.getNumStudents(),120);
        assertEquals(studentBag.getStudents().get(PawnColor.RED),24);
        assertEquals(studentBag.getStudents().get(PawnColor.GREEN),24);
        assertEquals(studentBag.getStudents().get(PawnColor.YELLOW),24);
        assertEquals(studentBag.getStudents().get(PawnColor.PINK),24);
        assertEquals(studentBag.getStudents().get(PawnColor.BLUE),24);
    }

    @Test
    @DisplayName("drawStudentWithException")
    void drawStudentWithException() throws NoPawnPresentException {
        for(int i=0;i<120;i++){
            studentBag.drawStudent();
        }
        //now the student bag is empty
        assertEquals(studentBag.getNumStudents(),0);
        assertEquals(studentBag.getStudents().get(PawnColor.RED),0);
        assertEquals(studentBag.getStudents().get(PawnColor.GREEN),0);
        assertEquals(studentBag.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(studentBag.getStudents().get(PawnColor.PINK),0);
        assertEquals(studentBag.getStudents().get(PawnColor.BLUE),0);

        Throwable exception = assertThrows(NoPawnPresentException.class, () -> {
            studentBag.drawStudent();
        });
        assertEquals("Pawn not present!", exception.getMessage());

        assertEquals(studentBag.getNumStudents(),0);
        assertEquals(studentBag.getStudents().get(PawnColor.RED),0);
        assertEquals(studentBag.getStudents().get(PawnColor.GREEN),0);
        assertEquals(studentBag.getStudents().get(PawnColor.YELLOW),0);
        assertEquals(studentBag.getStudents().get(PawnColor.PINK),0);
        assertEquals(studentBag.getStudents().get(PawnColor.BLUE),0);

    }

    @Test
    @DisplayName("drawStudentCorrectly")
    void drawStudentCorrectly() throws NoPawnPresentException {
        setup();
        PawnColor pawnColor=studentBag.drawStudent();
        assertTrue(pawnColor.equals(PawnColor.RED) || pawnColor.equals(PawnColor.BLUE) || pawnColor.equals(PawnColor.GREEN) || pawnColor.equals(PawnColor.YELLOW) || pawnColor.equals(PawnColor.PINK));
        assertEquals(studentBag.getNumStudents(),119);
        for (PawnColor pawnColor1 :PawnColor.values()){
            if(pawnColor.equals(pawnColor1)){
                assertEquals(studentBag.getStudents().get(pawnColor1),23);
            }
            else{
                assertEquals(studentBag.getStudents().get(pawnColor1),24);
            }
        }

    }
}