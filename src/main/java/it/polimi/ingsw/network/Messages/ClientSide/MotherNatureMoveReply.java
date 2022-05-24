package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the client and it's sent to the server
 */
public class MotherNatureMoveReply extends Message {

    private static final long serialVersionUID = 2906645563364723481L;
    private final int steps;

    public MotherNatureMoveReply(String nickNameClient, int steps){
        super(nickNameClient, MessageType.REPLY_MOVE_MOTHER_NATURE);
        this.steps = steps;
    }

    public int getSteps() {return steps;}

    @Override
    public String toString() {
        return "MotherNatureMoveReply{" +
                "nickName="+getNickName()+", "+
                "steps=" + steps +
                '}';
    }
}
