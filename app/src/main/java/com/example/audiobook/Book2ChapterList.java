package com.example.audiobook;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Book2ChapterList extends AppCompatActivity {
ListView Book2ChapterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2_chapter_list);
        
//        Link Java and XML
        Book2ChapterList=findViewById(R.id.Book2ChapterList);
        Book2ChapterList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
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
