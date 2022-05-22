package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class AssistantSeedRequest extends Message {

    private static final long serialVersionUID = 2984763953492935947L;
    private final List<AssistantSeed> assistantSeedAvailable;

    //id serialization
    public AssistantSeedRequest(List<AssistantSeed> assistantSeedAvailable){
        super("SERVER", MessageType.REQUEST_ASSISTANT_SEED);
        this.assistantSeedAvailable = assistantSeedAvailable;
    }

    public List<AssistantSeed> getAssistantSeedAvailable() {
        return assistantSeedAvailable;
    }

    @Override
    public String toString() {
        return "AssistantSeedRequest{" +
                "nickName="+getNickName()+", "+
                "assistantSeedAvailable=" + assistantSeedAvailable +
                '}';
    }
}
