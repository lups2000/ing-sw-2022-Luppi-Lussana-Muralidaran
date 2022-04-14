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
        //le aggiungo nell'ordine in cui sono presenti nelle regole
    }

    public List<CharacterCard> pickThreeRandomCards(){
        List<CharacterCard> extracted=new ArrayList<>();
        //method to extract randomly the three character cards
        for(int i=0;i<3;i++){
            int rand=(int) (Math.random() * 12);
            switch (rand){
                case 0:
                    //extracted.add()
            }
        }
        return null;
    }
}
