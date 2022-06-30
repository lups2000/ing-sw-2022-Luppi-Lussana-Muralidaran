package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

import java.io.Serial;

/**
 * This message is sent to the client by the server to show him he has won the game
 */
public class Win extends Message {

    @Serial
    private static final long serialVersionUID = 2943736473776671181L;
    private Player winner;

    public Win(Player winner){
        super("SERVER", MessageType.WIN);
        this.winner=winner;
    }

    public Player getWinner() {return winner;}

    @Override
    public String toString() {
        return "Win{" +
                "nickName="+getNickName()+", "+
                "winner=" + winner.getNickname() +
                '}';
    }
}
