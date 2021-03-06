package it.polimi.ingsw.Model.CharacterCards;

//In setup, draw 6 students and place them on this card.
//EFFECT: you may take up to 3 students from this card and replace them with the same number of students from your entrance

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SwitchStudents extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 3645873027497384707L;
    private Map<PawnColor,Integer> students;

    public SwitchStudents(Game game) throws NoPawnPresentException {
        cost = 1;
        used = false;
        this.game = game;
        this.type = CharacterCardType.SWITCH_STUDENTS;

        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        for(int i=0;i<6;i++){
            PawnColor pawnColor= game.getStudentBag().drawStudent();
            students.put(pawnColor,students.get(pawnColor)+1);
        }
    }

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public void effect(Map<PawnColor,Integer> toPick, Map<PawnColor,Integer> toDeposit) throws TooManyPawnsPresent, NoPawnPresentException {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING_ACTION)){
                for(PawnColor pawnColor : PawnColor.values()){
                    if(toPick.get(pawnColor) > 0){
                        for(int i=0;i<toPick.get(pawnColor);i++){
                            students.put(pawnColor,students.get(pawnColor)-1);
                            player.getSchoolBoard().addStudToWaiting(pawnColor);
                        }
                    }
                }
            }
        }

        used();
    }

    @Override
    public String toString() {
        return "SwitchStudent ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}

