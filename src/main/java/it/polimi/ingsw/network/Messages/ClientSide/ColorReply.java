package it.polimi.ingsw.network.Messages.ClientSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

public class ColorReply extends Message {

    @Serial
    private static final long serialVersionUID = 1545728444737640947L;
    private PawnColor chosenColor;

    public ColorReply(String nickName,PawnColor chosenColor){
        super(nickName, MessageType.REPLY_COLOR);
        this.chosenColor=chosenColor;
    }

    public PawnColor getChosenColor() {
        return chosenColor;
    }

    @Override
    public String toString() {
        return "CharacterCardReply{" +
                "nickName="+getNickName()+", "+
                "chosenColor=" + getChosenColor() +
                '}';
    }
}
