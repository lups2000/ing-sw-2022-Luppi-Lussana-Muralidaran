package it.polimi.ingsw.Messages.ClientSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

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
