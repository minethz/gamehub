package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private EditText lemail,pass;
private FirebaseAuth mAuth;

ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lemail=findViewById(R.id.logemail);
        pass=findViewById(R.id.logPass);
        pbar=findViewById(R.id.progressBar);

        pbar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
    }


    public void signupTapped(View view) {
        Intent intent = new Intent(MainActivity.this, Signup.class);
        startActivity(intent);
    }

    public void logingTapped(View view) {
        String mail = lemail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        pbar.setVisibility(View.VISIBLE);
        if (mail.isEmpty() || password.isEmpty()) {
            pbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                pbar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                pbar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                Log.e("LoginActivity", "onComplete: Failed=" + task.getException());
                            }
                        }
                    });
        }
    }

}