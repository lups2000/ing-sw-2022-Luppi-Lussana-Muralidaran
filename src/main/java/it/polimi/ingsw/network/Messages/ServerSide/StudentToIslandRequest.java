package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class StudentToIslandRequest extends Message {

    //id serialization
    private List<Island> islands;
    private PawnColor pawnColor;

    public StudentToIslandRequest(PawnColor pawnColor,List<Island> islands){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_ISLAND);
        this.pawnColor=pawnColor;
        this.islands=islands;
    }

    public PawnColor getPawnColor() {return pawnColor;}
    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "StudentToIslandRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                ", pawnColor=" + pawnColor +
                '}';
    }
}
