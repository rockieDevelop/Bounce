package com.example.safra.bounce;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 4;

    private static SoundPool soundPool;
    private static int successSound;
    private static int dieSound;
    private static int jumpSound;
    private static int victorySound;

    public SoundPlayer(Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).
                    setMaxStreams(SOUND_POOL_MAX).build();
        }
        else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        successSound = soundPool.load(context, R.raw.success, 1);
        dieSound = soundPool.load(context, R.raw.dead, 1);
        jumpSound = soundPool.load(context, R.raw.swipe, 1);
        victorySound = soundPool.load(context, R.raw.victory, 1);
    }

    public void playSuccessSound(){
        soundPool.play(successSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playDieSound(){
        soundPool.play(dieSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playJumpSound(){
        soundPool.play(jumpSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playVictorySound(){
        soundPool.play(victorySound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
