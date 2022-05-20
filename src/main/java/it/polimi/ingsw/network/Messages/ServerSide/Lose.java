package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

/**
 * This message is used by the server and it's sent to the client
 */
public class Lose extends Message {

    private static final long serialVersionUID = 2906646473674636412L;
    private Player winner;

    public Lose(Player winner){
        super("SERVER", MessageType.LOSE);
        this.winner=winner;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "Lose{" +
                "nickName="+getNickName()+", "+
                "looser=" + winner.getNickname() +
                '}';
    }
}
