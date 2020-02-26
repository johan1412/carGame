package com.upec.androidtemplate20192020;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle {
    private Rect obstacle;
    private int color;

    public Obstacle(Rect rectangle) {
        this.obstacle = rectangle;
        this.color = Color.RED;
    }

    public Rect getObstacle() {
        return obstacle;
    }

    public boolean collision(Player player) {
        if(obstacle.contains(player.getPlayer().left, player.getPlayer().top)
            || obstacle.contains(player.getPlayer().right, player.getPlayer().top)
            || obstacle.contains(player.getPlayer().left, player.getPlayer().bottom)
            || obstacle.contains(player.getPlayer().left, player.getPlayer().bottom)) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(obstacle, paint);
    }
}
