package it.polimi.ingsw.network.Messages;

import java.io.Serial;
import java.io.Serializable;

/**
 * This abstract class is used for all the concrete messages sent between client and server
 */
public abstract class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 2904325636479313765L;

    private String nickName;
    private MessageType messageType;

    public Message(String nickName, MessageType messageType){
        this.nickName=nickName;
        this.messageType=messageType;
    }

    public String getNickName() {return nickName;}
    public MessageType getMessageType() {return messageType;}

    /**
     * To serialize the message in a string with all the information
     *
     * @return the whole message converted into a string
     */
    @Override
    public String toString() {
        return "Message{" +
                "nickName='" + nickName +
                ", messageType=" + messageType +
                '}';
    }
}
