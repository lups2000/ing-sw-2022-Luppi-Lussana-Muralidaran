package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class NumPlayersRequest extends Message {

    private static final long serialVersionUID = 2277254254511491381L;
    public NumPlayersRequest() {
        super("SERVER", MessageType.REQUEST_PLAYER_NUM);
    }

    @Override
    public String toString() {
        return "NumPlayersRequest{" +
                "nickName=" + getNickName() + "}";
    }
}
