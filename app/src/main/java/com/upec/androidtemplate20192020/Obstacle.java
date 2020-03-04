package com.upec.androidtemplate20192020;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;


public class Obstacle {
    private Bitmap bitmap;
    private int color;
    private Rect rect;

    public Obstacle(Resources res, int x) {
        this.color = Color.GREEN;
        bitmap = BitmapFactory.decodeResource(res, R.drawable.other_car);
        int width = 80;
        int height = 160;

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
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Rect getRect() {
        return this.rect;
    }
}
