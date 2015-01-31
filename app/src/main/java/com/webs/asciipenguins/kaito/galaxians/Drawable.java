package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by usuario on 26/11/2014.
 */
public class Drawable {

    private Bitmap bitmap;
    public float x, y, speed, screenW, screenH;
    public ArrayList<Bitmap> explosion = null;
    public ArrayList<Bitmap> animation = null;
    boolean death;
    boolean almostdead;
    float animationTime;
    float totalAnimationTime = 0.1f;
    float sizeX, sizeY;
    int numberOfLifes;

    public Drawable(Bitmap bitmap, int x, int y, int screenW, int screenH, int lifes) {
        this.screenW = screenW;
        this.screenH = screenH;
        this.bitmap = bitmap;
        speed = 1000;
        death = false;
        almostdead = false;
        animationTime = 0;
        this.y = y - sizeY;
        this.x =  x - sizeX/2;
        numberOfLifes = lifes;
        sizeX = sizeY = screenW/8;
        explosion = new ArrayList<Bitmap>();
        animation = new ArrayList<Bitmap>();
    }

    public ArrayList<Bitmap> getBoomAnimation(){
        return explosion;
    }

    public ArrayList<Bitmap> getAnimation(){
        return animation;
    }

    public void setBoomAnimation(ArrayList<Bitmap> arraylist){
        explosion = new ArrayList<Bitmap>(arraylist);
    }

    public void setAnimation(ArrayList<Bitmap> arraylist){
        animation = new ArrayList<Bitmap>(arraylist);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    private Bitmap setBitmapProperties(Bitmap bitmap){
        return getResizedBitmap(bitmap, (int)sizeY, (int)sizeX);
    }

    public void draw(Canvas canvas){

        if(death){ /*DONOTHING*/}
        else if(!almostdead) { //  Log.d("DrawableDraw", "ALIVE");
            ArrayList<Bitmap> myAnimation = getAnimation();
            if (myAnimation.size() > 0){
                int index = (int) (animationTime / totalAnimationTime * myAnimation.size());
                if(index >= myAnimation.size()) { animationTime = 0; index = 0; }
//                else {
                    canvas.drawBitmap(setBitmapProperties(myAnimation.get(index)), x, y, null);
//                }
            }
            else canvas.drawBitmap(setBitmapProperties(bitmap), x, y, null);
        }
        else {
            ArrayList<Bitmap> myExplosion = getBoomAnimation();
//            Log.d("DrawableDraw", "DIEING"+myExplosion.size());
            canvas.drawBitmap(setBitmapProperties(bitmap), x, y, null);
            if (myExplosion.size() > 0){
                int index = (int) (animationTime / totalAnimationTime * myExplosion.size());
                index = index%myExplosion.size();
//              Log.d("DIEING",""+index);
                if(index == myExplosion.size()-1) death = true;
                else {//todo think about it
                    canvas.drawBitmap(setBitmapProperties(myExplosion.get(index)), x, y, null);
                }
            }
            else death = true;
        }
    }
}
