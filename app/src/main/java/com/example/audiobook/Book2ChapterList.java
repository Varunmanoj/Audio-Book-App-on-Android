package com.example.audiobook;

import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Book2ChapterList extends AppCompatActivity {
    ListView Book2ChapterList;
    //For TTS
    String ReadText;
    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2_chapter_list);

//        Link Java and XML
        Book2ChapterList = findViewById(R.id.Book2ChapterList);
        Book2ChapterList.setOnItemClickListener((parent, view, position, id) -> {

            //            Extract the text selected  by user in string
            ReadText = Book2ChapterList.getItemAtPosition(position).toString();
            SpeakSelection();
            Vibrate();

//Click event for List Item
            switch (position) {
                case 0:
                    Toast.makeText(getApplicationContext(), "You selected chapter 1", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "You selected chapter 2", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    public void SpeakSelection() {
//                Make the app self voicing

        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
//                    Speak button click
            mTTS.speak(ReadText, TextToSpeech.QUEUE_FLUSH, null, null);

        });
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
        }
    }
    public  void CreateTTS(){
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
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
    @Override
    protected void onResume() {
        super.onResume();
        CreateTTS();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        CreateTTS();
    }
    @Override
    protected void onStart() {
        super.onStart();
        CreateTTS();
    }
}
