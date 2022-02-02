package com.example.audiobook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {
    ImageView image;
    TextView logo, slogan;
    Animation topAnim, bottomAnim;

    String Readtext;
    TextToSpeech mTTS;

    @Override
    protected void onStart() {
        super.onStart();
        Vibrate();
        CreateTTS();
        SpeakSelection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Vibrate();
        SpeakSelection();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);
//        Remove the Action Bathe Splash Screen
        getSupportActionBar().hide();

        image = findViewById(R.id.imageView2);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        Readtext = getString(R.string.SplashScreenLoadingMSG);

        //Text to Speech
        CreateTTS();
        SpeakSelection();
        Vibrate();


//        Automatically move to the next home Screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }

    public void Vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //Vibrate on click
        if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
//    Perform for API 26 and below
            vibrator.vibrate(200);
        }
    }

    public void ReleaseTTS() {
        //        Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }

    public void CreateTTS() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
        });
    }

    public void SpeakSelection() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
//            Speak  item
            mTTS.speak(Readtext, TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        ReleaseTTS();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReleaseTTS();
    }
}
