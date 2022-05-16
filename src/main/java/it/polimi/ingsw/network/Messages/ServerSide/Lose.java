package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

/**
 * This message is used by the server and it's sent to the client
 */
public class Lose extends Message {

    private static final long serialVersionUID = 2906646473674636412L;
    private Player loser;

    public Lose(Player loser){
        super("SERVER", MessageType.LOSE);
        this.loser=loser;
    }

    public Player getLoser() {
        return loser;
    }

    @Override
    public String toString() {
        return "Lose{" +
                "nickName="+getNickName()+", "+
                "looser=" + loser.getNickname() +
                '}';
    }
}
