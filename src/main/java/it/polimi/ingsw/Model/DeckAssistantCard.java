package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Luppi
 */
public class DeckAssistantCard {
    private List<AssistantCard> cards;
    private AssistantSeed seed;
    private boolean used;

    /**
     * Constructor, creation of the Deck of Assistent Cards(10 cards in total)
     * i-->value
     * j-->maxStepsMotherNature(when i is even j is incremented by one)
     * @param chosenSeed is the AssistantSeed chosen by the player at the beginning of the game
     */
    public DeckAssistantCard(AssistantSeed chosenSeed){
        int j=1;
        this.cards= new ArrayList<AssistantCard>();
        for(int i=1;i<=10;i++){
            AssistantCard assistantCard = new AssistantCard(i,j);
            cards.add(i,assistantCard);
            if(i%2==0){
                j++;
            }
        }
        this.seed = chosenSeed;
        this.used=false;
    }

    public AssistantSeed getSeed() {
        return seed;
    }

    public List<AssistantCard> getCards() {
        return cards;
    }

    public boolean isUsed() {
        return used;
    }
}
