package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class CloudTileRequest extends Message {

    //id serialization
    private List<CloudTile> cloudTiles;

    public CloudTileRequest(List<CloudTile> cloudTiles){
        super("SERVER", MessageType.REQUEST_CLOUD_TILE);
        this.cloudTiles=cloudTiles;
    }

    public List<CloudTile> getCloudTiles() {return cloudTiles;}

    @Override
    public String toString() {
        return "CloudTileRequest{" +
                "nickName="+getNickName()+", "+
                "cloudTiles=" + cloudTiles +
                '}';
    }
}
