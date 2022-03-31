package it.polimi.ingsw.Model;

import java.util.List;

public class DeckAssistantCard {
    private List<AssistantCard> cards;
    private AssistantSeed seed;
    private boolean used;

    //constructor
    public DeckAssistantCard(){
        //TODO
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
