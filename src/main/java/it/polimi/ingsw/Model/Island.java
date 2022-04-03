package it.polimi.ingsw.Model;

public class Island {
    private int index;
    private boolean EntryTiles;
    private ColorTower tower;
    private int numTowers;
    private int[] students;
    private static int numIslands;

    //constructor
    public Island(){
        //TODO
    }

    public int getIndex() {
        return index;
    }

    public int getNumTowers() {
        return numTowers;
    }

    public ColorTower getTower() {
        return tower;
    }

    public static int getNumIslands() {
        return numIslands;
    }

    public int[] getStudents() {
        return students;
    }

    public boolean isEntryTiles() {
        return EntryTiles;
    }
    public void addTower(){
        //TODO
    }
    public void removeTower(){
        //TODO
    }
    public void addStudent(PawnColor pawnColor){
        //TODO
    }
    public void computeInfluence(){
        //TODO
    }
    public void merge(){
        //TODO
    }
}
