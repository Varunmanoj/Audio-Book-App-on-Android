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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;
    TextToSpeech mTTS;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.resetpassbtn);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            Vibrate();
            if (!CheckConection()) {
                CreateDialog();
            } else {
                createUser();
            }
        });

        tvLoginHere.setOnClickListener(view -> {
            Vibrate();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void createUser() {
        String email = Objects.requireNonNull(etRegEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etRegPassword.getText()).toString().trim();

        if (email.isEmpty()) {
            etRegEmail.setError(getText(R.string.EmailError));
            mTTS.speak(getText(R.string.EmailError), TextToSpeech.QUEUE_FLUSH, null, null);
            etRegEmail.requestFocus();
        } else if (password.isEmpty()) {
            etRegPassword.setError(getText(R.string.PasswordError));
            mTTS.speak(getText(R.string.PasswordError), TextToSpeech.QUEUE_FLUSH, null, null);
            etRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    mTTS.speak("User registered successfully", TextToSpeech.QUEUE_FLUSH, null, null);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    mTTS.speak("Registration Error:" + task.getException().getMessage(), TextToSpeech.QUEUE_FLUSH, null,
                            null);
                }
            });
        }
    }

    public Boolean CheckConection() {
        // ConnectivityManager is used to check if phone is conected to Internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // NetworkInfo is used to check if phone is Connected to either WIFI or Mobile
        // Data
        NetworkInfo Wifistate = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((Wifistate != null) && (Wifistate.isConnected())) || (MobileState != null) && MobileState.isConnected();
    }

    private void CreateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
        dialog.setTitle(R.string.ADTitle);
        dialog.setMessage(R.string.AlertDialogMSG).setCancelable(false)
                .setIcon(R.drawable.wifioff)
                .setPositiveButton(R.string.ADPositive, (dialog1, which) -> {
                    // Open the Wireless Settings Page on the Phone (Wifi and Mobile Data)
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                })
                .setNegativeButton(R.string.ADNegative,
                        (dialog12,
                         which) -> Toast.makeText(getApplicationContext(), "Please Connect to Internet",
                                Toast.LENGTH_SHORT).show())
                .show();

    }

    public void Vibrate() {

        // Vibrate on click
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            // Perform forAPI 26 and above
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // Perform for API 26 and below
            vibrator.vibrate(200);
        }
    }

    public void CreateTTS() {
        // Make the app self voicing

        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
            //
        });
    }

    public void ReleaseTTS() {
        // Release resources if audio tts is not speaking
        if (!mTTS.isSpeaking()) {
            mTTS.stop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        CreateTTS();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReleaseTTS();
    }

    public void openTTSSettings() {
        //Open Android Text-To-Speech Settings
        Intent intent = new Intent();
        intent.setAction("com.android.settings.TTS_SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registerloginmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Read out the selected option
        String ReadMoreoptionsMenu = item.getTitle().toString();
        mTTS.speak(ReadMoreoptionsMenu, TextToSpeech.QUEUE_FLUSH, null, null);

        if (item.getItemId() == R.id.light) {
            Vibrate();
            // switch to Light Mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (item.getItemId() == R.id.Night) {
            Vibrate();
            // switch to Dark Mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (item.getItemId() == R.id.SystemDefault) {
            Vibrate();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (item.getItemId() == R.id.tts) {
            Vibrate();
            openTTSSettings();
        }

        return super.onOptionsItemSelected(item);
    }
}
