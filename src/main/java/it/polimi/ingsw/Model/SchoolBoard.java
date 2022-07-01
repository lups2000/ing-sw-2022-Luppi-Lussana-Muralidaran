package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.NoTowersException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import it.polimi.ingsw.Model.Exceptions.TooManyTowersException;
import it.polimi.ingsw.network.Messages.ServerSide.Generic;
import it.polimi.ingsw.observer.Observable;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the SchoolBoard.
 */
public class SchoolBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = 7151174979897245763L;
    private Map<PawnColor, Boolean> professors;
    private Map<PawnColor, Integer> studentsDining;
    private Map<PawnColor, Integer> studentsWaiting;
    private int numTowers;
    private final int numMaxTowers;
    private int numCoins;
    private int numStudentsWaiting;
    private final int numMaxStudentsWaiting;
    private int numMaxPlayers;
    private boolean experts;

    /**
     * Constructor
     * @param numMaxPlayers is the maximum number of players of the game.
     * @param experts is the expert variant.
     */
    public SchoolBoard(int numMaxPlayers,boolean experts){
        this.professors = new HashMap<>();
        this.numMaxPlayers=numMaxPlayers;
        this.experts=experts;
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

        this.numStudentsWaiting=0;

        if(numMaxPlayers == 2 || numMaxPlayers == 4){
            numTowers = 8;
            numMaxTowers = 8;
            numMaxStudentsWaiting = 7;
        }
        else{
            numTowers = 6;
            numMaxTowers = 6;
            numMaxStudentsWaiting = 9;
        }
        if(experts){
            this.numCoins = 1;
        }
    }

    public Map<PawnColor, Boolean> getProfessors() {return professors;}
    public int getNumMaxStudentsWaiting() {return numMaxStudentsWaiting;}
    public int getNumMaxTowers() {return numMaxTowers;}
    public int getNumTowers() {return numTowers;}
    public Map<PawnColor, Integer> getStudentsDining() {return studentsDining;}
    public Map<PawnColor, Integer> getStudentsWaiting() {return studentsWaiting;}
    public int getNumStudentsWaiting() {return numStudentsWaiting;}
    public int getNumMaxPlayers() {return numMaxPlayers;}
    public boolean isExperts() {return experts;}
    public int getNumCoins() {return numCoins;}
    public void setNumStudentsWaiting(int numStudentsWaiting) {this.numStudentsWaiting = numStudentsWaiting;}

    /**
     * Method to decrease the number of coins.
     * @param numCoins
     */
    public void decreaseNumCoins(int numCoins){
        if(numCoins<=0){
            throw new IllegalArgumentException("Parameter cannot be negative!");
        }
        else if(numCoins>this.numCoins){
            throw new IllegalArgumentException("Number of coins insufficient!");
        }
        this.numCoins=this.numCoins-numCoins;
    }

    /**
     * Method to move a student from the Waiting Room(entrance) to the DiningRoom.
     * @param pawnColor color of the respective student.
     */
    public void moveStudToDining(PawnColor pawnColor) throws NoPawnPresentException, TooManyPawnsPresent {
        if(pawnColor==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else if(studentsWaiting.get(pawnColor)<=0){
            throw new NoPawnPresentException();
        }
        else if(studentsDining.get(pawnColor)>=10){
            throw new TooManyPawnsPresent();
        }
        else{
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)-1);
            studentsDining.put(pawnColor,studentsDining.get(pawnColor)+1);
            numStudentsWaiting--;
            if(studentsDining.get(pawnColor)%3 == 0 && experts){
                numCoins++;
            }
        }
    }

    /**
     * Method to move a student from the Waiting Room(entrance) to an Island.
     * @param pawnColor pawnColor color of the respective student.
     * @param island island where the player wants to move his student.
     */
    public void moveStudToIsland(PawnColor pawnColor,Island island) throws NoPawnPresentException {
        if(pawnColor==null || island==null){
            throw new NullPointerException("Parameters cannot be null!");
        }
        else if(studentsWaiting.get(pawnColor)<=0){
            throw new NoPawnPresentException();
        }
        else{
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)-1);
            numStudentsWaiting--;
            island.addStudent(pawnColor);
        }
    }

    /**
     * Method to add a professor to the SchoolBoard.
     * @param pawnColor pawnColor of the respective professor.
     * @throws TooManyPawnsPresent if there is already the professor of that color.
     */
    public void addProfessor(PawnColor pawnColor) throws TooManyPawnsPresent {
        if(pawnColor==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else if(professors.get(pawnColor)){
            throw new TooManyPawnsPresent();
        }
        else {
            professors.put(pawnColor,true);
        }
    }

    /**
     * Method to remove a professor from the SchoolBoard.
     * @param pawnColor pawnColor of the respective professor.
     * @throws NoPawnPresentException if there is not the professor of that color.
     */
    public void removeProfessor(PawnColor pawnColor) throws NoPawnPresentException {
        if(pawnColor==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else if(!professors.get(pawnColor)){
            throw new NoPawnPresentException();
        }
        else {
            professors.put(pawnColor,false);
        }
    }

    /**
     * Method to add (or remove) towers from the SchoolBoard.
     * @param num the number of towers to add (or remove if num is negative).
     */
    public void updateNumberOfTowers(int num) throws TooManyTowersException, NoTowersException {
        if(numTowers+num > numMaxTowers){
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
     * Method to add a Student to the Waiting Room (Entrance).
     * @param pawnColor pawnColor of the student.
     */
    public void addStudToWaiting(PawnColor pawnColor) throws TooManyPawnsPresent {
        if(pawnColor==null) {
            throw new NullPointerException("Parameter cannot be null!");
        }
        else if (numStudentsWaiting>=numMaxStudentsWaiting){
            throw new TooManyPawnsPresent();
        }
        else {
            studentsWaiting.put(pawnColor,studentsWaiting.get(pawnColor)+1);
            numStudentsWaiting++;
        }
    }

    /**
     * Method to add a Student to the Dining Room, invoked by the StudentToDining character card.
     * @param pawnColor pawnColor of the student.
     */
    public void addStudToDining(PawnColor pawnColor) throws TooManyPawnsPresent {
        if(pawnColor==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else if(studentsDining.get(pawnColor)>=10){
            throw new TooManyPawnsPresent();
        }
        else {
            studentsDining.put(pawnColor,studentsDining.get(pawnColor)+1);
        }
    }

    /**
     * Method to remove a maximum of 3 students of a certain color from the SchoolBoard.
     * It's an effect of the ColorToStudentBag character card.
     * @param chosen is the pawnColor to remove.
     * @return the students effectively removed.
     */
    public int removeStudents(PawnColor chosen){
        if(chosen==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else{
            int removed = studentsDining.get(chosen);
            if(removed >= 3){
                studentsDining.put(chosen,studentsDining.get(chosen)-3);
                return 3;
            }
            else{
                studentsDining.put(chosen,0);
                return removed;
            }
        }
    }
}
