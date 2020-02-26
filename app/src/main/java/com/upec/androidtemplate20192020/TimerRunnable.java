package com.upec.androidtemplate20192020;

import android.os.SystemClock;
import android.widget.Chronometer;

public class TimerRunnable implements Runnable {
    private Chronometer chrono;

    public TimerRunnable(Chronometer chrono) {
        this.chrono = chrono;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        start();

    }

    public void start() {
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
    }
}
