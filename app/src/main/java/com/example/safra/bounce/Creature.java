package com.example.safra.bounce;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public abstract class Creature {
    protected float x, y;
    protected float myLeft;
    protected float myTop;

    protected float speed;
    protected float xMove, yMove;

    protected int boundsX, boundsY, boundsWidth, boundsHeight;

    protected MyView view;

    public Creature(float x, float y, float speed, MyView view){
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.view = view;

        xMove = 0;
        yMove = 0;

        boundsX = boundsY = boundsWidth = boundsHeight = 0;
    }

    protected void move(){
        moveX();
        moveY();
    }

    protected void moveX(){
        if(xMove > 0){
            if(!collision((int)(myLeft + boundsX + boundsWidth)/MyView.WIDTH, (int)(myTop + boundsY)/MyView.HEIGHT) ||
                    !collision((int)(myLeft + boundsX + boundsWidth)/MyView.WIDTH,(int)(myTop + boundsY + boundsHeight)/MyView.HEIGHT)){
                myLeft += xMove;
            }
        }
        else if(xMove < 0){
            if(!collision((int)myLeft/MyView.WIDTH, (int)(myTop + boundsY)/MyView.HEIGHT) ||
                    !collision((int)myLeft/MyView.WIDTH,(int)(myTop + boundsY + boundsHeight)/MyView.HEIGHT)){
                myLeft += xMove;
            }
        }
        //Log.d("test", "3 moveX function" + myLeft);
    }

    protected void moveY(){
        myTop += yMove;
    }

    protected boolean collision(int x, int y){
        return view.getTile(x,y).isSolid();
    }

    public abstract void update();

    public abstract void render(Canvas canvas);

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return myLeft;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return myTop;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
        //Log.d("test", "1 xMove" + this.xMove);
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
