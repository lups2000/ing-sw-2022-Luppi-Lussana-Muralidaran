package it.polimi.ingsw.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Luppi
 */
public class DeckAssistantCard implements Serializable {

    @Serial
    private static final long serialVersionUID = -8889919724434577025L;
    private List<AssistantCard> cards;
    private AssistantSeed seed;

    /**
     * Constructor, creation of the Deck of Assistant Cards(10 cards in total)
     * i-->value
     * j-->maxStepsMotherNature(when i is even j is incremented by one)
     * @param chosenSeed is the AssistantSeed chosen by the player at the beginning of the game
     */
    public DeckAssistantCard(AssistantSeed chosenSeed){
        int j=1;
        this.cards= new ArrayList<>();
        for(int i=1;i<=10;i++){
            AssistantCard assistantCard = new AssistantCard(i,j);
            cards.add(i-1,assistantCard);
            if(i%2==0){
                j++;
            }
        }
        this.seed = chosenSeed;
    }

    public AssistantSeed getSeed() {
        return seed;
    }
    public List<AssistantCard> getCards() {
        return this.cards;
    }


    /**
     * This method removes the card from the deck
     * @param assistantCard is the card that has to be removed from the player's deck after his turn
     */
    public AssistantCard pick(AssistantCard assistantCard) throws IllegalArgumentException,IllegalStateException{
        if(cards.size()==0){
            throw new IllegalStateException("Assistant cards finished!");
        }
        else{
            boolean found=false;
            AssistantCard picked = null;
            for(int i = 0; i<cards.size(); i++){
                if(cards.get(i).getValue()==assistantCard.getValue() && cards.get(i).getValue()==assistantCard.getMaxStepsMotherNature()){
                    picked = cards.get(i);
                    cards.remove(i);
                    found=true;
                    break;
                }
            }
            if(!found){
                throw new IllegalArgumentException("Assistant Card not present!");
            }
            return picked;
        }
    }
}
