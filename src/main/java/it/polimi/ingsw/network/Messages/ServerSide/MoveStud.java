package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serializable;

public class MoveStud extends Message implements Serializable {

    private static final long serialVersionUID = 2487586465267591381L;
    private String message;

    public MoveStud(String message){
        super("SERVER", MessageType.MOVE_STUD);
        this.message=message;
    }

    public String getMessage() {return message;}

    @Override
    public String toString() {
        return "MoveStud{" +
                "nickName="+getNickName()+", "+
                "message='" + message + '\'' +
                '}';
    }
}
