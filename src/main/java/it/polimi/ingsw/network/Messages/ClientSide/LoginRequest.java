package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the client and it's sent to the server
 */
public class LoginRequest extends Message {

    private static final long serialVersionUID = -6343239452500134346L; //da vedere

    public LoginRequest(String nickNameClient){
        super(nickNameClient, MessageType.REQUEST_LOGIN);
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "nickName=" +getNickName()+'}';
    }
}
