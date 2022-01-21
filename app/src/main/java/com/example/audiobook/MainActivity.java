package com.example.audiobook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
ListView Bookname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bookname=findViewById(R.id.Bookname);

//        Create on click for list item
        Bookname.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    Toast.makeText(getApplicationContext(), "You selected book 1", Toast.LENGTH_SHORT).show();
                    Intent nextscreanIntent=new Intent(MainActivity.this,Book1Chapters.class);
                    startActivity(nextscreanIntent);
                case 1:
                    Toast.makeText(getApplicationContext(), "You selected book 2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
