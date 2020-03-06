package com.upec.androidtemplate20192020;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;


public class Ecran extends View {
    private int borderColor;
    private ArrayList<Obstacle> list;
    private View view;
    private Paint paint = new Paint();


    public Ecran(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.borderColor = Color.rgb(50, 50, 50);
        this.view = this;
        this.list = new ArrayList<>();
    }



    public View getView() {
        return this.view;
    }



    public void drawObs(ArrayList<Obstacle> list) {
        this.list = list;
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        paint.setColor(this.borderColor);
        canvas.drawRect(0, 0, width/6, height, paint);
        canvas.drawRect(width-(width/6), 0, width, height, paint);
        for (Obstacle o : list) {
            if(o.getY() > 0) {
                canvas.drawBitmap(o.getBitmap(), o.getX(), o.getY(), paint);
            }
        }
    }
}
