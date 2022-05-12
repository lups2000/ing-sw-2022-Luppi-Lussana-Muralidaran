package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

/**
 * This message is used by the server and it's sent to the client
 */
public class Win extends Message {

    //id serialization
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
