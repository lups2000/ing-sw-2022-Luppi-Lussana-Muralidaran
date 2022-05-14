package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.ArrayList;

public class InfoMatch extends Message {

    private ArrayList<String> playersNicknames;
    private boolean experts;
    private int numPlayers;

    public InfoMatch(ArrayList<String> playersNicknames,boolean experts,int numPlayers){
        super("SERVER", MessageType.INFO_MATCH);
        this.playersNicknames = playersNicknames;
        this.experts = experts;
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<String> getPlayersNicknames() {
        return playersNicknames;
    }

    public boolean isExperts() {
        return experts;
    }

    @Override
    public String toString() {
        return "InfoMatch{" +
                "NickName="+getNickName()+
                "playersNicknames=" + playersNicknames +
                ", expertsVariant=" + experts +
                ", numPlayers=" + numPlayers +
                '}';
    }
}
