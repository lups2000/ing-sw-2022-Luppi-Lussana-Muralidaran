package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class IslandRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2976657863561921381L;
    private List<Island> islands;

    public IslandRequest(List<Island> islands){
        super("SERVER", MessageType.REQUEST_ISLAND);
        this.islands = islands;
    }

    public List<Island> getIslands() {
        return islands;
    }

    @Override
    public String toString() {
        return "IslandRequest{" +
                "nickName="+getNickName()+
                "islands="+getIslands()+
                '}';
    }
}