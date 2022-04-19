package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.*;

//PRADEE TODO

//EFF: durante questo turno, prendi il controllo dei professori anche se nella tua sala hai lo stesso numero
//  di studenti del giocatore che li controlla in quel momento

public class ControlOnProfessor extends CharacterCard{

    public ControlOnProfessor(){
        cost = 2;
        used = false;
    }

    //da rivedere
    /*
    private void allocateProfessors() throws NoPawnPresentException, TooManyPawnsPresent {
        for(PawnColor color : PawnColor.values()) {
            Player winner=Game.getInstance().getPlayers().get(0);
            Player owner = null;
            int maxStudents = 0;
            boolean draw = false;
            for (Player player : Game.getInstance().getPlayers()) {
                if (player.getSchoolBoard().getProfessors().get(color)) {
                    owner = player;
                }
                if (player.getSchoolBoard().getStudentsWaiting().get(color) > maxStudents) {
                    winner = player;
                    maxStudents = player.getSchoolBoard().getStudentsWaiting().get(color);
                }
                else if (player.getSchoolBoard().getStudentsWaiting().get(color) == maxStudents) {
                    draw = true;
                }
            }
            if (owner != null) {
                if (!draw && maxStudents != 0 && !(winner.equals(owner))) {
                    owner.getSchoolBoard().removeProfessor(color);
                    winner.getSchoolBoard().addProfessor(color);
                }
            } else {
                if (maxStudents != 0) {
                    winner.getSchoolBoard().addProfessor(color);
                }
            }
        }
    }

    @Override
    public void effect() throws NoPawnPresentException, TooManyPawnsPresent {
        allocateProfessors();
        used();
    }
     */
}
