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
        this.value=value;
        this.maxStepsMotherNature=maxStepsMotherNature;
    }
    public int getValue() {
        return value;
    }
    public int getMaxStepsMotherNature() {
        return maxStepsMotherNature;
    }

    /**
     Void function which updates the number of steps Mother Nature
     can do if the MoveMoreMotherNature CharacterCard is activated
    */
    public void updateMaxStepsMotherNature(int newStepsNumber) { this.maxStepsMotherNature=newStepsNumber; }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AssistantCard)) {
            return false;
        }

        AssistantCard c = (AssistantCard) o;
        // Compare the data members and return accordingly
        return value == c.value
                && maxStepsMotherNature == c.maxStepsMotherNature;
    }

}
