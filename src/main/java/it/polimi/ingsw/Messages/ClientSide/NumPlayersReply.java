package it.polimi.ingsw.Messages.ClientSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the client and sent to the server
 */
public class NumPlayersReply extends Message {

    //id serialization
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
