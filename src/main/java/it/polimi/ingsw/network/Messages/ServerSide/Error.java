package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

public class Error extends Message {

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
