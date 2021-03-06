package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.Messages.Message;

/**
 * Observer interface according to the Observer Pattern. It supports a generic method of update.
 */
public interface Observer {

    void update(Message message);
}
