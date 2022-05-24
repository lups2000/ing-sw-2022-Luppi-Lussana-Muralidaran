package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;
import java.util.Map;

public class StudentToDiningRequest extends Message {

    private static final long serialVersionUID = 2634673647637517311L;
    private Map<PawnColor,Integer> studentsWaiting;

    public StudentToDiningRequest(Map<PawnColor,Integer> studentsWaiting){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_DINING);
        this.studentsWaiting=studentsWaiting;
    }

    public Map<PawnColor, Integer> getStudentsWaiting() {return studentsWaiting;}

    @Override
    public String toString() {
        return "StudentToDiningRequest{" +
                "nickName="+getNickName()+", "+
                "studentsWaiting=" + studentsWaiting +
                '}';
    }
}
