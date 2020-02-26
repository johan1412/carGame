package com.upec.androidtemplate20192020;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Ecran extends View {
    float carPosX;
    int carColor;
    int borderColor;
    View view;

    public Ecran(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.carPosX = getWidth()/2;
        this.carColor = Color.RED;
        this.borderColor = Color.rgb(20, 20, 20);
        this.view = this;
    }


    public View getView() {
        return this.view;
    }


    public void setCarPos(float x) {
        float width = getWidth();
        if(x < (- width/4)) {
            this.carPosX = width/4 + 40;
        } else if(x > width/4){
            this.carPosX = width - (width/4) - 40;
        } else {
            this.carPosX = x + width/2;
        }
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        int w = getWidth();
        float h = getHeight();
        paint.setColor(this.borderColor);
        canvas.drawRect(0, 0, w/6, h, paint);
        canvas.drawRect(w-(w/6), 0, w, h, paint);
        paint.setColor(this.carColor);
        canvas.drawRect(carPosX-40, h-250, carPosX+40, h-100, paint);
    }
}
