package it.polimi.ingsw.network.Messages;

import java.io.Serializable;

/**
 * @author Matteo Luppi
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = 2904325636479313765L;

    private String nickName;
    private MessageType messageType;

    public Message(String nickName, MessageType messageType){
        this.nickName=nickName;
        this.messageType=messageType;
    }

    public String getNickName() {return nickName;}
    public MessageType getMessageType() {return messageType;}

    @Override
    public String toString() {
        return "Message{" +
                "nickName='" + nickName +
                ", messageType=" + messageType +
                '}';
    }
}
