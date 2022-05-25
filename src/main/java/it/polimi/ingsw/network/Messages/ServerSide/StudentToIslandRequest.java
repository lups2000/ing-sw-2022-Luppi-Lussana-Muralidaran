package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.Island;
import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;
import java.util.Map;

public class StudentToIslandRequest extends Message {

    private static final long serialVersionUID = 2476472674141441381L;
    private List<Island> islands;
    private Map<PawnColor,Integer> studentsWaiting;


    public StudentToIslandRequest(Map<PawnColor,Integer> studentsWaiting,List<Island> islands){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_ISLAND);
        this.studentsWaiting=studentsWaiting;
        this.islands=islands;
    }

    public Map<PawnColor, Integer> getStudentsWaiting() {return studentsWaiting;}
    public List<Island> getIslands() {return islands;}

    @Override
    public String toString() {
        return "StudentToIslandRequest{" +
                "nickName="+getNickName()+", "+
                "islands=" + islands +
                ", studentsWaiting=" + studentsWaiting +
                '}';
    }
}
