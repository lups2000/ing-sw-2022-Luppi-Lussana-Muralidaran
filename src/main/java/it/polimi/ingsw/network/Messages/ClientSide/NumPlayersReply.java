package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * This message is used by the client and sent to the server
 */
public class NumPlayersReply extends Message {

    private static final long serialVersionUID = 2906644546793891381L;
    private int numPlayers;

    public NumPlayersReply(String nickNameClient,int numPlayers){
        super(nickNameClient, MessageType.REPLY_PLAYER_NUM);
        this.numPlayers=numPlayers;
    }

    public int getNumPlayers() {return numPlayers;}

    @Override
    public String toString() {
        return "NumPlayersReply{" +
                "nickName=" +getNickName()+ ", "+
                "numPlayers=" + numPlayers +
                '}';
    }
}
