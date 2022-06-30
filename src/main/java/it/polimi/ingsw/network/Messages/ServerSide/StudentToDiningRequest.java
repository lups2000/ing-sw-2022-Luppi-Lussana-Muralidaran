package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.SchoolBoard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to ask him to pick a student to move to his dining room
 */
public class StudentToDiningRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2634673647637517311L;
    private final Player player;

    public StudentToDiningRequest(Player player){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_DINING);
        this.player=player;
    }

    public Player getPlayer() {return player;}

    @Override
    public String toString() {
        return "StudentToDiningRequest{" +
                "nickName="+getNickName()+
                "player=" + player +
                '}';
    }
}
