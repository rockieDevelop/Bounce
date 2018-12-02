package com.example.safra.bounce;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Enemy extends Creature{
    private boolean moveHorizontally; //right left
    private boolean moveVertically; //up down

    public Enemy(int x, int y, float speed, MyView view, boolean horizontal, boolean vertical){
        super(x, y, speed, view);

        this.moveHorizontally = horizontal;
        this.moveVertically = vertical;

        boundsX = 1*MyView.WIDTH/32;
        boundsY = 1*MyView.HEIGHT/32;
        boundsWidth = 30*MyView.WIDTH/32;
        boundsHeight = 30*MyView.HEIGHT/32;
        myLeft = (x*MyView.WIDTH);
        myTop = (y*MyView.HEIGHT);
    }

    @Override
    public void update() {
        if(moveHorizontally){
            xMove = speed;
            if(xMove > 0){ //vpravo
                int tx = (int) (myLeft + xMove + boundsX + boundsWidth) / MyView.WIDTH;

                if(!collision(tx, (int)(myTop + boundsY)/MyView.HEIGHT) &&
                        !collision(tx,(int)(myTop + boundsY + boundsHeight)/MyView.HEIGHT)){
                    myLeft += xMove;
                }
                else {
                    //myLeft = ((int) (myLeft + boundsX) / MyView.WIDTH) * MyView.WIDTH;
                    myLeft = tx * MyView.WIDTH - boundsX - boundsWidth - 1;
                    speed = -speed;
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
                    speed = -speed;
                }
            }
        }
        if(moveVertically){
            yMove = speed;
            if(yMove > 0) { //dolu
                int ty = (int)(myTop + yMove + boundsY + boundsHeight) / MyView.HEIGHT;

                if (!collision((int) (myLeft + boundsX) / MyView.WIDTH, ty) &&
                        !collision((int) (myLeft + boundsX + boundsWidth) / MyView.WIDTH, ty)) {
                    myTop += yMove;
                }else{
                    //myTop = ((int) (myTop + boundsY) / MyView.HEIGHT)*MyView.HEIGHT;
                    myTop = ty * MyView.HEIGHT - boundsY - boundsHeight - 1;
                    speed = -speed;
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
                    speed = -speed;
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas) {
        Bitmap resized = Bitmap.createScaledBitmap(MyView.BMP[4], MyView.WIDTH, MyView.HEIGHT, true);
        canvas.drawBitmap(resized, myLeft, myTop, null);
        /*Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 0, 0));
        myPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(myLeft+ boundsX, myTop + boundsY,
                myLeft+boundsX+boundsWidth, myTop+boundsY+boundsHeight, myPaint);*/
    }
}
