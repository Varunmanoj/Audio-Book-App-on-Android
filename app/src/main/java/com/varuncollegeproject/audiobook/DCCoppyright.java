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

public class DCCoppyright extends AppCompatActivity {
    TextView Percent;
    SeekBar sk;
    ImageButton play;
    MediaPlayer mp;


    int currentprogress;
    int maxduration;
    int percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dccoppyright);
        play = findViewById(R.id.imageButton);
        Percent = findViewById(R.id.textView5);
        sk = findViewById(R.id.seekBar);

        mp = null;
        CreateAudio();

//        Click buttons
        play.setOnClickListener(v -> {
            Vibrate();
            //        Show Doalog if Phone is not connected to Internet

            if (!mp.isPlaying()) {

                StartAudio();
                AutoMoveSeekBar();
                play.setImageDrawable(getDrawable(R.drawable.pauseaudio));
                play.setContentDescription(getString(R.string.PauseAudio));
            } else {
                PauseAudio();
                play.setImageDrawable(getDrawable(R.drawable.playaudio));
                play.setContentDescription(getString(R.string.PlayAudio));


            }

        });
    }


    public void CreateAudio() {
        String TitleURL = "https://firebasestorage.googleapis.com/v0/b/here-me-audio-book.appspot.com/o/Dickens_Carol%2Fdc_copyrightinformation.wav?alt=media&token=b953fce6-2792-4ef8-839d-8a77982b5f19";
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
        maxduration = mp.getDuration() / 100;
        currentprogress = mp.getCurrentPosition() / 100;

        //        Auto change SeekBar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sk.setProgress(mp.getCurrentPosition());

            }
        }, 0, 10);

//Convert the value in to %

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentprog;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
                currentprog = progress;
                percent = currentprog / maxduration;
                Percent.setText("" + percent + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Percent.setText("" + percent + "%");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //                Change Pause button to play button on completion of song
        if (mp != null) {
            mp.setOnCompletionListener(mp -> {
                play.setImageDrawable(getDrawable(R.drawable.playaudio));
                play.setContentDescription(getString(R.string.PlayAudio));
            });
        }
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


    public void StartAudio() {
        if (mp != null) {
            mp.start();
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
            mp.reset();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        CreateAudio();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CreateAudio();

    }
}
