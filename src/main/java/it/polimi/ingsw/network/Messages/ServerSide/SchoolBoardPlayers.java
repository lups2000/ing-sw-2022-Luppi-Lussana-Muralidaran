package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This message is sent to the client by the server to show him all the school boards
 */
public class SchoolBoardPlayers extends Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 2646910379456595237L;
    private List<Player> players;

    public SchoolBoardPlayers(List<Player> players){
        super("SERVER", MessageType.SCHOOLBOARD);
        this.players=players;
    }

    public List<Player> getPlayers() {return players;}

    @Override
    public String toString() {
        return "SchoolBoardPlayers{" +
                "nickName="+getNickName()+", "+
                "players=" + players +
                '}';
    }
}
