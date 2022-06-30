package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen nickname
 */
public class LoginRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2906646364793891381L;
    public LoginRequest(String nickNameClient){
        super(nickNameClient, MessageType.REQUEST_LOGIN);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "nickName=" +getNickName()+'}';
    }
}
