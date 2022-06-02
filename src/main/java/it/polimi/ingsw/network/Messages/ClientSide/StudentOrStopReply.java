package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

public class StudentOrStopReply extends Message {

    @Serial
    private static final long serialVersionUID = 2906348941393881381L;
    private final PawnColor pawnColor;
    private final boolean stop;

    public StudentOrStopReply(String nickNameClient,PawnColor pawnColor,boolean stop){
        super(nickNameClient, MessageType.REPLY_STUDENT_OR_STOP);
        this.pawnColor=pawnColor;
        this.stop = stop;
    }

    public PawnColor getPawnColor() {return pawnColor;}

    public boolean isStop() {
        return stop;
    }

    @Override
    public String toString() {
        return "StudentOrStopReply{" +
                "nickName="+getNickName()+", "+
                "pawnColor=" + getPawnColor() +
                "isStop=" + isStop() +
                '}';
    }
}
