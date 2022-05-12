package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

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
