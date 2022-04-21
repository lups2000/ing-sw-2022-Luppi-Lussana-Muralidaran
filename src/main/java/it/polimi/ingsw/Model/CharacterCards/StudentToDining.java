package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;
import java.util.HashMap;
import java.util.Map;

//In setup, draw 4 students and place them on this card.
//EFFECT: take 1 student from this card and place it in your dining room.
//  Then, draw a new student from the bag and place it on this card

public class StudentToDining extends CharacterCard{

    private Map<PawnColor,Integer> students;

    public StudentToDining(Game game) throws NoPawnPresentException {
        cost = 2;
        used = false;
        this.game = game;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        for(int i=0;i<4;i++){
            PawnColor pawnColor= game.getStudentBag().drawStudent();
            students.put(pawnColor,students.get(pawnColor)+1);
        }
    }

    public void effect(PawnColor chosen) throws NoPawnPresentException, TooManyPawnsPresent {
        if(students.get(chosen) == 0){
            throw new NoPawnPresentException();
        }
        else{
            students.put(chosen,students.get(chosen)-1);
            for(Player player : game.getPlayers()){
                if((player.getStatus()).equals(PlayerStatus.PLAYING)){
                    player.getSchoolBoard().addStudToDining(chosen);
                }
            }
            PawnColor sorted = game.getStudentBag().drawStudent();
            students.put(sorted,students.get(sorted)+1);
        }
        used();
    }
}

