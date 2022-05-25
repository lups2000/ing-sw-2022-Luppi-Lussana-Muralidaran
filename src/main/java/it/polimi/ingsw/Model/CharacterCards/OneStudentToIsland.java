package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.PawnColor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matteo Luppi
 */

//In setup, draw 4 students and place them on this card.
//EFFECT: take 1 student from this card and place it on an island of your choice.
    //Then, draw a new student from the bag and place it on this card

public class OneStudentToIsland extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 4727649867986987547L;
    private Map<PawnColor,Integer> students;

    /**
     * Constructor
     * set cost,set variable used and then draw randomly 4 students from studentBag
     */
    public OneStudentToIsland(Game game) throws NoPawnPresentException {
        cost = 1;
        used = false;
        this.game = game;
        this.type = CharacterCardType.ONE_STUDENT_TO_ISLAND;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        for(int i=0;i<4;i++){
            PawnColor pawnColor=game.getStudentBag().drawStudent();
            students.put(pawnColor,students.get(pawnColor)+1);
        }
    }

    /**
     * Private method called only in the method 'effect(...)'
     */
    private void addStudent() throws NoPawnPresentException {
        PawnColor pawnColor=game.getStudentBag().drawStudent();
        students.put(pawnColor,students.get(pawnColor)+1);
    }

    /**
     * Private method called only in the method 'effect(...)'
     */
    private void removeStudent(PawnColor pawnColor){
        students.put(pawnColor,students.get(pawnColor)-1);
    }

    /**
     * Overloading of the method effect
     * @param island where we want to put the student
     * @param pawnColorChosen color of the student we want to put on the island
     */
    public void effect(Island island,PawnColor pawnColorChosen) throws NoPawnPresentException {
        if(students.get(pawnColorChosen)<=0){
            throw new NoPawnPresentException();
        }
        else{
            island.addStudent(pawnColorChosen);
            removeStudent(pawnColorChosen); //remove the student from the card
            addStudent(); //put new student on the card
        }
        used();
    }

    @Override
    public String toString() {
        return "OneStudentToIsland ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}
