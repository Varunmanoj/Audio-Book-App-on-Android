package com.example.audiobook;

import android.media.MediaPlayer;
import android.os.Bundle;
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

//        Link Java and XML
        Playaudio=findViewById(R.id.playaudio);
        Pauseaudio=findViewById(R.id.pauseaudio);
        Stopaudio=findViewById(R.id.stopaudio);

        Playaudio.setOnClickListener(v -> {
            if (mp == null) {
                mp = MediaPlayer.create(this, R.raw.song);
                mp.setOnCompletionListener(mp -> stopaudio());
            }
            mp.start();
        });
        Pauseaudio.setOnClickListener(v -> {
            if (mp != null) {
                mp.pause();
            }
        });
        Stopaudio.setOnClickListener(v -> {
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
