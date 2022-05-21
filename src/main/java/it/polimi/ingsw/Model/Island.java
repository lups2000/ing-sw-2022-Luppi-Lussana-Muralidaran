package it.polimi.ingsw.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paolo Lussana,Matteo Luppi, Pradeeban Muralidaran
 */

public class Island implements Serializable {
    @Serial
    private static final long serialVersionUID = -681054024599101267L;
    private int index;
    private int noEntryTiles;
    private ColorTower tower;
    private int numTowers;
    private boolean motherNature;
    private Map<PawnColor,Integer> students;
    //static attribute that represents how many islands are in the game (an archipelago counts as 1 island)
    private static int numIslands = 12;

    /**
     * constructor of the initial 12 islands
     * @param index is the initial index of the island, from 0 to 11
     */
    public Island(int index){
        this.index = index;
        this.noEntryTiles = 0;
        this.tower = null;
        this.numTowers = 0;
        this.motherNature = index == 0;
        this.students = new HashMap<>();
        students.put(PawnColor.RED,0);
        students.put(PawnColor.BLUE,0);
        students.put(PawnColor.YELLOW,0);
        students.put(PawnColor.PINK,0);
        students.put(PawnColor.GREEN,0);
    }

    public int getIndex() {return index;}
    public void setIndex(int newIndex){
        if(newIndex<0 || newIndex>12){
            throw new IndexOutOfBoundsException("The index cannot be lower than 0 or greater than 12!");
        }
        else {
            this.index = newIndex;
        }
    }
    public int getNumTowers() {
        return numTowers;
    }
    public ColorTower getTower() {
        return tower;
    }
    public boolean isMotherNature(){
        return motherNature;
    }
    public Map<PawnColor, Integer> getStudents() {
        return students;
    }
    public static int getNumIslands() {
        return numIslands;
    }
    public static void setNumIslands(int numIslands) {Island.numIslands = numIslands;}
    public void setMotherNature(boolean motherNature) {this.motherNature = motherNature;}

    /**
     * @param num so it is possible both to add or to remove an EntryTile (num will respectively be +1 or -1)
     */
    public void setNoEntryTiles(int num) {
        if(num<-1 || num>1){
            throw new IndexOutOfBoundsException("The parameter cannot be lower than -1 or greater than 1!");
        }
        else{
            noEntryTiles = noEntryTiles + num;
        }
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    /**
     * method to change the tower(s) on the island(s), if tower is null it means that there were no towers at all on the island and so we set numTowers to 1
     * if tower is not null we don't add new towers, we simply replace them with another colorTower
     * @param tower is the new colorTower to build on the island(s)
     */
    public void changeTower(ColorTower tower){
        if(this.tower == null){ // no tower on the Island
            numTowers = 1;
        }
        this.tower=tower;
    }

    /**
     * method to add a student to the island
     * @param toAdd is the color of the student to add
     */
    public void addStudent(PawnColor toAdd){
        if(toAdd==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else{
            students.put(toAdd,students.get(toAdd) + 1);
        }
    }

    /**
     * method to compute a player's influence on an island
     * the method is divided in two sub-methods in order to differentiate the influence calculated by the towers and the students
     * @param player indicates which player's influence we are computing
     * @return the value of the player's influence on this island
     */
    public int computeTotalInfluence(Player player){
        if(player==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else{
            return computeTowerInfluence(player) + computeStudentsInfluence(player);
        }
    }

    public int computeTowerInfluence(Player player) {
        int influence = 0;
        if (player.getColorTower().equals(tower)) {
            influence = numTowers;
        }
        return influence;
    }

    public int computeStudentsInfluence(Player player){
        int influence = 0;
        for (PawnColor d : PawnColor.values()){
            if(player.getSchoolBoard().getProfessors().get(d)){
                influence = influence + students.get(d);
            }
        }
        return influence;
    }

    /**
     * method to compute a player's influence on an island given by the only students
     * it is invoked if the NoColorInfluence character card was activated
     * @param player indicates which player's influence we are computing
     * @param noColorInfluence is the color chosen whose students don't count in this round
     * @return the value of the player's influence on this island
     */
    public int computeNoColorInfluence(Player player,PawnColor noColorInfluence){
        int influence = 0;

        for (PawnColor d : PawnColor.values()){
            if(!(d.equals(noColorInfluence)) && player.getSchoolBoard().getProfessors().get(d)){
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
        if(toMerge==null){
            throw new NullPointerException("Parameter cannot be null!");
        }
        else{
            noEntryTiles = noEntryTiles + toMerge.noEntryTiles;
            numTowers = numTowers + toMerge.numTowers;
            if(toMerge.isMotherNature()){
                this.motherNature = true;
            }
            toMerge.students.forEach((k, v) -> this.students.merge(k, v, Integer::sum));
            numIslands--;
        }
    }
}