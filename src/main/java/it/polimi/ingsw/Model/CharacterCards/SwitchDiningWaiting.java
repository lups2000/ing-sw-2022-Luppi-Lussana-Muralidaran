package it.polimi.ingsw.Model.CharacterCards;

//EFF: puoi scambiare fra loro fino a 2 studenti presenti nella tua sala e nel tuo ingresso

public class SwitchDiningWaiting extends CharacterCard{

    public SwitchDiningWaiting(){
        cost = 1;
        used = false;
    }

    public void effect() {

        used();
    }
}

