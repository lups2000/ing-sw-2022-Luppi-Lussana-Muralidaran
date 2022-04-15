package it.polimi.ingsw.Model.CharacterCards;

//PRE: all'inizio della partita, pescate 4 studenti e piazzateli su questa carta
//EFF: prendi 1 studente da questa carta e piazzalo nella tua sala. poi pesca un nuovo studente dal sacchetto e posizionalo su questa carta

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.NoPawnPresentException;
import it.polimi.ingsw.Model.PawnColor;

import java.util.HashMap;
import java.util.Map;

public class StudentToDining extends CharacterCard{

    private Map<PawnColor,Integer> students;

    public StudentToDining() throws NoPawnPresentException {
        cost = 2;
        used = false;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        for(int i=0;i<4;i++){
            PawnColor pawnColor= Game.getInstance().getStudentBag().drawStudent();
            students.put(pawnColor,students.get(pawnColor)+1);
        }
    }

    public void effect() {

        used();
    }
}

