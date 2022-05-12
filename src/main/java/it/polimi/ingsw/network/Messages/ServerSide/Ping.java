package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

/**
 * Just to keep the connection alive
 */
public class Ping extends Message {

    //id serialization

    public Ping(){
        super(null, MessageType.PING);
    }
}
