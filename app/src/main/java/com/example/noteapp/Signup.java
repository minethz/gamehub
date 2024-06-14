package com.example.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
private EditText mail,pass,conpass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail=findViewById(R.id.logemail);
        pass=findViewById(R.id.logPass);
        conpass=findViewById(R.id.conlogPass);

        mAuth = FirebaseAuth.getInstance();
    }

    public void backToLoginTapped(View view) {
        Intent intent = new Intent(Signup.this, MainActivity.class);
        startActivity(intent);
    }

    public void signuptapped(View view) {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String conpassword = conpass.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()|| conpassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
        }else {
            if (password.length()<7)
            {
                Toast.makeText(getApplicationContext(), "Enter more than 8 characters", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(conpassword)) {
                Toast.makeText(getApplicationContext(), "Password doesnt match", Toast.LENGTH_SHORT).show();
            }else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Account created",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Signup.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        }
    }
}