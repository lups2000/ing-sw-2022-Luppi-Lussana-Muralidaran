package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantCard;

/**
 * This message is used by the server and it's sent to the client
 */
public class AssistantCardRequest extends Message {

    //id serialization

    public AssistantCardRequest(){
        super("SERVER", MessageType.REQUEST_ASSISTANT_CARD);
    }

    @Override
    public String toString() {
        return "AssistantCardRequest{" +
                "nickName='" + getNickName() +
                '}';
    }
}
