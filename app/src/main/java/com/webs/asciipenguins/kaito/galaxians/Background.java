package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import java.util.Random;

/**
 * Created by usuario on 21/11/2014.
 */
public class Background {

    Bitmap backgroundBitmap;
    int x, y;
    int screenW, screenH;
    int countBackground;
    GameView game;

    public Background(Bitmap bitmap, int scrW, int scrH, GameView gameWiew){
        this.backgroundBitmap = bitmap;
        this.x = this.y = 0;
        this.screenW = scrW;
        this.screenH = scrH;
        countBackground = screenW/backgroundBitmap.getWidth()+1;
        game = gameWiew;
    }

    public void SetBitmap(Bitmap b){
        backgroundBitmap = b;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    public void Draw(Canvas canvas){
      //  Log.d("p", " " + countBackground );
//        for(int i = 0; i < countBackground+1; ++i){
            if(canvas != null){
              //  Log.d("a", "-----------------------");
//                canvas.drawBitmap(backgroundBitmap, x, y+backgroundBitmap.getWidth()*i, null);
                BitmapShader shader;
                shader = new BitmapShader(getResizedBitmap(backgroundBitmap, screenH, screenW),
                                                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint(); paint.setAntiAlias(true); paint.setShader(shader);
                RectF rect = new RectF(0.0f, 0.0f, screenW, screenH);

                int radius = 50;
// rect contains the bounds of the shape
// radius is the radius in pixels of the rounded corners
// paint contains the shader that will texture the shape
                canvas.drawColor(0xff000000);
                canvas.drawRoundRect(rect, radius, radius, paint);
                /**///canvas.drawBitmap(getResizedBitmap(backgroundBitmap, screenH, screenW), x, y, null);

                for(int i = 0; i < 5; ++i){
                    Random rand = new Random();
                    Random rand2 = new Random();
                    float x, y;
                    x = rand.nextInt() % screenW-100;
                    y = rand2.nextInt() % screenH-100;
                    x += radius;
                    y += radius;
                    Paint newPaint = new Paint();
                    newPaint.setAntiAlias(true);
                    newPaint.setColor(0x55ffff00);
//                    canvas.drawCircle(x, y, screenW/200, newPaint);
                    canvas.drawLine(x, y, x, y+screenH/20, newPaint);
                }
//                if(Math.abs(x) > backgroundBitmap.getWidth()){
//                    x = x + backgroundBitmap.getWidth();
//                }
            }
//        }
    }

    public void Update(float delta){
        //y =(int) ((int) y - game.speed *delta);
    }
}
