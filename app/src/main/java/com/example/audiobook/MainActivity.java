package com.example.audiobook;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.accessibility.AccessibilityManager;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView Bookname;
    //    For TTS to work
    String Readtext;
    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bookname = findViewById(R.id.Bookname);

//        Create on click for list item
        Bookname.setOnItemClickListener((parent, view, position, id) -> {

//            Extract the text selected  by user in string
//                Make the app self voicing
            String Readtext = Bookname.getItemAtPosition(position).toString();

            mTTS = new TextToSpeech(getApplicationContext(), status -> {
                if (status == TextToSpeech.SUCCESS) {
                    mTTS.setLanguage(Locale.ENGLISH);
                }
//                    Speak button click
                mTTS.speak(Readtext,TextToSpeech.QUEUE_FLUSH,null);

            });

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
        mTTS.stop();
        mTTS.shutdown();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()){
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
