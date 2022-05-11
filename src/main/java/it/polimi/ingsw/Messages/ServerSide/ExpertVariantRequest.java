package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class ExpertVariantRequest extends Message {

    //id serialization


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
