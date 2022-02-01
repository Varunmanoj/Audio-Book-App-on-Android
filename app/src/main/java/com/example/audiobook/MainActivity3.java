package com.example.audiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    ImageView image;
    TextView logo,slogan;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        image = findViewById(R.id.imageView2);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity3.this,MainActivity.class);
            startActivity(intent);
            finish();
        },3000);

    }

}