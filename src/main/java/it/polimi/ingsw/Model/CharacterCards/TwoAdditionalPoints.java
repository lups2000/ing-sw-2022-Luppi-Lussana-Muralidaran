package it.polimi.ingsw.Model.CharacterCards;

//EFF: in questo turno durante il calcolo dell'influenza hai 2 punti di influenza addizionali

public class TwoAdditionalPoints extends CharacterCard{

    public TwoAdditionalPoints(){
        cost = 2;
        used = false;
    }

    public void effect() {

        used();
    }
}

