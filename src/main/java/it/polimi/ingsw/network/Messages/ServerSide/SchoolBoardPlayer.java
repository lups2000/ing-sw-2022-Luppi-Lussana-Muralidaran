package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.SchoolBoard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serializable;

public class SchoolBoardPlayer extends Message implements Serializable {

    private static final long serialVersionUID = 2646910379456595237L;
    private SchoolBoard schoolBoard;

    public SchoolBoardPlayer(SchoolBoard schoolBoard){
        super("SERVER", MessageType.SCHOOLBOARD);
        this.schoolBoard=schoolBoard;
    }

    public SchoolBoard getSchoolBoard() {return schoolBoard;}

    @Override
    public String toString() {
        return "SchoolBoardPlayer{" +
                "nickName="+getNickName()+", "+
                "schoolBoard=" + schoolBoard +
                '}';
    }
}
