package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.AssistantCard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class AssistantCardRequest extends Message {

    //id serialization

    private final List<AssistantCard> assistantCards;

    public AssistantCardRequest(List<AssistantCard> assistantCards){
        super("SERVER", MessageType.REQUEST_ASSISTANT_CARD);
        this.assistantCards = assistantCards;
    }

    public List<AssistantCard> getAssistantCards() {
        return assistantCards;
    }

    @Override
    public String toString() {
        return "AssistantCardRequest{" +
                "nickName='" + getNickName() +
                '}';
    }
}
