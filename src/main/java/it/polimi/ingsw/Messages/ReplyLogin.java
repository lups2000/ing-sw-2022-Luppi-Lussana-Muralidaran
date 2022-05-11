package it.polimi.ingsw.Messages;

public class ReplyLogin extends Message{

    private static final long serialVersionUID = -1423312065079102467L;
    private boolean isNickAccepted;
    private boolean isConnAccepted;


    public ReplyLogin(boolean isNickAccepted,boolean isConnAccepted){
        super("nickServer",MessageType.REPLY_LOGIN);
        this.isNickAccepted=isNickAccepted;
        this.isConnAccepted=isConnAccepted;
    }

    public boolean isConnAccepted() {
        return isConnAccepted;
    }

    public boolean isNickAccepted() {
        return isNickAccepted;
    }

    @Override
    public String toString() {
        return "ReplyLogin{" +
                "NickName="+getNickName()+
                "isNickAccepted=" + isNickAccepted +
                ", isConnAccepted=" + isConnAccepted +
                '}';
    }
}
