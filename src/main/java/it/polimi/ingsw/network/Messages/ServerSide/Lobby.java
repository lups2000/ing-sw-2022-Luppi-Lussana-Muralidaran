package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to show him all the players currently in the lobby
 */
public class Lobby extends Message {

    @Serial
    private static final long serialVersionUID = 2746726411493891381L;
    private List<Player> playersLobby; //current players in the Lobby
    private int numMaxPlayersLobby; //max size Lobby

    public Lobby(List<Player> playersLobby,int numMaxPlayersLobby){
        super("SERVER", MessageType.LOBBY);
        this.playersLobby=playersLobby;
        this.numMaxPlayersLobby=numMaxPlayersLobby;
    }

    public int getNumMaxPlayersLobby() {return numMaxPlayersLobby;}

    public List<Player> getPlayersLobby() {return playersLobby;}

    @Override
    public String toString() {
        return "Lobby{" +
                "nickName="+ getNickName() +
                "playersLobby=" + playersLobby +
                ", numMaxPlayersLobby=" + numMaxPlayersLobby +
                '}';
    }
}
