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

    //id serialization
    private List<AssistantCard> assistantCards;

    public AssistantCardReply(String nickNameClient, List<AssistantCard> assistantCards){
        super(nickNameClient, MessageType.REPLY_ASSISTANT_CARD);
        this.assistantCards=assistantCards;
    }

    public List<AssistantCard> getAssistantCards() {return assistantCards;}

    @Override
    public String toString() {
        return "AssistantCardReply{" +
                "nickName="+getNickName()+", "+
                "assistantCards=" + assistantCards +
                '}';
    }
}
