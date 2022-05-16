package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;


public class StudentToDiningReply extends Message {

    private static final long serialVersionUID = 2906348941393891381L;
    private PawnColor pawnColor;

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
                '}';
    }
}
