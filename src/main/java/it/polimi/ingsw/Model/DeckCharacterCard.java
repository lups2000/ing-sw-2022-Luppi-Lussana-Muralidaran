package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;

import java.util.ArrayList;
import java.util.List;

public class DeckCharacterCard {
    List<CharacterCard> characterCards;

    public DeckCharacterCard(){
        this.characterCards=new ArrayList<>();
        //qua dobbiamo aggiungere al mazzo tutte e 12 le carte
        //da fare dopo quando abbiamo definito tutti le 12 classi
    }

    public List<CharacterCard> pickThreeCards(){
        //method to extract randomly the three character cards
        //TODO
        return null;
    }
}
