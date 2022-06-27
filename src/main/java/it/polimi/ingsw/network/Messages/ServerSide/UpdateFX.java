package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class UpdateFX extends Message {

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
