package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantCard;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen assistant card
 */
public class AssistantCardReply extends Message {

    @Serial
    private static final long serialVersionUID = 4633218024793891381L;
    private AssistantCard assistantCard;

    public AssistantCardReply(String nickNameClient, AssistantCard assistantCard){
        super(nickNameClient, MessageType.REPLY_ASSISTANT_CARD);
        this.assistantCard=assistantCard;
    }

    public AssistantCard getAssistantCard() {return assistantCard;}

    @Override
    public String toString() {
        return "AssistantCardReply{" +
                "nickName="+getNickName()+", "+
                "assistantCards=" + assistantCard +
                '}';
    }
}
