package com.upec.androidtemplate20192020;

import android.content.res.Resources;
import android.graphics.Rect;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Random;

public class ThreadCourse implements Runnable {
    CourseActivity course;
    private ArrayList<Obstacle> obstacles;
    int width, height, cycles;
    private Resources res;


    public ThreadCourse(CourseActivity course, int width, int height, Resources res) {
        this.course = course;
        this.obstacles = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.cycles = 0;
        this.res = res;
    }



    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        int i = 1;
        int speed;
        while (!course.isFinish()) {
            speed = course.getSPEED();
            if ((cycles % 20 == 0) && (speed != 0)) {
                Random rand = new Random();
                int x = rand.nextInt(((width - (width / 6)) - (width / 6))) + (width / 6);
                Obstacle o = new Obstacle(res, x);
                obstacles.add(o);
            }
            for (Obstacle obs : obstacles) {
                if (obs.getY() > (height + 100)) {
                    obstacles.remove(obs);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            int posPlayer = course.getPosPlayer();
            for (Obstacle obst : obstacles) {
                obst.setY(obst.getY() + (speed*5));
                if (Rect.intersects(obst.getRect(), new Rect(posPlayer-40, height-250, posPlayer+40, height-100))) {
                    course.collision();
                    obstacles.remove(obst);
                    break;
                }
            }
            update(i);
            this.cycles++;
            if (cycles % 5 == 0) {
                i = i+speed;
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
