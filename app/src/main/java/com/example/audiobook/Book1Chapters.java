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

public class Book1Chapters extends AppCompatActivity {

    ListView book1Chapter1List;
    //    For TTS to work
    String Readtext;
    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1_chapters);

        //Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

//        Link Java and XML
        book1Chapter1List = findViewById(R.id.Book1ChapterList);
        book1Chapter1List.setOnItemClickListener((parent, view, position, id) -> {

//            Extract the text selected  by user in string
//                Make the app self voicing
            String Readtext = book1Chapter1List.getItemAtPosition(position).toString();
            mTTS = new TextToSpeech(getApplicationContext(), status -> {
                if (status == TextToSpeech.SUCCESS) {
                    mTTS.setLanguage(Locale.ENGLISH);
                }
//                    Speak button click
                mTTS.speak(Readtext, TextToSpeech.QUEUE_FLUSH, null);
            });

            //Vibrate on click
            if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
//    Perform for API 26 and below
                vibrator.vibrate(200);
            }
            //Click Listener for items
            switch (position) {
                case 0:
                    Toast.makeText(getApplicationContext(), "You selected chapter 1", Toast.LENGTH_SHORT).show();
                    Intent MusicPlayerinter = new Intent(Book1Chapters.this, Book1Chapter1audio.class);
                    startActivity(MusicPlayerinter);
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "You selected chapter 2", Toast.LENGTH_SHORT).show();
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
    protected void onPause() {
        super.onPause();
//        Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()) {
            mTTS.stop();
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
}
