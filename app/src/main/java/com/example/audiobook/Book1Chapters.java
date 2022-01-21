package com.example.audiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Book1Chapters extends AppCompatActivity {
ListView book1Chapter1List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book1_chapters);
        
//        Link Java and XML
        book1Chapter1List=findViewById(R.id.Book1ChapterList);
        book1Chapter1List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(), "You selected chapter 1", Toast.LENGTH_SHORT).show();
                    case 1:
                        Toast.makeText(getApplicationContext(), "You selected chapter 2", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
