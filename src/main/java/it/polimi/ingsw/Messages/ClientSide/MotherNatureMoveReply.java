package it.polimi.ingsw.Messages.ClientSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the client and it's sent to the server
 */
public class MotherNatureMoveReply extends Message {

    //id serialization
    private int islandIndex;

    public MotherNatureMoveReply(String nickNameClient,int islandIndex){
        super(nickNameClient, MessageType.REPLY_MOVE_MOTHER_NATURE);
        this.islandIndex=islandIndex;
    }

    public int getIslandIndex() {return islandIndex;}

    @Override
    public String toString() {
        return "MotherNatureMoveReply{" +
                "nickName="+getNickName()+", "+
                "islandIndex=" + islandIndex +
                '}';
    }
}
