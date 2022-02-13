package com.varuncollegeproject.audiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class Player extends AppCompatActivity {
    TextView start,end;
    SeekBar sk;
    ImageButton play,pause,stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play = findViewById(R.id.imageButton);
        pause = findViewById(R.id.imageButton2);
        stop = findViewById(R.id.imageButton3);
        start = findViewById(R.id.textView5);
        end = findViewById(R.id.textView6);
        sk  = findViewById(R.id.seekBar);
    }
}