package it.polimi.ingsw.Messages.ServerSide;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.MessageType;

/**
 * Just to keep the connection alive
 */
public class Ping extends Message {

    //id serialization

    public Ping(){
        super(null, MessageType.PING);
    }
}
