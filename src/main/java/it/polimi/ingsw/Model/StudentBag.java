package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

/**
 * @author Paolo Lussana
 */
public class StudentBag {
    private int numStudents;
    private Map<PawnColor,Integer> students;

    /**
     * constructor
     * the studentBag at the start of the game contains 120 students, 24 per color, since 10 students are already placed on 10 islands (2 per color)
     */
    public StudentBag(){
        this.numStudents = 120;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,24);
        students.put(PawnColor.BLUE,24);
        students.put(PawnColor.YELLOW,24);
        students.put(PawnColor.PINK,24);
        students.put(PawnColor.GREEN,24);
    }

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public int getNumStudents() {
        return numStudents;
    }

    /**
     * method to add students to the bag, it can only be invoked by a specific CharacterCard
     * @param toAdd is the chosen color of the students to add to the StudentBag
     * @param num is the number of students (of the chosen color) to add to the StudentBag
     */
    public void addStudents(PawnColor toAdd, int num){
        students.put(toAdd, students.get(toAdd) + num);
        numStudents = numStudents + num;
    }

    /**
     * method to extract a random student from the studentBag
     * @return the PawnColor of the student extracted
     */
    public PawnColor drawStudent() throws NoPawnPresentException {
        if(numStudents == 0){
            throw new NoPawnPresentException();
        }
        PawnColor drawn = null;
        numStudents--;
        boolean check = true;   //boolean to check if there is still a student of the drawn color

        while(check) {
            int rand = (int) (Math.random() * 5);

            for (PawnColor d : PawnColor.values()) {
                if (d.ordinal() == rand) {
                    drawn = d;
                    break;
                }
            }

            if (students.get(drawn) >= 1) {
                students.put(drawn, students.get(drawn) - 1);
                check = false;
            }
        }
        return drawn;
    }
}
