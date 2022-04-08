package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SchoolBoard
 * @author Matteo Luppi
 */
public class SchoolBoard {
    private Map<PawnColor, Boolean> professors;
    private Map<PawnColor, Integer> studentsDining;
    private Map<PawnColor, Integer> studentsWaiting;
    private int numTowers;
    private final int numMaxTowers;
    private int numStudentsWaiting;
    private int numMaxStudentsWaiting;
    private Player player;

    /**
     * Constructor
     * @param numMaxTowers number of towers,it depends on the number of players
     * @param numMaxStudentsWaiting number of students in the Waiting Room, it depends on the number of players
     * @param player represents the owner of the schoolBoard
     */
    public SchoolBoard(int numMaxTowers,int numMaxStudentsWaiting,Player player){
        this.numTowers=numMaxTowers;
        this.numMaxTowers=numMaxTowers;
        this.professors=new HashMap<>();
        this.studentsDining=new HashMap<>();
        this.studentsWaiting=new HashMap<>();
        this.numStudentsWaiting=numMaxStudentsWaiting;
        this.numMaxStudentsWaiting=numMaxStudentsWaiting;
        this.player=player;
    }

    public Map<PawnColor, Boolean> getProfessors() {return professors;}
    public int getNumTowers() {return numTowers;}
    public Map<PawnColor, Integer> getStudentsDining() {return studentsDining;}
    public Map<PawnColor, Integer> getStudentsWaiting() {return studentsWaiting;}
    public int getNumStudentsWaiting() {return numStudentsWaiting;}
    public Player getPlayer() {return player;}

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
            if(studentsDining.get(pawnColor)%3 == 0){
                this.player.addCoin(1);
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
    public void addTower() throws TooManyTowersException {
        if(numTowers>=numMaxTowers){
            throw new TooManyTowersException();
        }
        else {
            numTowers++;
        }
    }
    public void removeTower() throws NoTowersException {
        if(numTowers<=0){
            throw new NoTowersException();
        }
        else {
            numTowers--;
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
