package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class NumPlayersRequest extends Message {

    //id serialization

    public NumPlayersRequest() {
        super("SERVER", MessageType.REQUEST_PLAYER_NUM);
    }

    @Override
    public String toString() {
        return "NumPlayersRequest{" +
                "nickName=" + getNickName() + "}";
    }
}
