package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class AssistantSeedRequest extends Message {

    //id serialization
    public AssistantSeedRequest(){
        super("SERVER", MessageType.REQUEST_ASSISTANT_SEED);
    }

    @Override
    public String toString() {
        return "AssistantSeedRequest{" +
                "nickName="+getNickName()+
                '}';
    }
}
