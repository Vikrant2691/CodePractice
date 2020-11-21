package com.threading.raceconditionexample;

public class CountdownThread extends Thread {

    private Countdown threadCountdown;

    CountdownThread(Countdown countdown){
        threadCountdown=countdown;

    }

    @Override
    public void run() {
        threadCountdown.doCountdown();
    }
}
