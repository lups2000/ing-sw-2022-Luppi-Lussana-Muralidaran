package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serializable;
import java.util.List;

public class GameBoard extends Message implements Serializable {

    private static final long serialVersionUID = 2817470917497248728L;
    private List<Island> islands;
    private List<Player> players;
    private List<CloudTile> cloudTiles;

    public GameBoard(List<Island> islands,List<CloudTile> cloudTiles,List<Player> players){
        super("SERVER", MessageType.GAME_BOARD);
        this.islands=islands;
        this.players=players;
        this.cloudTiles = cloudTiles;
    }

    public List<Island> getIslands() {return islands;}

    public List<Player> getPlayers() {return players;}

    public List<CloudTile> getCloudTiles() {
        return cloudTiles;
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                ", players=" + players +
                '}';
    }
}
