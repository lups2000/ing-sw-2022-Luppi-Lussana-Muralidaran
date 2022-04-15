package it.polimi.ingsw.Model.CharacterCards;

//EFF: scegli un colore di studente, ogni giocatore (incluso te) deve rimettere nel sacchetto 3 studenti di quel colore presenti nella tua sala
//chi avesse meno di 3 studenti di quel colore rimetterà tutti quelli che ha

public class ColorToStudentBag extends CharacterCard{

    public ColorToStudentBag(){
        cost = 3;
        used = false;
    }

    public void effect() {

        used();
    }
}

