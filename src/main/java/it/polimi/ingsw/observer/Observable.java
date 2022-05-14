package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.Messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable class according to the Observer Pattern
 */
public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    /**
     * This method adds an observer to the list of observers
     * @param observer to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * This method removes an observer from the list of observers
     * @param observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * This method notify all the observers that there is an update
     * @param message the message to be passed to the observers.
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}