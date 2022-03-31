package it.polimi.ingsw.Model;

public class SchoolBoard {
    private boolean[] professors;
    private int[] studentsDining;
    private int[] studentsWaiting;
    private int numTowers;
    private PawnColor pawnColor;

    //contructor
    public SchoolBoard(){
        //TODO
    }

    public boolean[] getProfessors() {
        return professors;
    }

    public int getNumTowers() {
        return numTowers;
    }

    public int[] getStudentsWaiting() {
        return studentsWaiting;
    }

    public int[] getStudentsDining() {
        return studentsDining;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }
    public void moveStudToDining(int[] studentsWaiting,int[] studentsDining){
        //TODO
    }
    public void moveStudToWaiting(int[] studentsDining,int[] studentsWaiting){
        //TODO
    }
    public void addProfessor(){
        //TODO
    }
    public void removeProfessor(){
        //TODO
    }
    public void addTower(){
        //TODO
    }
    public void removeTower(){
        //TODO
    }
    public void addStudToWaiting(){
        //TODO
    }
}
