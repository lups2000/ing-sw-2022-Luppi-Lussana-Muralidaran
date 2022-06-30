package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CloudTile;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * This message is sent to the client by the server to show him all the cloud tiles
 */
public class CloudTiles extends Message {

    @Serial
    private static final long serialVersionUID = 2967268627192938646L;
    private List<CloudTile> cloudTiles;

    public CloudTiles(List<CloudTile> cloudTiles) {
        super("SERVER", MessageType.INFO_CLOUDS);
        this.cloudTiles = cloudTiles;
    }

    public List<CloudTile> getCloudTiles(){
        return cloudTiles;
    }

    @Override
    public String toString() {
        return "CloudTiles{" +
                "nickName="+getNickName()+", "+
                "cloudTiles=" + cloudTiles +
                '}';
    }
}
