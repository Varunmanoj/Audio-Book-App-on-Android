package com.example.audiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
        if (mp==null){
            mp=MediaPlayer.create(this,R.raw.song);
        }
        mp.start();
        });
        Pauseaudio.setOnClickListener(v -> {
      mp.pause();
        });
        Stopaudio.setOnClickListener(v -> {
      mp.stop();
        });
    }
}
