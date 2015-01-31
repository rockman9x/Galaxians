package com.webs.asciipenguins.kaito.galaxians;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by usuario on 14/11/2014.
 */
public class GaemActivity extends Activity {

    private GameView game;

    public View pauseMenu;
    public View pauseButton;
    public View exitMenu;
    public View exitButton;
    RelativeLayout relativeLayoutGame;

    View.OnClickListener ContinueListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            game.paused = false;
            pauseMenu.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener ContinueListenere = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            game.paused = false;
            exitMenu.setVisibility(View.GONE);
            exitButton.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener MainMenuListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            game.saveScore();
            GaemActivity.this.finish();
        }
    };

    View.OnClickListener PauseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            game.paused = true;
            pauseButton.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener exitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            game.paused = true;
            exitButton.setVisibility(View.GONE);
            exitMenu.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaem);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenW = displayMetrics.widthPixels;
        //TODO fix this shiet
        final int screenH = displayMetrics.heightPixels-100;

        relativeLayoutGame = (RelativeLayout) findViewById(R.id.relativeLayout);
        game = new GameView(getApplicationContext(), this, screenW, screenH);
        relativeLayoutGame.addView(game);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
        float sizePauseButton = screenW/8;
        pauseButton = inflater.inflate(R.layout.activity_exit, null, false);
        pauseButton.setY(0);
        pauseButton.setX(screenW - sizePauseButton);
        relativeLayoutGame.addView(pauseButton);
        pauseButton.setOnClickListener(PauseClickListener);
        pauseButton.getLayoutParams().width = (int)sizePauseButton;
        pauseButton.getLayoutParams().height = (int)sizePauseButton;

        pauseMenu= inflater.inflate(R.layout.activity_exit, null, false);
        relativeLayoutGame.addView(pauseMenu);
        pauseMenu.setVisibility(View.GONE);

        exitButton = inflater.inflate(R.layout.activity_exit, null, false);
        exitButton.setY(0);
        exitButton.setX(screenW - sizePauseButton);
        relativeLayoutGame.addView(exitButton);
        exitButton.setOnClickListener(exitClickListener);
        exitButton.getLayoutParams().width = (int)sizePauseButton;
        exitButton.getLayoutParams().height = (int)sizePauseButton;
        exitButton.setVisibility(View.GONE);

        exitMenu= inflater.inflate(R.layout.activity_exit, null, false);
        relativeLayoutGame.addView(exitMenu);
        exitMenu.setVisibility(View.GONE);

        Button pcont, econt, etoMenu, ptoMenu;
        pcont = (Button) pauseMenu.findViewById(R.id.pauseButtonContId);
        ptoMenu = (Button) pauseMenu.findViewById(R.id.PauseButtonMenuId);
        econt = (Button) exitMenu.findViewById(R.id.pauseButtonContId);
        etoMenu = (Button) exitMenu.findViewById(R.id.PauseButtonMenuId);

        pcont.setOnClickListener(ContinueListener);
        ptoMenu.setOnClickListener(MainMenuListener);
        econt.setOnClickListener(ContinueListenere);
        etoMenu.setOnClickListener(MainMenuListener);

        SharedPreferences sharedpreferences;
        sharedpreferences = getApplicationContext().getSharedPreferences("scores", Context.MODE_PRIVATE);
        String score;
        score = new String("0");
        if (sharedpreferences.contains("name")){
            score = sharedpreferences.getString("name", "");
        }
        int punctuation = Integer.parseInt(score);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name", ""+punctuation);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.inici, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    public void OnDestroy(){
        Log.d("ON DESTROY", "DESTROYED");
        super.onDestroy();
    }

    public void changeToExitButton() {

    }
}
