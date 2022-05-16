package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantSeed;

/**
 * This message is used by the client and it' sent to the server
 */
public class AssistantSeedReply extends Message {

    private static final long serialVersionUID = 2906646364797413651L;
    private AssistantSeed assistantSeed;

    public AssistantSeedReply(String nickNameClient,AssistantSeed assistantSeed){
        super(nickNameClient, MessageType.REPLY_ASSISTANT_SEED);
        this.assistantSeed=assistantSeed;
    }

    public AssistantSeed getAssistantSeed() {return assistantSeed;}

    @Override
    public String toString() {
        return "AssistantSeedReply{" +
                "nickName="+getNickName()+", "+
                "assistantSeed=" + assistantSeed +
                '}';
    }
}
