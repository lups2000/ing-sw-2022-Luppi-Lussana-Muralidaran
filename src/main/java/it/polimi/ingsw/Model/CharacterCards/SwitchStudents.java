package it.polimi.ingsw.Model.CharacterCards;

//PRE: all'inizio della partita pescate 6 studenti e piazzateli su questa carta
//EFF: puoi prendere fino a 3 studenti da questa carta e scambiarli con altrettanti studenti presenti nel tuo ingresso

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.util.HashMap;
import java.util.Map;

public class SwitchStudents extends CharacterCard{

    private Map<PawnColor,Integer> students;

    public SwitchStudents(Game game) throws NoPawnPresentException {
        cost = 1;
        used = false;
        this.game = game;

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

    public void effect(Map<PawnColor,Integer> toPick, Map<PawnColor,Integer> toDeposit) throws TooManyPawnsPresent {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING)){
                for(PawnColor color : PawnColor.values()){
                    if(toPick.get(color) > 0){
                        for(int i=0;i<toPick.get(color);i++){
                            students.put(color,students.get(color)-1);
                            player.getSchoolBoard().addStudToWaiting(color);
                        }
                    }
                    if(toDeposit.get(color) > 0){
                        for(int i=0;i<toDeposit.get(color);i++){
                            students.put(color,students.get(color)+1);
                            player.getSchoolBoard().removeStudents(color);
                        }
                    }
                }
            }
        }

        used();
    }
}

