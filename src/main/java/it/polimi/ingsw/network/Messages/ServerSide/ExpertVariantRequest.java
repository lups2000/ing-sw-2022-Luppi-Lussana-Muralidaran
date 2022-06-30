package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to ask him to choose or not the experts variant
 */
public class ExpertVariantRequest extends Message {

    @Serial
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
