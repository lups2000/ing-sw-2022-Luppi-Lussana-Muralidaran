package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantCard;

import java.util.List;

/**
 * This message is used by the client and it's sent to the server
 */
public class AssistantCardReply extends Message {

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
