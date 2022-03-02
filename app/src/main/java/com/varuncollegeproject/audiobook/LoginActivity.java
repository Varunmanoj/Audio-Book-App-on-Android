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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    TextView FP;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        FP = findViewById(R.id.fp);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            Vibrate();
            if (!CheckConection()) {
                CreateDialog();
            } else {
                loginUser();
            }
        });
        tvRegisterHere.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        FP.setOnClickListener(v -> {
            Vibrate();
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    private void loginUser() {
        String email = Objects.requireNonNull(etLoginEmail.getText()).toString();
        String password = Objects.requireNonNull(etLoginPassword.getText()).toString();

        if (email.isEmpty()) {
            etLoginEmail.setError(getText(R.string.LoginEmailError));
            etLoginEmail.requestFocus();
        } else if (password.isEmpty()) {
            etLoginPassword.setError(getText(R.string.LoginPassworderror));
            etLoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Log in Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
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
}
