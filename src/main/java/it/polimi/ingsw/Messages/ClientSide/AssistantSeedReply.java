package it.polimi.ingsw.Messages.ClientSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;
import it.polimi.ingsw.Model.AssistantSeed;

/**
 * This message is used by the client and it' sent to the server
 */
public class AssistantSeedReply extends Message {

    //id inizialization
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
