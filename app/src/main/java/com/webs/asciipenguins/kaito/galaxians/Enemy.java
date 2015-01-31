package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by usuario on 22/11/2014.
 */
public class Enemy extends Drawable{

    static ArrayList<Bitmap> enemyExplosion;
    public boolean moveIndependently;
    float points;
    float type;

    @Override
    public void setBoomAnimation(ArrayList<Bitmap> arraylist) {
        enemyExplosion = new ArrayList<Bitmap>(arraylist);
    }

    @Override
    public ArrayList<Bitmap> getBoomAnimation() {
        return enemyExplosion;
    }

    public Enemy(Bitmap bitmap, int x, int y, int screenW, int screenH, int enemySizeX, int enemySizeY, int type) {
        super( bitmap, x, y, screenW, screenH, 1);
        moveIndependently = false;
        this.type = type;
        points = 20*type;
        speed = 500;
        sizeX = enemySizeX;
        sizeY = enemySizeY;
        Log.d("Enemycreation", "Succesfully created");
    }


    public void update(float delta, boolean left, boolean down) {
        animationTime += delta;
        if (death) {
            animationTime += delta;
        }else if(moveIndependently){
            if(y < screenH/4){
                y += speed * delta;
            }else if(y < 2*screenH/4){
                y += speed * delta;
                x += speed * delta;
            }else if(y < 3*screenH/4){
                y += speed * delta;
                x -= speed * delta *2;
            }
//            else if(y < 3.5*screenH/4){
//                y += speed * delta;
//                x += speed * delta *3;
//            }
            else{
                y += speed * delta* 2-(screenH/y);
                x += speed * delta* (screenH/y);
            }
            if (y > screenH) y = 0;
            if (x < -sizeX) x = screenW;
            if (x > screenW) x = 0;
        }
        else {
            if (left) {
                x -= speed * delta;
                if (x < -sizeX) x = screenW;
            }
            if (!left) {
                x += speed * delta;
                if (x > screenW) x = 0;
            }
//            if (down) {
//                y += sizeY;
//                if (y > screenH) y = 0;
//            }
        }
    }
}