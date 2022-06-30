package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen island
 */
public class IslandReply extends Message {

    @Serial
    private static final long serialVersionUID = 1535728444737640947L;
    private int indexIsland;

    public IslandReply(String nickName,int indexIsland){
        super(nickName, MessageType.REPLY_ISLAND);
        this.indexIsland=indexIsland;
    }

    public int getIndexIsland() {
        return indexIsland;
    }

    @Override
    public String toString() {
        return "CharacterCardReply{" +
                "nickName="+getNickName()+", "+
                "indexIsland=" + getIndexIsland() +
                '}';
    }
}
