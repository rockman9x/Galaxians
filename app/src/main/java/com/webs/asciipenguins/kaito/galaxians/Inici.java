package com.webs.asciipenguins.kaito.galaxians;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Inici extends Activity implements View.OnClickListener {

    Button StartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

        StartButton = (Button)findViewById(R.id.buttonPlay);
        StartButton.setOnClickListener(this);

        StartButton = (Button)findViewById(R.id.buttonstats);
        StartButton.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if( id == R.id.buttonPlay){
            startActivity(new Intent().setClass(this, GaemActivity.class));

        }
        if( id == R.id.buttonstats){
            startActivity(new Intent().setClass(this, StatsActivity.class));
        }

    }
}
