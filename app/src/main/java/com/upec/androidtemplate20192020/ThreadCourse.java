package com.upec.androidtemplate20192020;

import android.content.res.Resources;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;


public class ThreadCourse implements Runnable {
    private CourseActivity course;
    private final ArrayList<Obstacle> obstacles;
    private int width, height, cycles;
    private Resources res;
    private int speed;
    private boolean testMode;


    public ThreadCourse(CourseActivity course, int width, int height, Resources res, boolean testMode) {
        this.course = course;
        this.obstacles = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.cycles = 0;
        this.res = res;
        this.testMode = testMode;
    }



    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        for(int i=0; i < 4; i++) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                return;
            }
        }
        int i = 1;
        while (!course.isFinish()) {
            speed = course.getSPEED();
            if (cycles % 20 == 0) {
                Random rand1 = new Random();
                int x1 = rand1.nextInt(width / 4) + (width / 4) - 20;
                Obstacle o1 = new Obstacle(res, x1);
                Random rand2 = new Random();
                int x2 = rand2.nextInt(((width - (width / 4)) - (width / 2))) + (width / 2) + 20;
                Obstacle o2 = new Obstacle(res, x2);
                synchronized (obstacles) {
                    obstacles.add(o1);
                    obstacles.add(o2);
                }
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int posPlayer = course.getPosPlayer();
            double posPlayerY = height*0.94;
            for (Obstacle obst : obstacles) {
                Rect p = new Rect(posPlayer-55, (int)posPlayerY-220, posPlayer+55, (int)posPlayerY);
                if (Rect.intersects(obst.getRect(), p)) {
                    update(i, true, obst);
                    /*try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;*/
                }
                obst.setY(obst.getY() + 10 + (speed*30));
            }
            update(i, false, null);
            this.cycles++;
            if (cycles % 5 == 0) {
                i = i+speed;
            }
        }
    }



    private void update(final int i, final boolean collision, final Obstacle obs) {
        course.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                synchronized (obstacles) {
                    course.printObstacles(obstacles);
                    if (speed != 0) course.changeBackground();
                    if (collision) {
                        course.collision();
                        obstacles.remove(obs);
                    }
                }
                if(cycles%5 == 0) {
                    course.setProgressBar(i);
                }
                if(testMode && (i > 10)) {
                    course.setFinish();
                } else if(i > 100) {
                    course.setFinish();
                }
            }
        });
    }

}
