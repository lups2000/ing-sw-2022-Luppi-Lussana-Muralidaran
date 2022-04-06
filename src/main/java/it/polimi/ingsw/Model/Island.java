package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;

public class Island {
    private int index;
    private int entryTiles;
    private ColorTower tower;
    private int numTowers;
    private Map<PawnColor,Integer> students;
    private static int numIslands;

    //constructor
    public Island(int i){
        this.index = i;
        this.entryTiles = 0;
        this.tower = null;
        this.numTowers = 0;
        this.students = new HashMap<>();

        if(i == 0 || i == 6){
            //this.students = new int[] {0,0,0,0,0};
        }
        else{
            //funzione random per inserire uno studente di un colore random
        }
        //dove inizializzare l'attributo STATICO numIslands?!
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

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public void setEntryTiles() {
        entryTiles++;
    }

    public int getEntryTiles() {
        return entryTiles;
    }

    public void changeTower(ColorTower t){
        if(t == null) numTowers = 1;
        tower = t;
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
