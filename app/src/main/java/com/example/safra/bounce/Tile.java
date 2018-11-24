package com.example.safra.bounce;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tile {

    public static Tile[] tiles = new Tile[3];

    public static Tile empty = new emptyTile(0);
    public static Tile wall = new wallTile(1);
    public static Tile obstacle = new obstacleTile(2);

    protected Bitmap texture;
    protected final int id;

    protected int x = 0,y = 0;

    public Tile(Bitmap texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void update(){

    }

    public void render(Canvas canvas, int left, int right, int top, int bottom){
        canvas.drawBitmap(texture, null,
                new Rect(left, top, right, bottom), null);
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isDangerous(){
        return false;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
