package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.SchoolBoard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.network.Messages.ServerSide.StudentToDiningRequest;

import java.util.Map;

public class StudentToDiningReply extends Message {

    //id serialization
    private PawnColor pawnColor;
    private SchoolBoard schoolBoard;

    public StudentToDiningReply(String nickNameClient,PawnColor pawnColor){
        super(nickNameClient,MessageType.REPLY_MOVE_STUD_DINING);
        this.pawnColor=pawnColor;
    }

    public PawnColor getPawnColor() {return pawnColor;}

    @Override
    public String toString() {
        return "StudentToDiningReply{" +
                "nickName="+getNickName()+", "+
                "pawnColor=" + pawnColor +
                ", schoolBoard=" + schoolBoard +
                '}';
    }
}
