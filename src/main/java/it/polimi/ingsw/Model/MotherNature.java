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
     *
     * @param steps indicates the steps MotherNature does each round
     */
    public void move(int steps){
        this.index = index+steps;
    }
}
