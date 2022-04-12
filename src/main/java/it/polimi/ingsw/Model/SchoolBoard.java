package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SchoolBoard
 * @author Matteo Luppi,Paolo Lussana
 */
public class SchoolBoard {
    private Map<PawnColor, Boolean> professors;
    private Map<PawnColor, Integer> studentsDining;
    private Map<PawnColor, Integer> studentsWaiting;
    private int numTowers;
    private final int numMaxTowers;
    private int numCoins;
    private int numStudentsWaiting;
    private final int numMaxStudentsWaiting;


    public SchoolBoard(){
        this.professors = new HashMap<>();
        professors.put(PawnColor.RED,false);
        professors.put(PawnColor.BLUE,false);
        professors.put(PawnColor.YELLOW,false);
        professors.put(PawnColor.PINK,false);
        professors.put(PawnColor.GREEN,false);

        this.studentsDining = new HashMap<>();
        studentsDining.put(PawnColor.RED,0);
        studentsDining.put(PawnColor.BLUE,0);
        studentsDining.put(PawnColor.YELLOW,0);
        studentsDining.put(PawnColor.PINK,0);
        studentsDining.put(PawnColor.GREEN,0);

        this.studentsWaiting = new HashMap<>();
        studentsWaiting.put(PawnColor.RED,0);
        studentsWaiting.put(PawnColor.BLUE,0);
        studentsWaiting.put(PawnColor.YELLOW,0);
        studentsWaiting.put(PawnColor.PINK,0);
        studentsWaiting.put(PawnColor.GREEN,0);


        if(Game.getInstance().getMaxNumPlayers() == 2 || Game.getInstance().getMaxNumPlayers() == 4){
            numTowers = 8;
            numMaxTowers = 8;
            numMaxStudentsWaiting = 7;
        }
        else{
            numTowers = 6;
            numMaxTowers = 6;
            numMaxStudentsWaiting = 9;
        }
        if(Game.getInstance().getExpertsVariant()){
            this.numCoins = 1;
        }
        else{
            this.numCoins = 0;
        }
    }

    public Map<PawnColor, Boolean> getProfessors() {
        return professors;
    }

    public int getNumMaxStudentsWaiting() {
        return numMaxStudentsWaiting;
    }

    public int getNumMaxTowers() {
        return numMaxTowers;
    }

    public int getNumTowers() {
        return numTowers;
    }

    public Map<PawnColor, Integer> getStudentsDining() {
        return studentsDining;
    }

    public Map<PawnColor, Integer> getStudentsWaiting() {
        return studentsWaiting;
    }

    public int getNumStudentsWaiting() {
        return numStudentsWaiting;
    }

    /**
     * Method to move a student from the Waiting Room(entrance) to the DiningRoom
     *
     * @param pawnColor corresponds to a Student
     */
    public void moveStudToDining(PawnColor pawnColor) throws NoPawnPresentException, TooManyPawnsPresent {
        if(studentsWaiting.get(pawnColor)<=0){
            throw new NoPawnPresentException();
        }
        else if(studentsDining.get(pawnColor)>=10){
            throw new TooManyPawnsPresent();
        }
        else{
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)-1);
            studentsDining.put(pawnColor,studentsDining.get(pawnColor)+1);
            numStudentsWaiting--;
            if(studentsDining.get(pawnColor)%3 == 0 && Game.getInstance().getExpertsVariant()){
                numCoins++;
            }
        }
    }

    /**
     * Method to move a student from the Waiting Room(entrance) to an Island
     * @param pawnColor corresponds to the Student
     * @param island corresponds to the Island the player want to move the student on
     */
    public void moveStudToIsland(PawnColor pawnColor,Island island) throws NoPawnPresentException {
        if(studentsWaiting.get(pawnColor)<=0){
            throw new NoPawnPresentException();
        }
        else if(pawnColor==null || island==null){
            throw new NullPointerException();
        }
        else{
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)-1);
            numStudentsWaiting--;
            island.addStudent(pawnColor);
        }
    }

    public void addProfessor(PawnColor pawnColor) throws TooManyPawnsPresent {
        if(professors.get(pawnColor)){
            throw new TooManyPawnsPresent();
        }
        else if(pawnColor==null){
            throw new NullPointerException();
        }
        else {
            professors.put(pawnColor,true);
        }
    }
    public void removeProfessor(PawnColor pawnColor) throws NoPawnPresentException {
        if(!professors.get(pawnColor)){
            throw new NoPawnPresentException();
        }
        else if(pawnColor==null){
            throw new NullPointerException();
        }
        else {
            professors.put(pawnColor,false);
        }
    }

    /**
     * method to add (or remove) towers from the school board
     * @param num the number of towers to add (or remove if num is negative)
     */
    public void addTowers(int num) throws TooManyTowersException,NoTowersException{
        if(numTowers+num >= numMaxTowers){
            throw new TooManyTowersException();
        }
        else if(numTowers+num <= 0){
            throw new NoTowersException();
        }
        else {
            numTowers = numTowers + num;
        }
    }

    /**
     * Method to add a Student to the Waiting Room (Entrance)
     * @param pawnColor corresponds to the Student
     */
    public void addStudToWaiting(PawnColor pawnColor) throws TooManyPawnsPresent {
        if (numStudentsWaiting>=numMaxStudentsWaiting){
            throw new TooManyPawnsPresent();
        }
        else if(pawnColor==null){
            throw new NullPointerException();
        }
        else {
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)+1);
            numStudentsWaiting++;
        }
    }
}
