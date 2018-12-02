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

    protected boolean falling = true;
    protected boolean jumping = false;

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
        if(xMove > 0){ //vpravo
            int tx = (int) (myLeft + xMove + boundsX + boundsWidth) / MyView.WIDTH;

            if(!collision(tx, (int)(myTop + boundsY)/MyView.HEIGHT) &&
                    !collision(tx,(int)(myTop + boundsY + boundsHeight)/MyView.HEIGHT)){
                myLeft += xMove;
            }
            else {
                //myLeft = ((int) (myLeft + boundsX) / MyView.WIDTH) * MyView.WIDTH;
                myLeft = tx * MyView.WIDTH - boundsX - boundsWidth - 1;
            }
        }
        else if(xMove < 0){ //vlevo
            int tx = (int) (myLeft + xMove + boundsX) / MyView.WIDTH;

            if(!collision(tx, (int)(myTop + boundsY)/MyView.HEIGHT) &&
                    !collision(tx,(int)(myTop + boundsY + boundsHeight)/MyView.HEIGHT)){
                myLeft += xMove;
            }
            else {
                //myLeft = ((int)(myLeft + boundsX + boundsWidth)/MyView.WIDTH) * MyView.WIDTH;
                myLeft = tx * MyView.WIDTH + MyView.WIDTH - boundsX;
            }
        }
        //Log.d("test", "3 moveX function" + myLeft);
    }

    protected void moveY(){
        if(yMove > 0) { //dolu
            int ty = (int)(myTop + yMove + boundsY + boundsHeight) / MyView.HEIGHT;

            if (!collision((int) (myLeft + boundsX) / MyView.WIDTH, ty) &&
                    !collision((int) (myLeft + boundsX + boundsWidth) / MyView.WIDTH, ty)) {
                myTop += yMove;
                falling = true;
            }else{
                //myTop = ((int) (myTop + boundsY) / MyView.HEIGHT)*MyView.HEIGHT;
                myTop = ty * MyView.HEIGHT - boundsY - boundsHeight - 1;
                falling = false;
            }
        }
        else if(yMove < 0) { //nahoru
            int ty = (int) (myTop + boundsY + yMove) / MyView.HEIGHT;

            if (!collision((int) (myLeft + boundsX) / MyView.WIDTH, ty) &&
                    !collision((int) (myLeft + boundsX + boundsWidth) / MyView.WIDTH, ty)) {
                myTop += yMove;
            }else{
                //myTop = ((int) (myTop + boundsY + boundsHeight) / MyView.HEIGHT)*MyView.HEIGHT;
                myTop = ty * MyView.HEIGHT + MyView.HEIGHT - boundsY;
                jumping = false;
                yMove = 0;
            }
        }

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

    public int getBoundsX() {return boundsX;}
    public int getBoundsY() {return boundsY;}
    public int getBoundsWidth() {return boundsWidth;}
    public int getBoundsHeight() {return boundsHeight;}
}
