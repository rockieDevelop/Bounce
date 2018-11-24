package com.example.safra.bounce;

import android.graphics.Bitmap;

public class obstacleTile extends Tile {
    public obstacleTile(int id) {
        super(MyView.BMP[2], id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isDangerous() {
        return true;
    }
}
