package com.upec.androidtemplate20192020;

import android.graphics.Color;
import android.graphics.Rect;

public class Obstacle {
    private Rect rectangle;
    private int color;

    public Obstacle(Rect rectangle) {
        this.rectangle = rectangle;
        this.color = Color.GREEN;
    }


    public int getY() {
        return rectangle.top;
    }

    public void setY(int y) {
        rectangle.top = y;
        rectangle.bottom = y + 150;
    }

    public Rect getRectangle() {
        return this.rectangle;
    }

    public boolean collision(Player player) {
        if(rectangle.contains(player.getPlayer().left, player.getPlayer().top)
            || rectangle.contains(player.getPlayer().right, player.getPlayer().top)
            || rectangle.contains(player.getPlayer().left, player.getPlayer().bottom)
            || rectangle.contains(player.getPlayer().left, player.getPlayer().bottom)) {
            return true;
        } else {
            return false;
        }
    }
}
