package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

import java.io.Serial;

/**
 * This message is sent to the client by the server to show him he has lost and which other player instead won the game
 */
public class Lose extends Message {

    @Serial
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
