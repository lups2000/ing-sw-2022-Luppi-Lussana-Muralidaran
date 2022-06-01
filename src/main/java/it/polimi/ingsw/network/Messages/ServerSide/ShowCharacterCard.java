package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.Map;

public class ShowCharacterCard extends Message {
    @Serial
    private static final long serialVersionUID = 2967263627191938646L;
    private Map<PawnColor,Integer> students;

    public ShowCharacterCard(Map<PawnColor,Integer> students){
        super("SERVER", MessageType.SHOW_CHARACTER_CARD);
        this.students=students;
    }

    public Map<PawnColor,Integer> getStudents() {return students;}

    @Override
    public String toString() {
        return "ShowCharacterCard{" +
                "nickName="+getNickName()+", "+
                "students=" + getStudents() +
                '}';
    }
}
