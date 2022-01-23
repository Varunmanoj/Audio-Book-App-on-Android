package com.example.audiobook;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Book2ChapterList extends AppCompatActivity {
    ListView Book2ChapterList;
    //For TTS
    String Readtext;
    TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2_chapter_list);

//        Link Java and XML
        Book2ChapterList = findViewById(R.id.Book2ChapterList);
        Book2ChapterList.setOnItemClickListener((parent, view, position, id) -> {

//            Extract the text selected  by user in string
//                Make the app self voicing
            String Readtext = Book2ChapterList.getItemAtPosition(position).toString();

            mTTS = new TextToSpeech(getApplicationContext(), status -> {
                if (status == TextToSpeech.SUCCESS) {
                    mTTS.setLanguage(Locale.ENGLISH);
                }
//                    Speak button click
                mTTS.speak(Readtext, TextToSpeech.QUEUE_FLUSH, null);

            });

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
}
