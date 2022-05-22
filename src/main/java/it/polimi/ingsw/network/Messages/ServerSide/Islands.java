package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class Islands extends Message {

    private static final long serialVersionUID = 2967263627192938646L;
    private List<Island> islands;

    public Islands(List<Island> islands){
        super("SERVER", MessageType.INFO_ISLANDS);
        this.islands=islands;
    }

    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "Islands{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                '}';
    }
}
