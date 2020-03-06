package com.upec.androidtemplate20192020;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Obstacle {
    private Bitmap bitmap;
    private Rect rect;

    public Obstacle(Resources res, int x) {
        bitmap = BitmapFactory.decodeResource(res, R.drawable.other_car);
        int width = 110;
        int height = 220;

        this.rect = new Rect(x, 0, x+80, 80);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }


    public int getY() {
        return this.rect.top;
    }

    public int getX() {
        return this.rect.left;
    }

    public void setY(int y) {
        this.rect.top = y;
        this.rect.bottom = y + 220;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Rect getRect() {
        return this.rect;
    }
}
