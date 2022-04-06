package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;


public class StudentBag {
    private int numStudents;
    private Map<PawnColor,Integer> students;

    /**
     * constructor
     * the studentBag at the start of the game contains all the students, 26 per color
     */
    public StudentBag(){
        this.numStudents = 130;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,26);
        students.put(PawnColor.BLUE,26);
        students.put(PawnColor.YELLOW,26);
        students.put(PawnColor.PINK,26);
        students.put(PawnColor.GREEN,26);
    }

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public int getNumStudents() {
        return numStudents;
    }

    /**
     * method to add students to the bag
     * @param added is the HashMap that counts, for each color, how many students need to be added to the studentBag
     */
    public void addStudents(Map<PawnColor,Integer> added){
        added.forEach((k, v) -> students.merge(k, v, Integer::sum));
    }

    /**
     * method to extract random students from the studentBag
     * @param x is the number of students to extract
     * @return the HashMap extracted which counts, for each color, how many students are extracted
     */
    public Map<PawnColor,Integer> drawStudent(int x){
        if(numStudents<x){
            System.out.println("è possibile estrarre solamente " + numStudents + " studenti");  //ECCEZIONE?
            x = numStudents;
        }
        Map<PawnColor,Integer> extracted = new HashMap<>();
        extracted.put(PawnColor.RED,0);
        extracted.put(PawnColor.BLUE,0);
        extracted.put(PawnColor.YELLOW,0);
        extracted.put(PawnColor.PINK,0);
        extracted.put(PawnColor.GREEN,0);
        numStudents = numStudents - x;

        for(int i=0;i<x;i++){
            int rand = (int)(Math.random() * 5);
            PawnColor p = null;

            for(PawnColor d : PawnColor.values()){
                if(d.ordinal() == rand){
                    p = d;
                    break;
                }
            }

            if(students.get(p) >= 1) {
                extracted.put(p, extracted.get(p) + 1);
                students.put(p, students.get(p) - 1);
            }
            else i--;
        }
        return extracted;
    }
}
