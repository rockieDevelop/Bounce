package com.example.kru13.sokoview;

import android.graphics.Canvas;

public abstract class Creature {
    protected int x, y;

    protected float speed = 3;
    protected float xMove, yMove;


    public Creature(int x, int y, float speed){
        this.x = x;
        this.y = y;
        this.speed = speed;

        xMove = 0;
        yMove = 0;
    }

    public abstract void update();

    public abstract void render(Canvas canvas);

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
