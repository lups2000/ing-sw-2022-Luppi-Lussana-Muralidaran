package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;


public class StudentToIslandReply extends Message {

    private static final long serialVersionUID = 2906687587793891381L;
    private final int islandIndex;
    private final PawnColor pawnColor;

    public StudentToIslandReply(String nickNameClient,PawnColor pawnColor,int islandIndex){
        super(nickNameClient, MessageType.REPLY_MOVE_STUD_ISLAND);
        this.pawnColor=pawnColor;
        this.islandIndex=islandIndex;
    }

    public int getIslandIndex() {return islandIndex;}
    public PawnColor getPawnColor() {return pawnColor;}

    @Override
    public String toString() {
        return "StudentToIslandReply{" +
                "nickName="+getNickName()+", "+
                "islandIndex=" + islandIndex +
                ", pawnColor=" + pawnColor +
                '}';
    }
}
