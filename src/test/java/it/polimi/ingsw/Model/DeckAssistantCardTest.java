package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DeckAssistantCard class is tested by DeckAssistantCardTest
 * @author Matteo Luppi
 */

class DeckAssistantCardTest {

    /**
     * Method setup measures the correct instantiation of the AssistantCardDeck
     */
    @Test
    @DisplayName("test setup")
    void setup() {
        DeckAssistantCard deckAssistantCard=new DeckAssistantCard(AssistantSeed.KING);
        assertEquals(deckAssistantCard.getSeed(),AssistantSeed.KING);
        assertEquals(deckAssistantCard.getCards().size(),10);

        int j=1;
        for(int i=0;i<=9;i++){
            assertEquals(deckAssistantCard.getCards().get(i).getValue(),i+1);
            assertEquals(deckAssistantCard.getCards().get(i).getMaxStepsMotherNature(),j);
            if((i+1)%2==0){
                j++;
            }
        }
        //double check
        int i=0;
        j=1;
        for(AssistantCard assistantCard: deckAssistantCard.getCards()){
            assertEquals(assistantCard.getValue(),i+1);
            assertEquals(assistantCard.getMaxStepsMotherNature(),j);
            if((i+1)%2==0){
                j++;
            }
            i++;
        }
    }


    @Test
    @DisplayName("Pick card present")
    void pickCardPresent() {
        DeckAssistantCard deckAssistantCard=new DeckAssistantCard(AssistantSeed.KING);
        AssistantCard assistantCard=new AssistantCard(1,1);
        assertEquals(deckAssistantCard.getCards().size(),10);
        assertTrue(deckAssistantCard.getCards().contains(assistantCard));

        //pick an existing card from the deck
        deckAssistantCard.pick(assistantCard);
        //now the card does not exist anymore in the deck
        assertEquals(deckAssistantCard.getCards().size(),9);
        assertTrue(!deckAssistantCard.getCards().contains(assistantCard));

    }

    @Test
    @DisplayName("Pick card not present")
    void pickCardNotPresent() {
        DeckAssistantCard deckAssistantCard=new DeckAssistantCard(AssistantSeed.KING);
        AssistantCard assistantCard=new AssistantCard(0,0);
        assertEquals(deckAssistantCard.getCards().size(),10);
        assertTrue(!deckAssistantCard.getCards().contains(assistantCard));

        //pick a non-existing card from the deck-->exception
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            deckAssistantCard.pick(assistantCard);
        });
        assertEquals("Assistant Card not present!", exception.getMessage());
        //the deck contains the same quantity of cards and the same cards
        assertEquals(deckAssistantCard.getCards().size(),10);
        assertTrue(!deckAssistantCard.getCards().contains(assistantCard));

    }
}