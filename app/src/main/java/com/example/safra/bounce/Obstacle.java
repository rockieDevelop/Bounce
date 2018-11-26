package com.example.safra.bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Obstacle {
    private int boundsX, boundsY, boundsWidth, boundsHeight;
    private int x, y;

    public Obstacle(int x, int y){
        //boundsX = MyView.WIDTH/32*5;
        //boundsY = MyView.HEIGHT/32*14;
        //boundsWidth = MyView.WIDTH/32*22;
        //boundsHeight = MyView.WIDTH/32*18;
        boundsX = 5*MyView.WIDTH/32;
        boundsY = 14*MyView.HEIGHT/32;
        boundsWidth = 22*MyView.WIDTH/32;
        boundsHeight = 18*MyView.HEIGHT/32;

        this.x = x;
        this.y = y;
    }

    public int getBoundsX() {
        return boundsX;
    }

    public int getBoundsY() {
        return boundsY;
    }

    public int getBoundsWidth() {
        return boundsWidth;
    }

    public int getBoundsHeight() {
        return boundsHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void render(Canvas canvas){
        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 0, 0));
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x*MyView.WIDTH + boundsX, y*MyView.HEIGHT + boundsY,
                x*MyView.WIDTH+boundsX+boundsWidth, y*MyView.HEIGHT+boundsY+boundsHeight, myPaint);
    }
}
