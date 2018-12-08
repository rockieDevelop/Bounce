package com.example.safra.bounce;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

        //boundsX = MyView.WIDTH/12;
        //boundsY = MyView.HEIGHT/16;
        //boundsHeight = 2*MyView.HEIGHT/5;
        //boundsWidth = 2*MyView.WIDTH/3;
        boundsX = 1*MyView.WIDTH/2/25;
        boundsY = 1*MyView.HEIGHT/2/25;
        boundsHeight = 23*MyView.HEIGHT/2/25;
        boundsWidth = 23*MyView.WIDTH/2/25;
    }

    @Override
    public void update() {
        move();
        menu.mySharedEditor = menu.mySharedPref.edit();
        menu.mySharedEditor.putInt("pX",(int)(myLeft+1)/MyView.WIDTH);
        menu.mySharedEditor.putInt("pY",(int)(myTop+1)/MyView.HEIGHT);
        menu.mySharedEditor.apply();
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
        if(collisionWith()){
            if(MyView.optionSounds)
                MyView.sound.playDieSound();
            view.restart();
            xMove = 0;
            yMove = 0;
            Log.d("test", "DEAD");
        }
        if(atEnd()){
            view.victory();
            Log.d("vic", "victory");
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
        Bitmap resized = Bitmap.createScaledBitmap(MyView.BMP[5], MyView.WIDTH/2, MyView.HEIGHT/2, true);
        canvas.drawBitmap(resized, myLeft, myTop, null);
        //Log.d("test", "x" + myLeft + "y" + myTop+"bX"+boundsX+"bY"+boundsY+"bW"+boundsWidth+"bH"+boundsHeight);
        /*Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 0, 0));
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(myLeft+ boundsX, myTop + boundsY,
                myLeft+boundsX+boundsWidth, myTop+boundsY+boundsHeight, myPaint);*/
    }

    public boolean collisionWith(){
        for(Obstacle o : view.getObstacles()) {
            float px = o.getX()*MyView.WIDTH + o.getBoundsX();
            float py = o.getY()*MyView.HEIGHT + o.getBoundsY();

            if(((px >= myLeft && px <= myLeft + boundsX + boundsWidth)||
                    (px + o.getBoundsWidth() >= myLeft && px + o.getBoundsWidth() <= myLeft + boundsX + boundsWidth)) &&
                    ((py >= myTop && py <= myTop + boundsY + boundsHeight)||
                            (py + o.getBoundsHeight() >= myTop && py + o.getBoundsHeight() <= myTop + boundsY + boundsHeight))) {
                return true;
            }
        }
        for(Enemy e : view.getEnemies()) {
            float px = e.getX() + e.getBoundsX();
            float py = e.getY() + e.getBoundsY();

            if(((px >= myLeft && px <= myLeft + boundsX + boundsWidth)||
                    (px + e.getBoundsWidth() >= myLeft && px <= myLeft + boundsX + boundsWidth)) &&
                    ((py >= myTop && py <= myTop + boundsY + boundsHeight)||
                            (py + e.getBoundsHeight() >= myTop && py <= myTop + boundsY + boundsHeight))) {
                return true;
            }
        }

        return false;
    }

    public boolean atEnd(){
        Gate g = view.getGate();
        float px = g.getX()*MyView.WIDTH + g.getBoundsX();
        float py = g.getY()*MyView.HEIGHT + g.getBoundsY();

        if(((px >= myLeft && px <= myLeft + boundsX + boundsWidth)||
                (px + g.getBoundsWidth() >= myLeft && px <= myLeft + boundsX + boundsWidth)) &&
                ((py >= myTop && py <= myTop + boundsY + boundsHeight)||
                        (py + g.getBoundsHeight() >= myTop && py <= myTop + boundsY + boundsHeight))) {
            return true;
        }
        return false;
    }

    public float getDistanceJumped() {
        return distanceJumped;
    }

    public void setDistanceJumped(float distanceJumped) {
        this.distanceJumped = distanceJumped;
    }
}
