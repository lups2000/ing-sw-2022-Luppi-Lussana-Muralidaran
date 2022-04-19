package it.polimi.ingsw.Model.CharacterCards;

//PRE: all'inizio della partita, pescate 4 studenti e piazzateli su questa carta
//EFF: prendi 1 studente da questa carta e piazzalo nella tua sala. poi pesca un nuovo studente dal sacchetto e posizionalo su questa carta

import it.polimi.ingsw.Model.*;

import java.util.HashMap;
import java.util.Map;

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

    public void effect(PawnColor chosen) throws NoPawnPresentException,TooManyPawnsPresent{
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
