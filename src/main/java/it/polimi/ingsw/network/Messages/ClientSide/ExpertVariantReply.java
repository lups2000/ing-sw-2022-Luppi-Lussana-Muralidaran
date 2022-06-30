package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate if he wants to activate the experts variant or not
 */
public class ExpertVariantReply extends Message {

    @Serial
    private static final long serialVersionUID = 4673646364793891381L;
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
