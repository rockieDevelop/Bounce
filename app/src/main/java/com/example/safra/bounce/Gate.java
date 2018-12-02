package com.example.safra.bounce;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Gate {
    private int boundsX, boundsY, boundsWidth, boundsHeight;
    private int x, y;

    public Gate(int x, int y){
        boundsX = 6*MyView.WIDTH/32;
        boundsY = 0*MyView.HEIGHT/32;
        boundsWidth = 20*MyView.WIDTH/32;
        boundsHeight = 32*MyView.HEIGHT/32;

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
        Bitmap resized = Bitmap.createScaledBitmap(MyView.BMP[3], MyView.WIDTH, MyView.HEIGHT, true);
        canvas.drawBitmap(resized, x*MyView.WIDTH, y*MyView.HEIGHT, null);
        /*Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 0, 0));
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x*MyView.WIDTH + boundsX, y*MyView.HEIGHT + boundsY,
                x*MyView.WIDTH+boundsX+boundsWidth, y*MyView.HEIGHT+boundsY+boundsHeight, myPaint);*/
    }
}
