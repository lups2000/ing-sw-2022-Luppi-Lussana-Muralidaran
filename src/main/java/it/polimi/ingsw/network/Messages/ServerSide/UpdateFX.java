package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class UpdateFX extends Message {

    private static final long serialVersionUID = 2817470917497248728L;
    private Game game;

    public UpdateFX(Game game){
        super("SERVER", MessageType.UPDATEFX);
        this.game=game;
    }

    public Game getGame() {return game;}

    @Override
    public String toString() {
        return "UpdateFX{" +
                "nickName="+getNickName()+", "+
                "game="+game+
                '}';
    }
}
