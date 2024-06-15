package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Locale;

public class Home extends AppCompatActivity {
    TextView timeTextView;
    private FirebaseAuth firebaseAuth;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();

        timeTextView = findViewById(R.id.Time);
        handler.post(updateTimeRunnable);
    }

    public void tappedShopNow(View view) {
        Intent intent = new Intent(Home.this, Shop.class);
        startActivity(intent);
    }

    public void tappedquote(View view) {
        Intent intent = new Intent(Home.this, ApiQuotes.class);
        startActivity(intent);
    }

    public void tappedPrivate(View view) {
        Intent intent = new Intent(Home.this, Privatenotes.class);
        startActivity(intent);
    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            handler.postDelayed(this, 3000); // 3 seconds
        }
    };

    private void updateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        String currentTime = dateFormat.format(calendar.getTime());
        timeTextView.setText(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeRunnable);
    }

    public void tappedLogout(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
