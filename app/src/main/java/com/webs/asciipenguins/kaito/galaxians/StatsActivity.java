package com.webs.asciipenguins.kaito.galaxians;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by usuari on 14/11/2014.
 */
public class StatsActivity extends Activity  implements View.OnClickListener{

    Button theButton;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if( id == R.id.button2){
            theButton = (Button)findViewById(R.id.button3);
            theButton.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.textView2);
            tv.setText("You will lose your score if you confirm");
        }
        if( id == R.id.button3){
            SharedPreferences sharedpreferences;
            sharedpreferences = getApplicationContext().getSharedPreferences("scores", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("name", "0");
            editor.commit();
            if (sharedpreferences.contains("name")){
                TextView score;
                score = (TextView) findViewById(R.id.textView);
                score.setText(sharedpreferences.getString("name", ""));
            }
            theButton = (Button)findViewById(R.id.button3);
            theButton.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.textView2);
            tv.setText("Maximum Score:");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stats);

        theButton = (Button)findViewById(R.id.button2);
        theButton.setOnClickListener(this);

        theButton = (Button)findViewById(R.id.button3);
        theButton.setOnClickListener(this);
        theButton.setVisibility(View.GONE);

        SoundPlayer.initSounds(getApplicationContext());
        SoundPlayer.playSound(getApplicationContext(), 3);

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        TextView score;
        score = (TextView) findViewById(R.id.textView);

        if (sharedpreferences.contains("name")){
            score.setText(sharedpreferences.getString("name", ""));
        }

        score.setTextSize(50);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenW = displayMetrics.widthPixels;
        final int screenH = displayMetrics.heightPixels-100;
        score.getOffsetForPosition(screenW/10,screenH/3);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.inici, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


}
