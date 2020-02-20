package com.upec.androidtemplate20192020;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Ecran extends View {
    float carPosX;
    int carSize = 100;
    int carColor;

    public Ecran(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.carPosX = getWidth()/2;
        this.carColor = Color.BLACK;
    }

    public void setCarPos(float x) {
        int width = getWidth();
        if(x < (- width/2)) {
            this.carPosX = 0;
        } else if(x > width/2){
            this.carPosX = getWidth();
        } else {
            this.carPosX = x + width/2;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth();
        float h = getHeight();
        Paint car = new Paint();
        car.setColor(this.carColor);
        canvas.drawRect(carPosX-100, h-300, carPosX+100, (float)getHeight()-100, car);
    }
}
