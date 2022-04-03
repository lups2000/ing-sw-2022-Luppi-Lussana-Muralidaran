package it.polimi.ingsw.Model;

public class AssistantCard {
    private int value;
    private int maxStepsMotherNature;
    private AssistantStatus assistantStatus;

    /**
     * Constructor
     * @param value it is an integer value,between 1 and 10
     * @param maxStepsMotherNature it is and integer value between 1 and 5
     */
    public AssistantCard(int value,int maxStepsMotherNature){
        if(value<1 || value>10 || maxStepsMotherNature<1 || maxStepsMotherNature>5){
            System.out.println("ERRORE!!!");
            //exception
        }
        else{
            this.value=value;
            this.maxStepsMotherNature=maxStepsMotherNature;
            this.assistantStatus=AssistantStatus.AVAILABLE;
        }
    }
    public int getValue() {
        return value;
    }
    public int getMaxStepsMotherNature() {
        return maxStepsMotherNature;
    }
    public AssistantStatus getAssistantStatus() {
        return assistantStatus;
    }
}
