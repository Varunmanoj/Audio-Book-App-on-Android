package com.varuncollegeproject.audiobook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Book2ChapterList extends AppCompatActivity {
    ListView Book2ChapterList;
    //For TTS
    String ReadText;
    TextToSpeech mTTS;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book2_chapter_list);

//        Link Java and XML
        Book2ChapterList = findViewById(R.id.Book2ChapterList);
        Book2ChapterList.setOnItemClickListener((parent, view, position, id) -> {

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.ProgressTitle));
            progressDialog.setMessage(getString(R.string.ProgressMSG));

            //            Extract the text selected  by user in string
            ReadText = Book2ChapterList.getItemAtPosition(position).toString();
            SpeakSelection();
            Vibrate();

//Click event for List Item
            switch (position) {
                case 0:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, Ptitle.class));
                    }
                    break;
                case 1:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, Pchap1.class));
                    }
                    break;
                case 2:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap2.class));
                    }
                    break;
                case 3:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap3.class));
                    }
                    break;
                case 4:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap4.class));
                    }
                    break;
                case 5:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap5.class));
                    }
                    break;
                case 6:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap6.class));
                    }
                    break;
                case 7:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap7.class));
                    }
                    break;
                case 8:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap8.class));
                    }
                    break;
                case 9:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap9.class));
                    }
                    break;
                case 10:
                    if (!CheckConection()) {
                        CreateDialog();
                    } else {
                        progressDialog.show();
                        startActivity(new Intent(this, pchap10.class));
                    }
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

    public void CreateTTS() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
        });
    }

    private void CreateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Book2ChapterList.this);
        dialog.setTitle(R.string.ADTitle);
        dialog.setMessage(R.string.AlertDialogMSG).setCancelable(false)
                .setIcon(R.drawable.wifioff)
                .setPositiveButton(R.string.ADPositive, (dialog1, which) -> {
//                    Open the Wireless Settings Page on the Phone (Wifi and Mobile Data)
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                })
                .setNegativeButton(R.string.ADNegative, (dialog12, which) -> Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show())
                .show();
    }

    public Boolean CheckConection() {
//        ConnectivityManager is used to check if phone is conected to Internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

//        NetworkInfo is used to check if phone is Connected to either WIFI or Mobile Data
        NetworkInfo Wifistate = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((Wifistate != null) && (Wifistate.isConnected())) || (MobileState != null) && MobileState.isConnected();
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
