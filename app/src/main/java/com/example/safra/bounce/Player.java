package com.example.safra.bounce;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player extends Creature {

    private float gravity = 2;
    private final float MAX_SPEED = 30;
    private float distanceJumped = 0;

    public Player(float x, float y, float speed, MyView view){
        super(x, y, speed, view);

        myLeft = (x*MyView.WIDTH)+(MyView.WIDTH/5);
        myTop = (y*MyView.HEIGHT)+(MyView.HEIGHT/4);

        boundsX = MyView.WIDTH/10;
        boundsY = MyView.HEIGHT/10;
        boundsHeight = 2*MyView.HEIGHT/5;
        boundsWidth = 2*MyView.WIDTH/3;
    }

    @Override
    public void update() {
        move();
        if(falling){
            yMove += gravity;
            if(yMove > MAX_SPEED/2)
                yMove = MAX_SPEED/2;
        }

        else if(jumping && distanceJumped < MyView.HEIGHT*3){
            yMove -= speed;
            if(yMove > MAX_SPEED)
                yMove = MAX_SPEED;

            distanceJumped += (-yMove);
            if(distanceJumped >= MyView.HEIGHT*3){
                yMove += distanceJumped - MyView.HEIGHT*3;
            }
        }else{
            falling = true;
            jumping = false;
            yMove = 0;
        }

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

    public float getDistanceJumped() {
        return distanceJumped;
    }

    public void setDistanceJumped(float distanceJumped) {
        this.distanceJumped = distanceJumped;
    }
}
