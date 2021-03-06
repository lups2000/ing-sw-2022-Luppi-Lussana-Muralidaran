package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * Just to keep the connection alive
 */
public class Ping extends Message {

    @Serial
    private static final long serialVersionUID = 2746726476471741647L;

    public Ping(){
        super(null, MessageType.PING);
    }
}
