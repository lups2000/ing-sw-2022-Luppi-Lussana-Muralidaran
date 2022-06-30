package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.AssistantSeed;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to ask him to pick an assistant seed
 */
public class AssistantSeedRequest extends Message {

    @Serial
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
