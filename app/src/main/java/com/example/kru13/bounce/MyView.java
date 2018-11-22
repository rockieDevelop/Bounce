package com.example.kru13.sokoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kru13 on 12.10.16.
 */
public class MyView extends View{

    public static Bitmap[] BMP;

    public static int WIDTH;
    public static int HEIGHT;

    private List<Enemy> enemies = new ArrayList<Enemy>();
    private Player player;

    private int level[] = {
            12,12,1,10,7,7,         //lx, ly, playerX, playerY, gateX, gateY
            2,10,1,3,1,             //number of enemies, enemy1_X, enemy1_Y, enemy2_X, enemy2_Y, ...
            1,1,1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,1,1,0,0,0,1,
            1,0,0,0,0,0,0,1,0,0,0,1,
            1,0,0,0,0,0,2,0,1,0,0,1,
            1,0,1,1,1,1,1,0,1,1,0,1,
            1,0,0,0,0,1,1,1,1,0,0,1,
            1,0,0,2,0,0,0,0,1,0,1,1,
            1,1,1,1,1,1,1,1,1,0,0,1,
            1,0,0,0,0,0,0,0,0,1,0,1,
            1,0,0,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1,1,1
    };
    int lx = level[0];
    int ly = level[1];
    int playerX = level[2];
    int playerY = level[3];
    int gateX = level[4];
    int gateY = level[5];
    int numOfEnemies = level[6];

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        BMP = new Bitmap[9];

        BMP[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        BMP[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        BMP[2] = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle);
        BMP[3] = BitmapFactory.decodeResource(getResources(), R.drawable.gate);
        BMP[4] = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        BMP[5] = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        BMP[6] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowleft);
        BMP[7] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowright);
        BMP[8] = BitmapFactory.decodeResource(getResources(), R.drawable.arrowup);

        for(int i = 0; i < numOfEnemies; i++){
            enemies.add(new Enemy(level[7+(i*2)],level[8+(i*2)], 3));
        }
        player = new Player(playerX, playerY, 5);

    }

    void move(float xPressed, float yPressed){
        Point p = new Point();
        getDisplay().getSize(p);
        p = new Point(p.x/2, p.y/2);
        float xSize = p.x;
        float ySize = p.y;

        //Toast.makeText(getContext(), "xDown " + xDown, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "yDown " + yDown, Toast.LENGTH_SHORT).show();

        float x = (xPressed - p.x) / p.x;
        float y = (yPressed - p.y) / p.y;
        //Toast.makeText(getContext(), "x " + x + " y " + y, Toast.LENGTH_SHORT).show();
        //move right
        if(x > 0 && x - 0 >= Math.abs(y - 0))
        {
            level[playerY*10+playerX] = 0;
            level[playerY*10+playerX+1] = 4;
            playerX++;
            invalidate();
        }
        //move left
        else if(x < 0 && 0 - x > Math.abs(y - 0))
        {
            level[playerY*10+playerX] = 0;
            level[playerY*10+playerX-1] = 4;
            playerX--;
            invalidate();
        }
        //move up
        else if(y > 0)
        {
            level[playerY*10+playerX] = 0;
            level[(playerY+1)*10+playerX] = 4;
            playerY++;
            invalidate();
        }
        //move down
        else
        {
            level[playerY*10+playerX] = 0;
            level[(playerY-1)*10+playerX] = 4;
            playerY--;
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                float xDown = event.getX();
                float yDown = event.getY();
                move(xDown, yDown);
                break;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        WIDTH = w / ly;
        HEIGHT = h / lx;
        Log.d("size", WIDTH + " w  h " + HEIGHT);

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //BitmapFactory.decodeResource(getResources(),R.drawable.player, options);
        //Log.d("size", options.outWidth + " w1  h1 " + options.outHeight);

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
            loadMap(canvas, level);
            player.render(canvas);
    }

    protected void loadMap(Canvas canvas, int level[]){
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                canvas.drawBitmap(BMP[level[(i*12 + j)+7+(numOfEnemies*2)]], null,
                        new Rect(j*WIDTH, i*HEIGHT,(j+1)*WIDTH, (i+1)*HEIGHT), null);
            }
        }
    }
}
