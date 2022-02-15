package com.varuncollegeproject.audiobook;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends AppCompatActivity {
    TextView start,end;
    SeekBar sk;
    ImageButton play,pause, stop;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play = findViewById(R.id.imageButton);
        pause = findViewById(R.id.imageButton2);
        stop = findViewById(R.id.imageButton3);
        start = findViewById(R.id.textView5);
        end = findViewById(R.id.textView6);
        sk = findViewById(R.id.seekBar);

        mp = null;

//        Click buttons
        play.setOnClickListener(v -> {

            Vibrate();
            CreateAudio();
            mp.start();
            AutoMoveSeekBar();
        });

        pause.setOnClickListener(v -> {
            PauseAudio();
            Vibrate();

        });

        stop.setOnClickListener(v -> {
            stopAudio();
            Vibrate();

        });
    }

    public void CreateAudio() {
        String TitleURL = "https://firebasestorage.googleapis.com/v0/b/here-me-audio-book.appspot.com/o/Dickens_Carol%2Fdc_title.wav?alt=media&token=2b394dcd-98ce-4847-8170-3c55a6a4d1e9";
        //Play Audio
        if (mp == null) {
            mp = new MediaPlayer();
            try {
                mp.setDataSource(TitleURL);
                mp.prepare();
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void AutoMoveSeekBar() {
//Set the Max of the Seekbar to the Max duration of the audio file.
        sk.setMax(mp.getDuration());
        //        Auto change SeekBar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sk.setProgress(mp.getCurrentPosition());
            }
        }, 0, 10);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Vibrate() {

        //Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
//    Perform for API 26 and below
            vibrator.vibrate(200);
        }
    }

    public void PauseAudio() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    public void stopAudio() {
        if (mp != null) {
            mp.stop();
        }

    }

    public void ReleaseMP() {
        if (mp != null) {
            mp.release();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ReleaseMP();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReleaseMP();
    }
}
