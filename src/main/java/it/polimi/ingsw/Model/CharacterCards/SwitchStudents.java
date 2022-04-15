package it.polimi.ingsw.Model.CharacterCards;

//PRE: all'inizio della partita pescate 6 studenti e piazzateli su questa carta
//EFF: puoi prendeere fino a 3 studenti da questa carta e scambiarli con altrettanti studenti presenti nel tuo ingresso

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.PawnColor;

import java.util.HashMap;
import java.util.Map;

public class SwitchStudents extends CharacterCard{

    private Map<PawnColor,Integer> students;

    public SwitchStudents() throws NoPawnPresentException {
        cost = 1;
        used = false;

        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        for(int i=0;i<6;i++){
            PawnColor pawnColor= Game.getInstance().getStudentBag().drawStudent();
            students.put(pawnColor,students.get(pawnColor)+1);
        }
    }

    public void effect() {

        used();
    }
}

