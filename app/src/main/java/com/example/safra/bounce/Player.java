package com.example.safra.bounce;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player extends Creature {

    public Player(float x, float y, float speed, MyView view){
        super(x, y, speed, view);

        myLeft = (x*MyView.WIDTH)+(MyView.WIDTH/5);
        myTop = (y*MyView.HEIGHT)+(MyView.HEIGHT/4);

        boundsY = MyView.HEIGHT/10;
        boundsHeight = 3*MyView.HEIGHT/5;
        boundsWidth = MyView.WIDTH;
    }

    @Override
    public void update() {
        move();
        //myLeft = (x*MyView.WIDTH)+(MyView.WIDTH/5);
        //myTop = (y*MyView.HEIGHT)+(MyView.HEIGHT/4);
        //myRight = ((x+1)*MyView.WIDTH)-MyView.WIDTH/5;
        //myBot = (y+1)*MyView.HEIGHT;
        //Log.d("test", "4 myLeft myTop update" + myLeft + " " + myTop);
        //Log.d("pos", myLeft+ " "+ myTop);

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(MyView.BMP[5], myLeft, myTop, null);
        //Log.d("test", "5 myLeft myTop render" + myLeft + " " + myTop);
    }
}
