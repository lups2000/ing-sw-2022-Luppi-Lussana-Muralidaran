package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to ask him to pick a cloud tile
 */
public class CloudTileRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2625334356646364793L;
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
