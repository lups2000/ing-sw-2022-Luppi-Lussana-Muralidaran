package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serializable;
import java.util.List;

public class GameBoard extends Message implements Serializable {

    private static final long serialVersionUID = 2817470917497248728L;
    private ReducedGame reducedGame;

    public GameBoard(ReducedGame reducedGame){
        super("SERVER", MessageType.GAME_BOARD);
        this.reducedGame=reducedGame;
    }
    public ReducedGame getReducedGame() {return reducedGame;}

    @Override
    public String toString() {
        return "GameBoard{" +
                "nickName="+getNickName()+
                "reducedGame=" + reducedGame +
                '}';
    }
}
