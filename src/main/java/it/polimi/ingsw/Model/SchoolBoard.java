package it.polimi.ingsw.Model;

public class SchoolBoard {
    private boolean[] professors;
    private int[] studentsDining;
    private int[] studentsWaiting;
    private int numTowers;
    private final int numMaxTowers;
    private int numStudentsWaiting; //it depends on the number of players

    //contructor
    public SchoolBoard(int numTowers,int numStudentsWaiting){
        this.numTowers=numTowers;
        this.numMaxTowers=numTowers;
        this.professors=new boolean[5];
        this.studentsDining=new int[5];
        this.studentsWaiting=new int[5];
        this.numStudentsWaiting=numStudentsWaiting;
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

    public int getNumStudentsWaiting() {
        return numStudentsWaiting;
    }

    public void moveStudToDining(PawnColor pawnColor){
        if(studentsWaiting[pawnColor.ordinal()]==0){
            System.out.println("ERRORE!NON CI SONO STUDENTI DI QUESTO COLORE NELLA WAITING!!!");
            //piu avanti andra sostituito con un eccezione magari
        }
        else{
            studentsWaiting[pawnColor.ordinal()]--;
            studentsDining[pawnColor.ordinal()]++;
            numStudentsWaiting--;
        }
    }
    public void moveStudToIsland(PawnColor pawnColor,Island island){
        if(studentsWaiting[pawnColor.ordinal()]==0){
            System.out.println("ERRORE!NON CI SONO STUDENTI DI QUESTO COLORE NELLA WAITING!!!");
            //piu avanti andra sostituito con un eccezione magari
        }
        else{
            studentsWaiting[pawnColor.ordinal()]--;
            numStudentsWaiting--;
            island.addStudent(pawnColor);
        }
    }
    public void addProfessor(PawnColor pawnColor){
        if(professors[pawnColor.ordinal()]){
            System.out.println("ERRORE!IMPOSSIBILE RIMUOVERE PROFESSORE!!!");
            //lancerà una exception
        }
        else {
            professors[pawnColor.ordinal()]=true;
        }
    }
    public void removeProfessor(PawnColor pawnColor){
        if(!professors[pawnColor.ordinal()]){
            System.out.println("ERRORE!IMPOSSIBILE RIMUOVERE PROFESSORE!!!");
            //lancerà una exception
        }
        else {
            professors[pawnColor.ordinal()]=false;
        }
    }
    public void addTower(){
        if(numTowers>=numMaxTowers){
            System.out.println("ERRORE!IMPOSSIBILE AGGIUNGERE TORRE!!!");
            //lancerà una exception
        }
        else {
            numTowers++;
        }
    }
    public void removeTower(){
        if(numTowers<=0){
            System.out.println("ERRORE!IMPOSSIBILE RIMUOVERE TORRE!!!");
            //lancerà una exception
        }
        else {
            numTowers--;
        }
    }
    public void addStudToWaiting(PawnColor pawnColor){
        studentsWaiting[pawnColor.ordinal()]++;
        numStudentsWaiting++;
    }
}
