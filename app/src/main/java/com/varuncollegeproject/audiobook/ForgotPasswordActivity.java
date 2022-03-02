package com.varuncollegeproject.audiobook;

import android.content.Intent;
import android.os.Bundle;
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
            Resetpassword();
        });
    }

    private void Resetpassword() {
        String email = etemail.getText().toString();
        if (email.isEmpty()) {
            etemail.setError(getText(R.string.FPEmailerror));
            etemail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError(getText(R.string.FPEmailerror));
            etemail.requestFocus();
            return;
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
}
