package com.varuncollegeproject.audiobook;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends AppCompatActivity {
    TextView Percent;
    SeekBar sk;
    ImageButton play;
    MediaPlayer mp;
    FirebaseAuth auth;
    TextToSpeech mTTS;


    int currentprogress;
    int maxduration;
    int percent;
    String ReadMoreoptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play = findViewById(R.id.imageButton);
        Percent = findViewById(R.id.textView5);
        sk = findViewById(R.id.seekBar);

        mp = null;
        CreateAudio();

//        Click buttons
        play.setOnClickListener(v -> {
            Vibrate();
            //        Show Doalog if Phone is not connected to Internet

            if (!mp.isPlaying()) {

                StartAudio();
                AutoMoveSeekBar();
                play.setImageDrawable(getDrawable(R.drawable.pauseaudio));
                play.setContentDescription(getString(R.string.PauseAudio));
            } else {
                PauseAudio();
                play.setImageDrawable(getDrawable(R.drawable.playaudio));
                play.setContentDescription(getString(R.string.PlayAudio));
            }
        });
    }

    public void CreateAudio() {
        String TitleURL = "https://firebasestorage.googleapis.com/v0/b/here-me-audio-book.appspot.com/o/Dickens_Carol%2Fdc_title.wav?alt=media&token=2b394dcd-98ce-4847-8170-3c55a6a4d1e9";
        //Play Audio
        if (mp == null) {
            mp = new MediaPlayer();
            try {
                mp.setDataSource(TitleURL);
                mp.prepare();
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void AutoMoveSeekBar() {
//Set the Max of the Seekbar to the Max duration of the audio file.
        sk.setMax(mp.getDuration());
        maxduration = mp.getDuration() / 100;
        currentprogress = mp.getCurrentPosition() / 100;

        //        Auto change SeekBar
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sk.setProgress(mp.getCurrentPosition());

            }
        }, 0, 10);

//Convert the value in to %

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentprog;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
                currentprog = progress;
                percent = currentprog / maxduration;
                Percent.setText("" + percent + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Percent.setText("" + percent + "%");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //                Change Pause button to play button on completion of song
        if (mp != null) {
            mp.setOnCompletionListener(mp -> {
                play.setImageDrawable(getDrawable(R.drawable.playaudio));
                play.setContentDescription(getString(R.string.PlayAudio));
            });
        }
    }

    public void Vibrate() {

        //Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
//    Perform forAPI  26 and above
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
//    Perform for API 26 and below
            vibrator.vibrate(200);
        }
    }


    public void StartAudio() {
        if (mp != null) {
            mp.start();
        }
    }

    public void PauseAudio() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    public void stopAudio() {
        if (mp != null) {
            mp.stop();
            mp.reset();
        }
    }

    public void CreateTTS() {
        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        CreateAudio();
        CreateTTS();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CreateAudio();
        CreateTTS();
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

    public Boolean CheckConection() {
//        ConnectivityManager is used to check if phone is conected to Internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

//        NetworkInfo is used to check if phone is Connected to either WIFI or Mobile Data
        NetworkInfo Wifistate = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((Wifistate != null) && (Wifistate.isConnected())) || (MobileState != null) && MobileState.isConnected();
    }

    private void CreateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Player.this);
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

    private void Logout() {
        auth.signOut();
        Toast.makeText(this, R.string.LogoutSuss, Toast.LENGTH_SHORT).show();
        Intent logoutintent = new Intent(this, LoginActivity.class);

        //        Clear the Stack in memory and redirect the user to the login page on press of back button
        logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutintent);
    }

    //    options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playeroptionsmenu, menu);
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
            case R.id.Transcript:
                startActivity(new Intent(this, DCTitle.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
