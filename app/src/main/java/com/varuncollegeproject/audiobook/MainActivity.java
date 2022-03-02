package com.varuncollegeproject.audiobook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView Bookname;
    //    For TTS to work
    String Readtext;
    TextToSpeech mTTS;

    //    Firebase
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        Bookname = findViewById(R.id.Bookname);
//        Get  System Language of the system
        String lang = Locale.getDefault().getLanguage(); //returns en for english and hi for hindi
//        Create on click for list item

        Bookname.setOnItemClickListener((parent, view, position, id) -> {

//                Extract the text selected  by user in string
            Readtext = Bookname.getItemAtPosition(position).toString();

            SpeakSelection();
            Vibrate();

//                Create List Items  clickable
            switch (position) {
                case 0:

                    Intent nextscreanIntent = new Intent(MainActivity.this, Book1Chapters.class);
                    startActivity(nextscreanIntent);
                    break;
                case 1:
                    Intent Book2Intent = new Intent(MainActivity.this, Book2ChapterList.class);
                    startActivity(Book2Intent);
                    break;
            }
        });
    }

    public void SpeakSelection() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
//                    Speak button click
            mTTS.speak(Readtext, TextToSpeech.QUEUE_FLUSH, null, null);

        });
    }

    public void CreateTTS() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
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

    public void ExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ExitADTitle)
                .setMessage(R.string.ExitADMSG)
                .setPositiveButton(R.string.ExitADPositive, (DialogInterface.OnClickListener) (dialog, which) -> finish())
                .setNegativeButton(R.string.ExitADNegative, null)
                .setIcon(R.drawable.exitapp)
                .show();
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
    public void onBackPressed() {
        ExitAlertDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
