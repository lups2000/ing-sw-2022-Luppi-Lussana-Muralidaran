package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class CloudTileReply extends Message {

    //id serialization

    private List<CloudTile> cloudTiles;

    public CloudTileReply(String nickNameClient,List<CloudTile> cloudTiles){
        super(nickNameClient, MessageType.REPLY_CLOUD_TILE);
        this.cloudTiles=cloudTiles;
    }

    public List<CloudTile> getCloudTiles() {return cloudTiles;}

    @Override
    public String toString() {
        return "CloudTileReply{" +
                "nickName="+getNickName()+", "+
                "cloudTiles=" + cloudTiles +
                '}';
    }
}
