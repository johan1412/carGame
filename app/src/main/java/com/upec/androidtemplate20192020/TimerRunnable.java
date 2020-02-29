package com.upec.androidtemplate20192020;

import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

public class TimerRunnable implements Runnable {
    private Chronometer chrono;
    private CourseActivity course;
    private Button speedB;

    public TimerRunnable(Chronometer chrono, CourseActivity course, Button speedB) {
        this.chrono = chrono;
        this.course = course;
        this.speedB = speedB;
    }

    @Override
    public void run() {
        for(int i=0; i < 4; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
        debutChrono();
    }

    public void debutChrono() {
        course.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                speedB.setEnabled(true);
            }
        });
    }
}
