package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class CloudTileReply extends Message {

    private static final long serialVersionUID = 2906646364567232381L;
    private int idCloudTile;

    public CloudTileReply(String nickNameClient,int idCloudTile){
        super(nickNameClient, MessageType.REPLY_CLOUD_TILE);
        this.idCloudTile=idCloudTile;
    }

    public int getIdCloudTile() {return idCloudTile;}

    @Override
    public String toString() {
        return "CloudTileReply{" +
                "nickName="+getNickName()+", "+
                "idCloudTile=" + idCloudTile +
                '}';
    }
}
