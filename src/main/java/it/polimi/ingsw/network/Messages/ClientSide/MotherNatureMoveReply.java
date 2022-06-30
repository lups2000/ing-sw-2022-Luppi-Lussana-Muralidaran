package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen number of steps that mother nature will take
 */
public class MotherNatureMoveReply extends Message {

    @Serial
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
