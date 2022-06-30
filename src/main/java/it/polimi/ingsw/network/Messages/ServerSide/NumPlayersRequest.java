package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to ask him to choose the number of players in the game
 */
public class NumPlayersRequest extends Message {

    @Serial
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
