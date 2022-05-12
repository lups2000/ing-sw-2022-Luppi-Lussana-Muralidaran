package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

/**
 * This message is used by the server and it's sent to the client
 */
public class Lose extends Message {

    //id serialization
    private Player looser;

    public Lose(Player looser){
        super("SERVER", MessageType.LOSE);
        this.looser=looser;
    }

    @Override
    public String toString() {
        return "Lose{" +
                "nickName="+getNickName()+", "+
                "looser=" + looser.getNickname() +
                '}';
    }
}
