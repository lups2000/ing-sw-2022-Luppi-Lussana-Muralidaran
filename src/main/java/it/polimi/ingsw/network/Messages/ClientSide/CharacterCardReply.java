package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

public class CharacterCardReply extends Message {

    private static final long serialVersionUID = 1525728444737640947L;
    private CharacterCard characterCard;

    public CharacterCardReply(String nickName,CharacterCard characterCard){
        super(nickName, MessageType.REPLY_CHARACTER_CARD);
        this.characterCard=characterCard;
    }

    public CharacterCard getCharacterCard() {return characterCard;}

    @Override
    public String toString() {
        return "CharacterCardReply{" +
                "nickName="+getNickName()+", "+
                "characterCard=" + characterCard +
                '}';
    }
}
