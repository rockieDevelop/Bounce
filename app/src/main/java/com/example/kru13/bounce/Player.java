package com.example.kru13.sokoview;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Player extends Creature {

    public Player(int x, int y, float speed){
        super(x, y, speed);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(MyView.BMP[5], null,
                new Rect((x*MyView.WIDTH)+MyView.WIDTH/5, (y*MyView.HEIGHT)+MyView.HEIGHT/4,((x+1)*MyView.WIDTH)-MyView.WIDTH/5, (y+1)*MyView.HEIGHT), null);
    }
}
