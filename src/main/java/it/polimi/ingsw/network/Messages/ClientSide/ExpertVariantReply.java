package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the client and it's sent to the server
 */
public class ExpertVariantReply extends Message {

    //id serialization
    private boolean expertVariant;

    public ExpertVariantReply(String nickNameClient,boolean expertVariant){
        super(nickNameClient, MessageType.REPLY_EXPERT_VARIANT);
        this.expertVariant=expertVariant;
    }

    public boolean isExpertVariant() {return expertVariant;}

    @Override
    public String toString() {
        return "ExpertVariantReply{" +
                "nickName="+getNickName()+", "+
                "expertVariant=" + expertVariant +
                '}';
    }
}
