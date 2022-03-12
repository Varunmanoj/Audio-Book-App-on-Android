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
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etemail;
    Button Resetpassbtn;
    TextToSpeech mTTS;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etemail = findViewById(R.id.resetpassemail);
        Resetpassbtn = findViewById(R.id.resetpassbtn);
        auth = FirebaseAuth.getInstance();


        Resetpassbtn.setOnClickListener(v -> {
            Vibrate();
            if (!CheckConection()) {
                CreateDialog();
            } else {
                Resetpassword();
            }
        });
    }

    private void Resetpassword() {
        String email = etemail.getText().toString().trim();
        if (email.isEmpty()) {
            etemail.setError(getText(R.string.FPEmailerror));
            mTTS.speak(getText(R.string.FPEmailerror), TextToSpeech.QUEUE_FLUSH, null, null);
            etemail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError(getString(R.string.FPvalidemailerror));
            mTTS.speak(getText(R.string.FPvalidemailerror), TextToSpeech.QUEUE_FLUSH, null, null);
            etemail.requestFocus();
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.CheckEmail, Toast.LENGTH_SHORT).show();
                    mTTS.speak(getText(R.string.CheckEmail), TextToSpeech.QUEUE_FLUSH, null, null);
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.Tryagain, Toast.LENGTH_SHORT).show();
                    mTTS.speak(getText(R.string.Tryagain), TextToSpeech.QUEUE_FLUSH, null, null);
                }
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

    public Boolean CheckConection() {
//        ConnectivityManager is used to check if phone is conected to Internet or not
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

//        NetworkInfo is used to check if phone is Connected to either WIFI or Mobile Data
        NetworkInfo Wifistate = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo MobileState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((Wifistate != null) && (Wifistate.isConnected())) || (MobileState != null) && MobileState.isConnected();
    }

    private void CreateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ForgotPasswordActivity.this);
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

    public void CreateTTS() {
//       Make the app self voicing

        mTTS = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
//
        });
    }

    public void ReleaseTTS() {
        //        Release resources if audio tts is not speaking
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
}
