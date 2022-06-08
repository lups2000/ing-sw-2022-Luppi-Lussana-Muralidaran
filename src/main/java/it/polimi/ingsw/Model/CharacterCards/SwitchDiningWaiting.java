package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.NoPawnPresentException;
import it.polimi.ingsw.Model.Exceptions.TooManyPawnsPresent;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

//EFFECT: you may exchange up to 2 students between your entrance and your dining room

public class SwitchDiningWaiting extends CharacterCard implements Serializable {

    @Serial
    private static final long serialVersionUID = 3644762764736912717L;

    public SwitchDiningWaiting(Game game){
        cost = 1;
        used = false;
        this.game = game;
        this.type = CharacterCardType.SWITCH_DINING_WAITING;
    }

    public void effect(Map<PawnColor,Integer> exWaiting, Map<PawnColor,Integer> exDining) throws TooManyPawnsPresent, NoPawnPresentException {
        for(Player player : game.getPlayers()){
            if((player.getStatus()).equals(PlayerStatus.PLAYING_ACTION)){
                for(PawnColor color : PawnColor.values()){

                    if(exWaiting.get(color) == 1){
                        player.getSchoolBoard().addStudToDining(color);
                    }
                    else if(exWaiting.get(color) == 2){
                        player.getSchoolBoard().addStudToDining(color);
                        player.getSchoolBoard().addStudToDining(color);
                    }

                    if(exDining.get(color) == 1){
                        player.getSchoolBoard().addStudToWaiting(color);
                    }
                    else if(exDining.get(color) == 2){
                        player.getSchoolBoard().addStudToWaiting(color);
                        player.getSchoolBoard().addStudToWaiting(color);
                    }
                }
            }
        }
        used();
    }

    @Override
    public String toString() {
        return "SwitchDiningWaiting ( Cost: " +cost+
                ", alreadyUsed: "+used+
                " )";
    }
}

