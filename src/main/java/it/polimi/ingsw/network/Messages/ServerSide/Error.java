package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

public class Error extends Message {

    //id serialization
    private String messageError;

    public Error(String nickNameClient,String messageError){
        super(nickNameClient, MessageType.ERROR);
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
