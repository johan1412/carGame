package com.upec.androidtemplate20192020;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

public class ThreadCourse implements Runnable {
    CourseActivity course;
    private ArrayList<Obstacle> obstacles;
    int width, height, cycles;


    public ThreadCourse(CourseActivity course, int width, int height) {
        this.course = course;
        this.obstacles = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.cycles = 0;
    }



    @Override
    public void run() {
        int i = 1;
        int speed;
        while (!course.isFinish()) {
            speed = course.getSPEED();
            if(cycles%30 == 0) {
                Random rand = new Random();
                int x = rand.nextInt(((width - (width / 6)) - (width / 6))) + (width / 6);
                Obstacle o = new Obstacle(new Rect(x, 0, x + 80, 150));
                obstacles.add(o);
            }
            for(Obstacle obs : obstacles) {
                if(obs.getY() > (height + 100)) {
                    obstacles.remove(obs);
                }
            }
            try {
                Thread.sleep(1000/(speed*10));
            } catch (InterruptedException e) {
                return;
            }
            for(Obstacle obst : obstacles) {
                obst.setY(obst.getY()+(speed*5));
            }
            update(i);
            this.cycles++;
            if(cycles%5 == 0) {
                i++;
            }
        }
    }



    private void update(final int i) {
        course.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                course.printObstacles(obstacles);
                if(cycles%5 == 0) {
                    course.setProgressBar(i);
                }
                if(i >= 100) {
                    course.setFinish();
                }
            }
        });
    }

}
