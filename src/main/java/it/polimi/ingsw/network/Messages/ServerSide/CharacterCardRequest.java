package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class CharacterCardRequest extends Message {

    private static final long serialVersionUID = 2374673671437385947L;
    private final List<CharacterCard> characterCards;

    public CharacterCardRequest(List<CharacterCard> characterCards) {
        super("SERVER",MessageType.REQUEST_CHARACTER_CARD);
        this.characterCards=characterCards;
    }

    public List<CharacterCard> getCharacterCards() {return characterCards;}

    @Override
    public String toString() {
        return "CharacterCardRequest{" +
                "nickName="+getNickName()+", "+
                "characterCards=" + characterCards +
                '}';
    }
}
