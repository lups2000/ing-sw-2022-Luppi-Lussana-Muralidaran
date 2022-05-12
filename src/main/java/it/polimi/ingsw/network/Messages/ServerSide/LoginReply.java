package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the server and it is sent to the client
 */
public class LoginReply extends Message {

    private static final long serialVersionUID = -1423312065079102467L; //da cambiare
    private boolean isNickAccepted;
    private boolean isConnAccepted;


    public LoginReply(boolean isNickAccepted, boolean isConnAccepted){
        super("SERVER", MessageType.REPLY_LOGIN);
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
        return "LoginReply{" +
                "NickName="+getNickName()+
                "isNickAccepted=" + isNickAccepted +
                ", isConnAccepted=" + isConnAccepted +
                '}';
    }
}
