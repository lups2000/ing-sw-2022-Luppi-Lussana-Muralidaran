package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Luppi
 */
public class DeckAssistantCard {
    private List<AssistantCard> cards;
    private AssistantSeed seed;

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
    }

    public AssistantSeed getSeed() {
        return seed;
    }
    public List<AssistantCard> getCards() {
        return cards;
    }

    /**
     * This method tells us if a card is contained or not in the deck
     * @param assistantCard the card chosen
     * @return true/false
     */
    public boolean isCardAvailable(AssistantCard assistantCard){
        for(AssistantCard card: cards){
            if (cards.contains(assistantCard)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method removes the card from the deck
     * @param assistantCard is the card that has to be removed from the player's deck after his turn
     */
    public void pick(AssistantCard assistantCard){
        cards.remove(assistantCard);
    }

}
