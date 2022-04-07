package it.polimi.ingsw.Model;

public class MotherNature {
    private int index;

    /**
     * constructor
     * the island with index=0 will always be the one with MotherNature on it at the start of the game
     */
    public MotherNature(){
        this.index=0;
    }

    public int getIndex() {
        return index;
    }

    /**
     * @param index indicates the new island's index on which MotherNature lands
     */
    public void move(int index){
        this.index = index; //controlla che è compreso tra 0 e numIslands-1
    }
}
