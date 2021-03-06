package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;
import java.util.Map;

/**
 * This message is sent to the client by the server to ask him to pick a student to move to an island
 */
public class StudentToIslandRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2476472674141441381L;
    private List<Island> islands;
    private Player player;


    public StudentToIslandRequest(Player player,List<Island> islands){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_ISLAND);
        this.player=player;
        this.islands=islands;
    }

    public Player getPlayer() {return player;}
    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "StudentToIslandRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                ", player=" + player +
                '}';
    }
}
