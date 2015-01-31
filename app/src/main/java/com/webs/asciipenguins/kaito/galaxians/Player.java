package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by usuario on 21/11/2014.
 */
public class Player extends Drawable{


    public Player(Bitmap bitmap, int x, int y, int screenW, int screenH, int lifes) {
        super(bitmap, x, y, screenW, screenH, lifes);
        speed = 1000;
    }


    public void update(float delta, float touchPosition){
        animationTime += delta;
        if(death){ /* DO NOTHING */ }
        else
            y = 8*screenH/9;{
            if(touchPosition == 1){// Log.d("left", " "+speed+" "+delta);
                x -= speed * delta;
                if(x < 0) x = screenW;
            }
            else if(touchPosition == 3){// Log.d("dreta", " "+speed+" "+delta);
                x += speed * delta;
                if(x > screenW) x = 0;
            }
            else if(touchPosition == 2){//Shoot
            }
        }
    }

    public int hitted() {
        --numberOfLifes;
        if(numberOfLifes <= 0) {
            almostdead = true;
            animationTime = 0;
        }
        return numberOfLifes;
    }
}
