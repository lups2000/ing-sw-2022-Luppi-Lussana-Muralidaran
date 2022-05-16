package it.polimi.ingsw.network.Messages.ServerSide;

import it.polimi.ingsw.Model.PawnColor;
import it.polimi.ingsw.network.Messages.Message;
import it.polimi.ingsw.network.Messages.MessageType;

import java.util.List;

public class StudentToDiningRequest extends Message {

    private static final long serialVersionUID = 2634673647637517311L;
    private List<PawnColor> pawnColors;

    public StudentToDiningRequest(List<PawnColor> pawnColors){
        super("SERVER", MessageType.REQUEST_MOVE_STUD_DINING);
        this.pawnColors=pawnColors;
    }

    public List<PawnColor> getPawnColors() {return pawnColors;}

    @Override
    public String toString() {
        return "StudentToDiningRequest{" +
                "nickName="+getNickName()+", "+
                "pawnColors=" + pawnColors +
                '}';
    }
}
