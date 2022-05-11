package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * This message is used by the server and it's sent to the client
 */
public class MotherNatureMoveRequest extends Message {

    //id serialization

    public MotherNatureMoveRequest(){
        super("SERVER", MessageType.REQUEST_MOVE_MOTHER_NATURE);
    }

    @Override
    public String toString() {
        return "MotherNatureMoveRequest{" +
                "nickName=" +getNickName()+
                '}';
    }
}
