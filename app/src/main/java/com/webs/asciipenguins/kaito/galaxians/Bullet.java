package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by usuario on 23/11/2014.
 */
public class Bullet extends Enemy {

    public boolean up;

    public Bullet(Bitmap bitmap, int x, int y, int screenW, int screenH, int enemySizeX, int enemySizeY, boolean up){
        super(bitmap, x, y, screenW, screenH, enemySizeX, enemySizeY, 0);
        Log.d("cradora bullet ", " bullet crated");
        this.speed = 4000;
        this.up = up;
    }

    public void update(float delta) {
        if(up) y += speed * delta;
        else y -= speed * delta;
    }

}
