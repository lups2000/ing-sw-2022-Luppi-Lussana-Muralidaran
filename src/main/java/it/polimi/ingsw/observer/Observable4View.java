package it.polimi.ingsw.observer;

import it.polimi.ingsw.Model.AssistantCard;
import it.polimi.ingsw.Model.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Observable4View {
    protected final List<Observer4View> observers = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param obs the observer to be added.
     */
    public void addObserver(Observer4View obs) {
        observers.add(obs);
    }

    /**
     * Adds a list of observers.
     *
     * @param observerList the list of observers to be added.
     */
    public void addAllObservers(List<Observer4View> observerList) {
        observers.addAll(observerList);
    }

    /**
     * Removes an observer.
     *
     * @param obs the observer to be removed.
     */
    public void removeObserver(Observer4View obs) {
        observers.remove(obs);
    }

    /**
     * Removes a list of observers.
     *
     * @param observerList the list of observers to be removed.
     */
    public void removeAllObservers(List<Observer4View> observerList) {
        observers.removeAll(observerList);
    }

    /**
     * Notifies all the current observers through the lambda argument.
     *
     * @param lambda the lambda to be called on the observers.
     */
    protected void notifyObserver(Consumer<Observer4View> lambda) {
        for (Observer4View observer : observers) {
            lambda.accept(observer);
        }
    }
}
