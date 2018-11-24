package com.example.safra.bounce;

import android.graphics.Bitmap;

public class wallTile extends Tile {
    public wallTile(int id) {
        super(MyView.BMP[1], id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
