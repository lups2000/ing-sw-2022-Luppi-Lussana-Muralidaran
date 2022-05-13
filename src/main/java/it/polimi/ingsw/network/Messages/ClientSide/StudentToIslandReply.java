package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class StudentToIslandReply extends Message {

    //id serialization
    private List<Island> islands;
    private PawnColor pawnColor;

    public StudentToIslandReply(String nickNameClient,PawnColor pawnColor,List<Island> islands){
        super(nickNameClient, MessageType.REPLY_MOVE_STUD_ISLAND);
        this.pawnColor=pawnColor;
        this.islands=islands;
    }

    public List<Island> getIslands() {return islands;}
    public PawnColor getPawnColor() {return pawnColor;}

    @Override
    public String toString() {
        return "StudentToIslandReply{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                ", pawnColor=" + pawnColor +
                '}';
    }
}
