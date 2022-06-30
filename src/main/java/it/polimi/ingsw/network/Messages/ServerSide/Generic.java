package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to show him a textual message
 */
public class Generic extends Message {

    @Serial
    private static final long serialVersionUID = 2487384811793891381L;
    private String message;

    public Generic(String nickName,String message){
        super(nickName, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }

    public String getMessage() {return message;}

    @Override
    public String toString() {
        return "Generic{" +
                "nickName="+getNickName() + ", "+
                "message='" + message +
                '}';
    }
}
