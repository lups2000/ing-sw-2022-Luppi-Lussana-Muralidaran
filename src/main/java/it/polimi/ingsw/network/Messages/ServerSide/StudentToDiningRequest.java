package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

public class StudentToDiningRequest extends Message {

    // id serialization
    private PawnColor pawnColor;

    public StudentToDiningRequest(PawnColor pawnColor){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_DINING);
        this.pawnColor=pawnColor;
    }

    public PawnColor getPawnColor() {return pawnColor;}

    @Override
    public String toString() {
        return "StudentToDiningRequest{" +
                "nickName="+getNickName()+", "+
                "pawnColor=" + pawnColor +
                '}';
    }
}
