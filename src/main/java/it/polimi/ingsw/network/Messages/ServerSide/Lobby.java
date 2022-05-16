package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;
import it.polimi.ingsw.Model.Player;

import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class Lobby extends Message {

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
