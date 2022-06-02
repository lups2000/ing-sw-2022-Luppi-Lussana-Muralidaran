package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

public class CharacterCardReply extends Message {

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
