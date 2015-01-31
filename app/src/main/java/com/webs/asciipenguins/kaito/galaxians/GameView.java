package com.webs.asciipenguins.kaito.galaxians;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

        import static java.lang.System.exit;

/**
 * Created by usuario on 21/11/2014.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public Player player;

    public MainThread thread;
    public Vector<Enemy> enemies;
    public Background background;
    public Vector<Bullet> bullets;
    int screenW, screenH;

    float bulletSizeX, bulletSizeY, enemySizeX, enemySizeY;
    float touchPosition, timeBetweenShoots, timeForLastShoot;
    int punctuation, killEnemyPunctuation;

    int passesInX, passesInXToGoDown, level, numberOfLevels;
    public boolean paused, enemiesLeft, enemiesDown, gameDone;
    ScorePair score;

    Bitmap lifeIcon;
    Bitmap gameOverBitmap;
    GaemActivity gaem;
    public static final String PREF = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    public GameView(Context context, GaemActivity game, int screenW, int screenH) {
        super(context);
        SoundPlayer.initSounds(context);
        SoundPlayer.playSound(getContext(), SoundPlayer.tirurirurin);
        this.gaem = game;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        score = new ScorePair("Points", 0.0f);
        this.screenH = screenH;
        this.screenW = screenW;
        touchPosition = 0;
        punctuation = 0;
        killEnemyPunctuation = 20;
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground), screenW,screenH, this);
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.peng), screenW/2,/* 89*screenH/90*/screenH-(screenW/8), screenW, screenH, 5);
        ArrayList<Bitmap> explosionBitmaps = new ArrayList<Bitmap>();
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.explosion1));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.explosion2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.explosion3));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.explosion4));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.explosion5));
        player.setBoomAnimation(explosionBitmaps);
        enemiesLeft = false;
        enemiesDown = false;
        passesInX = 0;
        numberOfLevels = 4;
        passesInXToGoDown = 3;
        bulletSizeX = screenW/30; bulletSizeY = screenH/20;
        bullets = new Vector<Bullet>();
        timeForLastShoot = 0;
        timeBetweenShoots = 0.1f;
        enemies = new Vector<Enemy>();
        level = 0;
        initializeEnemies(level);
        lifeIcon = (getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.vida), 20, 20));
        gameDone = false;
        gameOverBitmap = (getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.game_over), screenH, screenW));
        sharedpreferences = getContext().getSharedPreferences("scores", Context.MODE_PRIVATE);
    }

    void initializeBackground(int lvl){
        if(lvl == 0) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground));
        else if(lvl == 1) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground2));
        else if(lvl == 2) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground3));
        else if(lvl == 3) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground4));
        else if(lvl == 4) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground5));
        else if(lvl == 5) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground6));
        else if(lvl == 6) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground7));
        else if(lvl == 7) background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground8));
        else background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground9));
    }

    void initializeEnemies(int level){
        initializeBackground(level);
        int lvl = level % numberOfLevels;
        //Log.d("loading enemies on lvl","none");
        SoundPlayer.playSound(getContext(), SoundPlayer.gong);
        int rowsOfEnemys, colsOfEnemys, space;
        ArrayList<Bitmap> explosionBitmaps = new ArrayList<Bitmap>();
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp1));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp3));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp4));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp3));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyexp4));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.exp1enemy2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.exp2enemy2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.exp3enemy2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.exp4enemy2));
        explosionBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.exp5enemy2));

        ArrayList<Bitmap> animation1 = new ArrayList<Bitmap>();
        animation1.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy));
        animation1.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyrotatedle));
        animation1.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy));
        animation1.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemyrotatedri));

        ArrayList<Bitmap> animation2 = new ArrayList<Bitmap>();
        animation2.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2));
        animation2.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2rotatedle));
        animation2.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2));
        animation2.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2rotatedri));


        if(lvl == 0) {//Log.d("loading enemies on lvl","0");
            rowsOfEnemys = 5; colsOfEnemys = 5;
            enemySizeX = enemySizeY = screenW / (colsOfEnemys * 1.5f);
            space = (int) enemySizeX / 3;
            for (int i = 0; i < rowsOfEnemys; ++i) {
                for (int j = 0; j < colsOfEnemys; ++j) {
                    int posx = (int) (j * enemySizeX + j * space);
                    int posy = (int) (i * enemySizeY);
                    if(i < 1) {
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 2));
                        enemies.lastElement().setAnimation(animation2);
                    }
                    else{
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 1));
                        enemies.lastElement().setAnimation(animation1);
                    }
                    enemies.lastElement().setBoomAnimation(explosionBitmaps);
                }
            }
        }
        if(lvl == 1) { //Log.d("loading enemies on lvl","1");
            rowsOfEnemys = 6; colsOfEnemys = 7;
            enemySizeX = enemySizeY = screenW / (colsOfEnemys * 1.5f);
            space = (int) enemySizeX / 3;
            for (int i = 0; i < rowsOfEnemys; ++i) {
                for (int j = 0; j < colsOfEnemys; ++j) {
                    int posx = (int) (j * enemySizeX + j * space);
                    int posy = (int) (i * enemySizeY);
                    if(i < 2) {
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY,2));
                        enemies.lastElement().setAnimation(animation2);
                    }
                    else{
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 1));
                        enemies.lastElement().setAnimation(animation1);
                    }
                    enemies.lastElement().setBoomAnimation(explosionBitmaps);
                }
            }

//            Log.d("loading enemies on lvl","1done");
        }
        if(lvl == 2) {//Log.d("loading enemies on lvl","2");
            background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground3));
            rowsOfEnemys = 6; colsOfEnemys = 11;

            enemySizeX = enemySizeY = (screenW / (colsOfEnemys * 1.5f));
            space = (int) enemySizeX / 3;
            for (int i = 0; i < rowsOfEnemys; ++i) {
                int posy = (int) (i * enemySizeY);
                int posx = 0;
                for(int e = 0; e < i; ++e){
                    posx += enemySizeX;
                    posx += space;
                }
                for (int j = 0; j < colsOfEnemys-2*i; ++j) {
                    if(i < 1) {
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY,2));
                        enemies.lastElement().setAnimation(animation2);
                    }
                    else{
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 1));
                        enemies.lastElement().setAnimation(animation1);
                    }
                    enemies.lastElement().setBoomAnimation(explosionBitmaps);
                    posx += enemySizeX;
                    posx += space;
                }
            }
        }
        if(lvl == 3) {//Log.d("loading enemies on lvl","3");
            background.SetBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paperbackground3));
            rowsOfEnemys = 7; colsOfEnemys = 7;

            enemySizeX = enemySizeY = (screenW / (colsOfEnemys * 1.5f));
            space = (int) enemySizeX / 3;
            for (int i = 0; i < rowsOfEnemys/2; ++i) {
                int posy = (int) (i * enemySizeY);
                int posx = 0;
                for(int e = 0; e < i; ++e){
                    posx += enemySizeX;
                    posx += space;
                }
                for (int j = 0; j < colsOfEnemys-2*i; ++j) {
                    if(i < 2) {
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy2), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY,2));
                        enemies.lastElement().setAnimation(animation2);
                    }
                    else{
                        enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 1));
                        enemies.lastElement().setAnimation(animation1);
                    }
                    enemies.lastElement().setBoomAnimation(explosionBitmaps);
                    posx += enemySizeX;
                    posx += space;
                }
            }
            for (int i = rowsOfEnemys/2; i < rowsOfEnemys; ++i) {
                int posy = (int) (i * enemySizeY);
                int posx = 0;
                for(int e = rowsOfEnemys/2; e < i; ++e){
                    posx += enemySizeX;
                    posx += space;
                }
                for (int j = 0; j < colsOfEnemys-2*(i-rowsOfEnemys/2); ++j) {
                    enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), posx, posy+40, screenW, screenH, (int) enemySizeX, (int) enemySizeY, 1));
                    enemies.lastElement().setAnimation(animation1);
                    enemies.lastElement().setBoomAnimation(explosionBitmaps);
                    posx += enemySizeX;
                    posx += space;
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        if(gameDone && !paused){
            boolean retry = true;
            thread.setRunning(false);
            while(retry){
                try{
                    thread.join();
                    retry = false;
                }catch (InterruptedException e){ }
                exit(0);
            }
        }
        if(event.getX() > (2*screenW/3) && event.getY() > (3*screenH/4) ) {
            touchPosition = 3;
        }else {
            if (event.getX() < (screenW / 3) && event.getY() > (3*screenH/4)) {
                touchPosition = 1;
            } else {
                if(event.getY() < (3*screenH/4) && event.getActionMasked() == event.ACTION_DOWN) touchPosition = 2;
                else touchPosition = 4;
            }
        }
        return true;
    }

    void Draw(Canvas canvas){
//        Log.d("GameView", "Drawing");
        if(!paused && !gameDone) {
            if (canvas != null) {
                background.Draw(canvas);
                player.draw(canvas);
                for(int i = 0; i < enemies.size(); ++i){
                    enemies.get(i).draw(canvas);
                }
                for(int i = 0; i < bullets.size(); ++i){
                    bullets.get(i).draw(canvas);
                }
            }
        }
        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        paint.setTextSize(20);
        canvas.drawText("Score: " + punctuation, 0, 20, paint);


        String score;
        score = new String();

        if (sharedpreferences.contains("name")){
            score = sharedpreferences.getString("name", "");
        }

        if (Integer.parseInt(score) < punctuation) {
            score = ""+punctuation;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("name", score);
            editor.commit();
        }

        canvas.drawText("Max Score: " + score, 120, 20, paint);
        int space = lifeIcon.getWidth();

        canvas.drawText("Lives", 300, 20, paint);
        paint.setARGB(255, 255, 0, 0);

        for(int i = 0; i < player.numberOfLifes; ++i){
            canvas.drawBitmap(lifeIcon, space*i+350,0, null);
        }

        if(touchPosition == 0){
            paint.setARGB(255, 255, 255, 255);
            paint.setTextSize(screenW/10);
            canvas.drawText("LEVEL " + level , screenW/4, screenH/2, paint);
        }
    }

    void Update(float delta){
        delta += delta/10 * level;
        if(player.death) { gameDone = true;}
        if(!paused && !gameDone) {
//            Log.d("GameView", "Updating");
            timeForLastShoot += delta;

            background.Update(delta);
            player.update(delta, touchPosition);

            if (touchPosition != 0 && enemies.size() > 0) {
                if (enemies.get(0).x <= 0) {
                    enemiesLeft = false;
                    ++passesInX;
                }
                if (enemies.get(enemies.size() - 1).x + enemies.get(0).sizeX >= screenW) {
                    enemiesLeft = true;
                    ++passesInX;
                }
//                if (passesInX > passesInXToGoDown) {
//                    passesInX = 0;
//                    enemiesDown = true;
//                }
                for (int i = 0; i < enemies.size(); ++i) {
                    Random rand = new Random();
                    if(rand.nextInt() % 2000 == 1) enemies.get(i).moveIndependently = true;
                    if(enemies.get(i).death) enemies.remove(i);
                    else {
                        enemies.get(i).update(delta, enemiesLeft, enemiesDown);

                        if (rand.nextInt() % 500 == 1) {
                            bullets.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.down_bullet),
                                    (int) enemies.get(i).x + ((int) enemies.get(i).sizeX / 2), (int) enemies.get(i).y + (int) enemies.get(i).sizeY,
                                    screenW, screenH, (int) bulletSizeX / 2, (int) bulletSizeY / 2, true));
                            SoundPlayer.playSound(getContext(), SoundPlayer.enemyshot);
                        }
                    }
                }
                if (enemiesDown) enemiesDown = false;
            } else {
                //        touchPosition = 0; still not touched
            }

            if (touchPosition == 2 && timeForLastShoot > timeBetweenShoots) {
                Log.d("Touchposition = 2 "," bullet crated");
                bullets.add(new Bullet(BitmapFactory.decodeResource(getResources(), R.drawable.bullet),
                        (int) player.x + ((int) player.sizeX / 2), (int) player.y, screenW, screenH, (int) bulletSizeX, (int) bulletSizeY, false));
                SoundPlayer.playSound(getContext(), 4);
                timeForLastShoot = 0;
                touchPosition = 5;

            }
            for (Bullet bullet1 : bullets) {
                bullet1.update(delta);
            }

            checkBulletCollision();

            //REMOVING UNNECCESSARY BULLETS
            for (int i = 0; i < bullets.size(); ++i) {
                if (bullets.get(i).y <= 0) bullets.remove(i);
                else if (bullets.get(i).y >= screenH) bullets.remove(i);
            }

            for(int i = 0; i < enemies.size(); ++i){
                if(!enemies.get(i).almostdead && colisionBetween(enemies.get(i), player)){
                    player.hitted();
                    enemies.get(i).almostdead = true;
                    enemies.get(i).animationTime = 0;
                    SoundPlayer.playSound(getContext(), SoundPlayer.shortExplosion);
                    SoundPlayer.playSound(getContext(), SoundPlayer.longExplosion);
                    //enemies.remove(i);
                }
            }

            if (enemies.size() == 0) {
                for (int i = 0; i < bullets.size(); ++i) {
                    bullets.remove(i);
                }
                touchPosition = 0; ++level;
//                level = level % numberOfLevels;
                initializeEnemies(level);
            }

//            Log.d("GameView", "Updated");
        }
    }

    private void checkBulletCollision(){
        //CHECK COLISIONS AND THIS SHIET
        for (int i = 0; i < bullets.size(); ++i) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); ++j) {
                Enemy enemy = enemies.get(j);
                if ((!bullet.up) && colisionBetween(bullet, enemy)) {
                    punctuation += enemies.get(j).points;
                    bullets.remove(i);
                    //enemies.remove(j);
                    enemies.get(j).almostdead = true;
                    enemies.get(j).animationTime = 0;
                    SoundPlayer.playSound(getContext(), SoundPlayer.shortExplosion);
                    j = enemies.size();
                } else if (bullet.up && colisionBetween(bullet, player)) {
                    bullets.remove(i);
                    player.hitted();
                    j = enemies.size();
                    SoundPlayer.playSound(getContext(), SoundPlayer.longExplosion);
                }
            }
        }

    }

    private boolean colisionBetween(Drawable bullet, Drawable enemy){
        if(bullet.x > enemy.x + enemy.sizeX) return false;
        if(bullet.x + bullet.sizeX < enemy.x) return false;
        if(bullet.y > enemy.y + enemy.sizeY) return false;
        if(bullet.y + bullet.sizeY < enemy.y) return false;
        if(enemy.almostdead) return false;
        return true;
    }
    private boolean colisionBetween(Drawable bullet, Player player){
        if(bullet.x > player.x + player.sizeX) return false;
        if(bullet.x + bullet.sizeX < player.x) return false;
        if(bullet.y > player.y + player.sizeY) return false;
        if(bullet.y + bullet.sizeY < player.y+player.sizeY/2) return false;
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        if(thread.getState() == Thread.State.NEW) {
            thread.setRunning(true);
            thread.start();
        }
        else{
            if(thread.getState() == Thread.State.TERMINATED){
                thread = new MainThread(getHolder(), this);
                thread.setRunning(true);
                thread.start();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        thread.setRunning(false);
        while(retry){
            try{
                //Log.d("DESTROYING SURFACE","penguin");
                thread.join();
                //Log.d("DESTROYING SURFACE","penguin2");
                retry = false;
            }catch (InterruptedException e){ }
            //Log.d("DESTROYING SURFACE","penguin3");
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

    public void drawGameOver(Canvas canvas){

        if(canvas != null) canvas.drawBitmap(gameOverBitmap, 0, 0, null);
        gaem.changeToExitButton();
        saveScore();

    }

    public void saveScore(){
//        SharedPreferences sharedpreferences;
//        sharedpreferences = getContext().getSharedPreferences("scores", Context.MODE_PRIVATE);
        String score;
        score = new String();

        if (sharedpreferences.contains("name")){
            score = sharedpreferences.getString("name", "");
        }
        if (Integer.parseInt(score) > punctuation) punctuation = Integer.parseInt(score);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name", ""+punctuation);
        editor.commit();
    }

}
