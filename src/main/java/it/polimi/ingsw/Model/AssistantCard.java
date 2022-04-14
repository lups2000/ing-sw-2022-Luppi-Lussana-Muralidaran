package it.polimi.ingsw.Model;


/**
 * @author Matteo Luppi
 */
public class AssistantCard {
    private int value;
    private int maxStepsMotherNature;

    /**
     * Constructor
     * @param value it is an integer value,between 1 and 10
     * @param maxStepsMotherNature it is and integer value between 1 and 5
     */
    public AssistantCard(int value,int maxStepsMotherNature){
        if(value<1 || value>10 || maxStepsMotherNature<1 || maxStepsMotherNature>5){
            throw new IllegalStateException("Error"); //ha senso lanciare una eccezione?In realta il mazzo lo costruiamo noi come vogliamo
        }
        else{
            this.value=value;
            this.maxStepsMotherNature=maxStepsMotherNature;
        }
    }
    public int getValue() {
        return value;
    }
    public int getMaxStepsMotherNature() {
        return maxStepsMotherNature;
    }

}
