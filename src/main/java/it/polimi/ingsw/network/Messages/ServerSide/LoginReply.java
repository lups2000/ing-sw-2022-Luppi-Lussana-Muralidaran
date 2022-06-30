package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to show him if the login procedure went ok or less
 */
public class LoginReply extends Message {

    @Serial
    private static final long serialVersionUID = 2908646346376476371L;
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
                "NickName="+getNickName()+", "+
                "isNickAccepted=" + isNickAccepted +
                ", isConnAccepted=" + isConnAccepted +
                '}';
    }
}
