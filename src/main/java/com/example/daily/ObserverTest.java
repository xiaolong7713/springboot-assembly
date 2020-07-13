package com.example.daily;

import java.util.Observable;
import java.util.Observer;

public class ObserverTest {
    public static void main(String[] args) {
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(o.toString() + " : " + arg.toString());
            }
        };

        Observabler observable = new Observabler();
        observable.addObserver(observer);
        observable.setChanged();
        observable.notifyObservers(new Object());
    }


    public static class Observabler extends Observable{
        public synchronized void setChanged() {
            super.setChanged();
        }
    }

}
