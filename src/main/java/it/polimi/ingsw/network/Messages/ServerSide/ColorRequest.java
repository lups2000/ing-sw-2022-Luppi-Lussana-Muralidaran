package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.Map;

/**
 * This message is used by the server and it's sent to the client
 */
public class ColorRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2906457863561921381L;
    private Map<PawnColor,Integer> availableStudents;

    public ColorRequest(Map<PawnColor,Integer> availableStudents){
        super("SERVER", MessageType.REQUEST_COLOR);
        this.availableStudents = availableStudents;
    }

    public Map<PawnColor,Integer> getAvailableStudents() {
        return availableStudents;
    }

    @Override
    public String toString() {
        return "ColorRequest{" +
                "nickName="+getNickName()+
                "availableStudents="+getAvailableStudents()+
                '}';
    }
}