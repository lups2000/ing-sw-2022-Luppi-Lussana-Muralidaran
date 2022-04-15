package it.polimi.ingsw.Model.CharacterCards;

//EFF: scegli un colore di studente, in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza

public class ColorNoInfluence extends CharacterCard{

    public ColorNoInfluence(){
        cost = 3;
        used = false;
    }

    public void effect() {

        used();
    }
}

