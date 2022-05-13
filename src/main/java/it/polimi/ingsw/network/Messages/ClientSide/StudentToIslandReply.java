package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;


public class StudentToIslandReply extends Message {

    //id serialization
    private final Island island;
    private final PawnColor pawnColor;

    public StudentToIslandReply(String nickNameClient,PawnColor pawnColor,Island island){
        super(nickNameClient, MessageType.REPLY_MOVE_STUD_ISLAND);
        this.pawnColor=pawnColor;
        this.island=island;
    }

    public Island getIsland() {return island;}
    public PawnColor getPawnColor() {return pawnColor;}

    @Override
    public String toString() {
        return "StudentToIslandReply{" +
                "nickName="+getNickName()+", "+
                "islands=" + island +
                ", pawnColor=" + pawnColor +
                '}';
    }
}
