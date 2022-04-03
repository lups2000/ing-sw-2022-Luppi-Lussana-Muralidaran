package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

public class DeckAssistantCard {
    private List<AssistantCard> cards;
    private AssistantSeed seed;
    private boolean used;

    /**
     * Constructor, creation of the Deck of Assistent Cards(10 cards in total)
     * i-->value
     * j-->maxStepsMotherNature(when i is even j is incremented by one)
     */
    public DeckAssistantCard(){
        int j=1;
        this.cards= new ArrayList<>();
        for(int i=1;i<=10;i++){
            AssistantCard assistantCard = new AssistantCard(i,j);
            cards.set(i,assistantCard);
            if(i%2==0){
                j++;
            }
        }
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
