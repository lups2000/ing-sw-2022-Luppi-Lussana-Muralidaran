package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class ExpertVariantRequest extends Message {

    private static final long serialVersionUID = 2906657863561921381L;
    public ExpertVariantRequest(){

        super("SERVER", MessageType.REQUEST_EXPERT_VARIANT);
    }

    @Override
    public String toString() {
        return "ExpertVariantRequest{" +
                "nickName="+getNickName()+
                '}';
    }
}
