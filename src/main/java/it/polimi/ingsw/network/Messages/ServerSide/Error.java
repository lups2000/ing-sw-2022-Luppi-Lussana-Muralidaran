package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to show him an error message coming with a description
 */
public class Error extends Message {

    @Serial
    private static final long serialVersionUID = 2946395749574891381L;
    private String messageError;

    public Error(String messageError){
        super("SERVER", MessageType.ERROR);
        this.messageError=messageError;
    }

    public String getMessageError() {return messageError;}

    @Override
    public String toString() {
        return "Error{" +
                "nickName="+getNickName()+", "+
                "messageError='" + messageError + '\'' +
                '}';
    }
}
