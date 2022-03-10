package com.varuncollegeproject.audiobook;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView Bookname;
    //    For TTS to work
    String Readtext;
    String ReadMoreoptionsMenu;
    TextToSpeech mTTS;

    //    Firebase
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();


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
            mTTS.shutdown();
        }
    }

    public Boolean CheckConection() {
//        ConnectivityManager is used to check if phone is conected to Internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

//        NetworkInfo is used to check if phone is Connected to either WIFI or Mobile Data
        NetworkInfo Wifistate = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((Wifistate != null) && (Wifistate.isConnected())) || (MobileState != null) && MobileState.isConnected();
    }

    private void CreateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
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

    public void LogoutConfermDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.LogoutConfermTitle)
                .setMessage(R.string.LogoutConfermMSG)
                .setPositiveButton(R.string.ExitADPositive, (dialog, which) -> Logout())
                .setNegativeButton(R.string.ExitADNegative, null)
                .setIcon(R.drawable.exitapp)
                .show();
    }

    private void Logout() {
        auth.signOut();
        Toast.makeText(this, R.string.LogoutSuss, Toast.LENGTH_SHORT).show();
        Intent logoutintent = new Intent(this, LoginActivity.class);

        //        Clear the Stack in memory and redirect the user to the login page on press of back button
        logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutintent);
    }

    public void ExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.ExitADTitle)
                .setMessage(R.string.ExitADMSG)
                .setPositiveButton(R.string.ExitADPositive, (dialog, which) -> finish())
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

    //    options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainoptionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        Read out the selected option
        ReadMoreoptionsMenu = item.getTitle().toString();
        mTTS.speak(ReadMoreoptionsMenu, TextToSpeech.QUEUE_FLUSH, null, null);
        switch (item.getItemId()) {
            case R.id.logout:
                Vibrate();
                LogoutConfermDialog();
                break;
            case R.id.light:
                Vibrate();
//                switch to Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.Night:
                Vibrate();
//                switch to Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.SystemDefault:
                Vibrate();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.helpandsupport:
                Vibrate();
                if (!CheckConection()) {
                    CreateDialog();
                } else {
                    startActivity(new Intent(this, Help.class));
                }
                break;
            case R.id.contactus:
                Vibrate();
                startActivity(new Intent(this, Contactus.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
