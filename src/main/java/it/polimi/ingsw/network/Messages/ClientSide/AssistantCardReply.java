package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantCard;

/**
 * This message is used by the client and it's sent to the server
 */
public class AssistantCardReply extends Message {

    //id serialization
    private AssistantCard assistantCard;

    public AssistantCardReply(String nickNameClient,AssistantCard assistantCard){
        super(nickNameClient, MessageType.REPLY_ASSISTANT_CARD);
        this.assistantCard=assistantCard;
    }

    public AssistantCard getAssistantCard() {return assistantCard;}

    @Override
    public String toString() {
        return "AssistantCardReply{" +
                "nickName="+ getNickName()+", "+
                "assistantCard=" + assistantCard +
                '}';
    }
}
