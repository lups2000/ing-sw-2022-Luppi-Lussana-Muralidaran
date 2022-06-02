package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.Model.SchoolBoard;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.io.Serial;
import java.util.Map;

public class StudentOrStopRequest extends Message {

    @Serial
    private static final long serialVersionUID = 2634673647637517411L;
    private Map<PawnColor,Integer> students;

    public StudentOrStopRequest(Map<PawnColor,Integer> students){
        super("SERVER", MessageType.REQUEST_STUDENT_OR_STOP);
        this.students=students;
    }

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "StudentOrStopRequest{" +
                "nickName="+getNickName()+
                "students=" + getStudents() +
                '}';
    }
}
