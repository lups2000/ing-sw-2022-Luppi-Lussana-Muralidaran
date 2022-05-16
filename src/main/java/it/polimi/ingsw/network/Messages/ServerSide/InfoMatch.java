package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.ArrayList;

public class InfoMatch extends Message {

    private static final long serialVersionUID = 2906643764736121381L;
    private ArrayList<Player> players;
    private boolean experts;
    private int numPlayers;

    public InfoMatch(ArrayList<Player> players,boolean experts,int numPlayers){
        super("SERVER", MessageType.INFO_MATCH);
        this.players = players;
        this.experts = experts;
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {return players;}

    public boolean isExperts() {
        return experts;
    }

    @Override
    public String toString() {
        return "InfoMatch{" +
                "NickName="+getNickName()+
                "players=" + players +
                ", expertsVariant=" + experts +
                ", numPlayers=" + numPlayers +
                '}';
    }
}
