package it.polimi.ingsw.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paolo Lussana
 */

public class Island {
    private int index;
    private int entryTiles;
    private ColorTower tower;
    private int numTowers;
    private Map<PawnColor,Integer> students;
    public static int numIslands = 12;

    /**
     * constructor of the initial 12 islands
     * @param index is the initial index of the island, from 0 to 11
     * @param drawn is the color result of the random extraction of a single student, done in method drawStudents() of StudentBag class
     *                  if the index i is =0 or =6 extracted will be =null, since they are the islands without any student at the beginning of the game
     */
    public Island(int index,PawnColor drawn){
        this.index = index;
        this.entryTiles = 0;
        this.tower = null;
        this.numTowers = 0;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);

        //in Game a ogni isola dell'arrayList chiamo il metodo drawStudents() di studentBag, tranne per i=0 e per i=6 dove passo un PawnColor null
        //devo stare attento a distribuire 10 studenti iniziali, 2 per ogni colore all'inizio
        students.put(drawn,students.get(drawn) + 1);
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

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    /**
     * @param num so it is possible both to add or to remove an EntryTile (num will respectively be +1 or -1)
     */
    public void setEntryTiles(int num) {
        entryTiles = entryTiles + num;
    }

    public int getEntryTiles() {
        return entryTiles;
    }

    /**
     * method to change the tower(s) on the island(s), if tower is null it means that there were no towers at all on the island and so we increment numTowers to 1
     * if tower is not null we don't add new towers, we simply replace them with another colorTower
     * @param t is the new colorTower to build on the island(s)
     */
    public void changeTower(ColorTower t){
        if(tower == null) numTowers = 1;
        tower = t;
    }

    /**
     * method to add a student to the island
     * @param toAdd is the color of the student to add
     */
    public void addStudent(PawnColor toAdd){
        students.put(toAdd,students.get(toAdd) + 1);
    }

    /**
     * method to compute a player's influence on an island
     * @param player indicates which player's influence we are computing
     * @return the value of the player's influence on this island
     */
    public int computeInfluence(Player player){
        int influence = 0;
        if(player.getColorTower().equals(tower)){
            influence = numTowers;
        }

        for (PawnColor d : PawnColor.values()){
            if(player.getSchoolBoard().getProfessors().get(d)){
                influence = influence + students.get(d);
            }
        }
        return influence;
    }

    /**
     * method to merge two near islands with the same ColorTower, we sum the attributes of the two islands
     * the two islands to merge can be both a single island or a group of islands, since we just sum the attributes
     * every time a merge takes place we recalculate the other islands' indexes in the Game class
     * @param toMerge is the island to merge with the current one
     */
    public void merge(Island toMerge){
        entryTiles = entryTiles + toMerge.entryTiles;
        numTowers = numTowers + toMerge.numTowers;
        toMerge.students.forEach((k, v) -> this.students.merge(k, v, Integer::sum));
        numIslands--;
    }
}