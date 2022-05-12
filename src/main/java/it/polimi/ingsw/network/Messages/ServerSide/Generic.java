package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class Generic extends Message {

    //id serialization

    private String message;

    public Generic(String message){
        super("SERVER", MessageType.GENERIC_MESSAGE);
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
