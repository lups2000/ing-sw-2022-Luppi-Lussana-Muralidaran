package it.polimi.ingsw.Messages;

public abstract class Message {

    private static final long serialVersionUID = 6589184250663958343L; //da cambiare

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
