package com.example.audiobook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Book1Chapters extends AppCompatActivity {

ListView book1Chapter1List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1_chapters);
        
//        Link Java and XML
        book1Chapter1List=findViewById(R.id.Book1ChapterList);
        book1Chapter1List.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    Toast.makeText(getApplicationContext(), "You selected chapter 1", Toast.LENGTH_SHORT).show();
                    Intent MusicPlayerinter=new Intent(Book1Chapters.this,Book1Chapter1audio.class);
                    startActivity(MusicPlayerinter);
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "You selected chapter 2", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
