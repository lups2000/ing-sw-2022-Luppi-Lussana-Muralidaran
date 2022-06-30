package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

/**
 * This message is sent to the server by the client to communicate the chosen character card
 */
public class CharacterCardReply extends Message {

    @Serial
    private static final long serialVersionUID = 1525728444737640947L;
    private int idCharacterCard;

    public CharacterCardReply(String nickName,int idCharacterCard){
        super(nickName, MessageType.REPLY_CHARACTER_CARD);
        this.idCharacterCard=idCharacterCard;
    }

    public int getIdCharacterCard() {return idCharacterCard;}

    @Override
    public String toString() {
        return "CharacterCardReply{" +
                "nickName="+getNickName()+", "+
                "idCharacterCard=" + idCharacterCard +
                '}';
    }
}
