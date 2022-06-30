package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.io.Serializable;

/**
 * This message is sent to the client by the server to show him a disconnection message
 */
public class Disconnection extends Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 2276472674627641171L;
    private final String nickName;
    private final String message;

    public Disconnection(String nickName,String message){
        super("SERVER", MessageType.DISCONNECTION);
        this.nickName=nickName;
        this.message=message;
    }

    @Override
    public String getNickName() {return nickName;}

    public String getMessage() {return message;}

    @Override
    public String toString() {
        return "Disconnection{" +
                "nickName='" + nickName +
                ", message='" + message +
                '}';
    }
}
