package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.AssistantCard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to ask him to pick an assistant card
 */
public class AssistantCardRequest extends Message {

    @Serial
    private static final long serialVersionUID = 4763799646364791381L;
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
