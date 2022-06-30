package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to ask him to choose the number of steps that mother nature will take
 */
public class MotherNatureMoveRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2904834863264243863L;
    private final List<Island> islands;
    private final int maxSteps;

    public MotherNatureMoveRequest(List<Island> islands,int maxSteps){
        super("SERVER", MessageType.REQUEST_MOVE_MOTHER_NATURE);
        this.islands=islands;
        this.maxSteps=maxSteps;
    }

    public List<Island> getIslands() {return islands;}
    public int getMaxSteps() {return maxSteps;}

    @Override
    public String toString() {
        return "MotherNatureMoveRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +", "+
                "maxSteps="+maxSteps+
                '}';
    }
}
