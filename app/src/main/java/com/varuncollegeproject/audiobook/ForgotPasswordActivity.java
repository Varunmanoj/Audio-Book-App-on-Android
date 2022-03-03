package com.varuncollegeproject.audiobook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText etemail;
    Button Resetpassbtn;
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
            Resetpassword();
        });
    }

    private void Resetpassword() {
        String email = etemail.getText().toString();
        if (email.isEmpty()) {
            etemail.setError(getText(R.string.FPEmailerror));
            etemail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError(getText(R.string.FPEmailerror));
            etemail.requestFocus();
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.CheckEmail, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.Tryagain, Toast.LENGTH_SHORT).show();
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
}
