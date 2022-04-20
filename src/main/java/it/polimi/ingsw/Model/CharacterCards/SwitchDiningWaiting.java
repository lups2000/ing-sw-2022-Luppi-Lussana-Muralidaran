package it.polimi.ingsw.Model.CharacterCards;

//EFF: puoi scambiare fra loro fino a 2 studenti presenti nella tua sala e nel tuo ingresso

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.util.Map;

public class SwitchDiningWaiting extends CharacterCard{

    public SwitchDiningWaiting(Game game){
        cost = 1;
        used = false;
        this.game = game;
    }

    public void effect(Map<PawnColor,Integer> exWaiting, Map<PawnColor,Integer> exDining) throws TooManyPawnsPresent, NoPawnPresentException {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING)){
                for(PawnColor color : PawnColor.values()){
                    if(exWaiting.get(color) == 1){
                        player.getSchoolBoard().moveStudToDining(color);
                    }
                    else if(exWaiting.get(color) == 2){
                        player.getSchoolBoard().moveStudToDining(color);
                        player.getSchoolBoard().moveStudToDining(color);
                    }

                    if(exDining.get(color) == 1){
                        player.getSchoolBoard().removeStudents(color);
                        player.getSchoolBoard().addStudToWaiting(color);
                    }
                    else if(exDining.get(color) == 2){
                        player.getSchoolBoard().removeStudents(color);
                        player.getSchoolBoard().removeStudents(color);
                        player.getSchoolBoard().addStudToWaiting(color);
                        player.getSchoolBoard().addStudToWaiting(color);
                    }
                }
            }
        }
        used();
    }
}

