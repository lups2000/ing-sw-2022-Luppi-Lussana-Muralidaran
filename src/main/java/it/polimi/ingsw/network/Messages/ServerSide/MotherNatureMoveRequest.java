package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

/**
 * This message is used by the server and it's sent to the client
 */
public class MotherNatureMoveRequest extends Message {

    //id serialization
    private final List<Island> islands;

    public MotherNatureMoveRequest(List<Island> islands){
        super("SERVER", MessageType.REQUEST_MOVE_MOTHER_NATURE);
        this.islands=islands;
    }

    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "MotherNatureMoveRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                '}';
    }
}
