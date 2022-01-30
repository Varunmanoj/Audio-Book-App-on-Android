package com.example.audiobook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView Bookname;
    //    For TTS to work
    String Readtext;
    TextToSpeech mTTS;

    //    Vibration
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Bookname = findViewById(R.id.Bookname);
//        Get  System Language of the system
        String lang = Locale.getDefault().getLanguage(); //returns en for english and hi for hindi
//        Create on click for list item

        Bookname.setOnItemClickListener((parent, view, position, id) -> {

//                Make the app self voicing
//                Extract the text selected  by user in string
            String Readtext = Bookname.getItemAtPosition(position).toString();

            mTTS = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    mTTS.setLanguage(Locale.ENGLISH);
                }
//                    Speak button click
                mTTS.speak(Readtext, TextToSpeech.QUEUE_FLUSH, null,null);

            });
//Vibrate on click
            if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
//    Perform for API 26 and below
                vibrator.vibrate(200);
            }
//                Create List Items  clickable
            switch (position) {
                case 0:
                    Toast.makeText(getApplicationContext(), "You selected book 1", Toast.LENGTH_SHORT).show();
                    Intent nextscreanIntent = new Intent(MainActivity.this, Book1Chapters.class);
                    startActivity(nextscreanIntent);
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "You selected book 2", Toast.LENGTH_SHORT).show();
                    Intent Book2Intent = new Intent(MainActivity.this, Book2ChapterList.class);
                    startActivity(Book2Intent);
                    break;
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
//                Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//                Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }
    public  void CreateTTS(){
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
        });
    }
}
