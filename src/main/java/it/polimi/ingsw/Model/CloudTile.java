package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Cloud Tile.
 */
public class CloudTile implements Serializable {
    @Serial
    private static final long serialVersionUID = -9138279216308762534L;
    private int maxNumStudents;
    private int numStudents;
    private int id;
    private Map<PawnColor,Integer> students;
    private int numMaxPlayers;//forse non serve

    /**
     * Constructor.
     * @param id integer value that represents the single cloud Tile.
     * @param numMaxPlayers integer value that represents the maximum number of players
*    * which is an indicator of the capacity of students of the Tile.
     */
    public CloudTile(int id,int numMaxPlayers) {
        this.id = id;
        this.numMaxPlayers=numMaxPlayers;
        this.numStudents=0;
        if(numMaxPlayers == 2 || numMaxPlayers == 4){
            this.maxNumStudents = 3;
        }
        else{
            this.maxNumStudents = 4;
        }
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);
    }

    public int getMaxNumStudents() {return maxNumStudents;}
    public Map<PawnColor, Integer> getStudents() {return students;}
    public int getId() {return id;}
    public int getNumStudents() {return numStudents;}

    /**
     * Method to pick the students from a cloud Tile.
     * @return All the students presents on this cloud tile.
     */
    public Map<PawnColor,Integer> pickStudents(){
        Map<PawnColor,Integer> picked = new HashMap<>();
        picked.putAll(students);
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);
        numStudents=0;
        return picked;
    }

    /**
     * Method to refill the cloud tile.
     * @param drawn is the pawn color of the student drawn from the Student Bag.
     */
    public void addStudent(PawnColor drawn) throws TooManyPawnsPresent {
        if(drawn==null ){
            throw new NullPointerException("The parameter cannot be null!");
        }
        else if(numStudents>=maxNumStudents){
            throw new TooManyPawnsPresent();
        }
        else{
            students.put(drawn,students.get(drawn) + 1);
            numStudents++;
        }
    }
}
