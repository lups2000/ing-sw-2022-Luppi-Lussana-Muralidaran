package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the client by the server to send him a reduced version of the model
 */
public class UpdateFX extends Message {

    @Serial
    private static final long serialVersionUID = 2813744867719462728L;
    private ReducedGame reducedGame;

    public UpdateFX(ReducedGame reducedGame){
        super("SERVER", MessageType.UPDATEFX);
        this.reducedGame=reducedGame;
    }

    public ReducedGame getReducedGame() {return reducedGame;}

    @Override
    public String toString() {
        return "UpdateFX{" +
                "nickName="+getNickName()+", "+
                "reduced="+reducedGame+
                '}';
    }
}
