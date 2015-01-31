package com.webs.asciipenguins.kaito.galaxians;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by usuario on 21/11/2014.
 */
public class MainThread extends Thread {

    float delta;
    private boolean running;
    private boolean gameOver;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;


    public MainThread(SurfaceHolder holder, GameView gameView) {
        this.surfaceHolder = holder;
        this.gameView = gameView;
        delta = 0;
        gameOver = false;

    }

    void setRunning(boolean b) {
        this.running = b;
    }

    @Override
    public void run() {
        Canvas canvas;

        while (running) {
//            Log.d("inThread", "running");

            if (!gameView.paused && !gameOver) {
                long startDraw = System.currentTimeMillis();

                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
//                            Log.d("inThread", "rekt");
                        if (running) gameView.Update(delta / 10000000);
                        if (running) gameView.Draw(canvas);
//                            Log.d("inThread", "rekt2");
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                long endDraw = System.currentTimeMillis();
                delta = (endDraw - startDraw) * 1000.f;

            }
            if (gameView.gameDone) gameOver = true;
            if (!gameView.paused && gameOver) {
                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gameView.drawGameOver(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

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
}