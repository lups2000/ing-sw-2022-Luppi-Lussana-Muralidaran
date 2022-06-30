package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen student to move to the chosen island
 */
public class StudentToIslandReply extends Message {

    @Serial
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
