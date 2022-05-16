package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class MotherNatureMoveRequest extends Message {

    private static final long serialVersionUID = 2904834863264243863L;
    private final List<Island> availableIslands;

    public MotherNatureMoveRequest(List<Island> islands){
        super("SERVER", MessageType.REQUEST_MOVE_MOTHER_NATURE);
        this.availableIslands=islands;
    }

    public List<Island> getAvailableIslands() {return availableIslands;}

    @Override
    public String toString() {
        return "MotherNatureMoveRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + availableIslands +
                '}';
    }
}
