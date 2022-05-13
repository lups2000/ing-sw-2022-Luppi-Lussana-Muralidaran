package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

/**
 * This message is used by the client and it's sent to the server
 */
public class MotherNatureMoveReply extends Message {

    //id serialization
    private List<Island> islands;

    public MotherNatureMoveReply(String nickNameClient, List<Island> islands){
        super(nickNameClient, MessageType.REPLY_MOVE_MOTHER_NATURE);
        this.islands=islands;
    }

    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "MotherNatureMoveReply{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                '}';
    }
}
