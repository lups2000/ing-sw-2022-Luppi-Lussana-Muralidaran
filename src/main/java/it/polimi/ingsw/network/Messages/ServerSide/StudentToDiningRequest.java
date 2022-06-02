package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.SchoolBoard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;

public class StudentToDiningRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2634673647637517311L;
    private final SchoolBoard schoolBoard;

    public StudentToDiningRequest(SchoolBoard schoolBoard){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_DINING);
        this.schoolBoard=schoolBoard;
    }

    public SchoolBoard getSchoolBoard() {return schoolBoard;}

    @Override
    public String toString() {
        return "StudentToDiningRequest{" +
                "nickName="+getNickName()+
                "schoolBoard=" + schoolBoard +
                '}';
    }
}
