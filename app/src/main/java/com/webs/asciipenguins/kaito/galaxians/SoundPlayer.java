package com.webs.asciipenguins.kaito.galaxians;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by usuario on 05/12/2014.
 */


public class SoundPlayer {
    public static final int shortExplosion = 1;
    public static final int longExplosion = 2;
    public static final int cheers = 3;
    public static final int shot = 4;
    public static final int enemyshot = 7;
    public static final int tirurirurin = 5;
    public static final int gong = 6;


    private static SoundPool soundPool;
    private static HashMap soundPoolMap;


    /** Populate the SoundPool*/
    public static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap(3);

        soundPoolMap.put( shortExplosion, soundPool.load(context, R.raw.explosionshort, 2) );
        soundPoolMap.put( longExplosion, soundPool.load(context, R.raw.explosionmeslong, 1) );
        soundPoolMap.put( cheers, soundPool.load(context, R.raw.cheers, 3));
        soundPoolMap.put( shot, soundPool.load(context, R.raw.anothershot, 4));
        soundPoolMap.put( tirurirurin, soundPool.load(context, R.raw.tirurirurin, 5));
        soundPoolMap.put( gong, soundPool.load(context, R.raw.gong, 6));
        soundPoolMap.put( enemyshot, soundPool.load(context, R.raw.laser, 6));

    }

    /** Play a given sound in the soundPool */
    public static void playSound(Context context, int soundID) {
        if(soundPool == null || soundPoolMap == null){
            initSounds(context);
        }
        float volume = (float) 0.2;// whatever in the range = 0.0 to 1.0

        // play sound with same right and left volume, with a priority of 1,
        // zero repeats (i.e play once), and a playback rate of 1f
        soundPool.play((Integer) soundPoolMap.get(soundID), volume, volume, 1, 0, 1f);
    }

}