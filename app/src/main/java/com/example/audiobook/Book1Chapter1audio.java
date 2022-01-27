package com.example.audiobook;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Book1Chapter1audio extends AppCompatActivity {
    ImageButton Playaudio;
    ImageButton Pauseaudio;
    ImageButton Stopaudio;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1_chapter1audio);
//Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

//        Link Java and XML
        Playaudio = findViewById(R.id.playaudio);
        Pauseaudio = findViewById(R.id.pauseaudio);
        Stopaudio = findViewById(R.id.stopaudio);

        Playaudio.setOnClickListener(v -> {
            //Vibrate on click
            if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
//    Perform for API 26 and below
                vibrator.vibrate(200);
            }

            if (mp == null) {
                mp = MediaPlayer.create(this, R.raw.dc_title);
                mp.setOnCompletionListener(mp -> stopaudio());
            }
            mp.start();
        });
        Pauseaudio.setOnClickListener(v -> {
            //Vibrate on click
            if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
//    Perform for API 26 and below
                vibrator.vibrate(200);
            }

            if (mp != null) {
                mp.pause();
            }
        });
        Stopaudio.setOnClickListener(v -> {
            //Vibrate on click
            if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
//    Perform for API 26 and below
                vibrator.vibrate(200);
            }

            stopaudio();
        });
    }

    private void stopaudio() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
            Log.d("mediaplayer", "released media player");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopaudio();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopaudio();
    }
}
