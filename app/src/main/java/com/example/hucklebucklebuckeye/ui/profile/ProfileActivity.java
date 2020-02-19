package com.example.hucklebucklebuckeye.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.hucklebucklebuckeye.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("ProfileActivity", "onCreate(Bundle) method called");
    }

    protected void onStart() {
        super.onStart();
        Log.d("ProfileActivity", "onStart() method called");
    }

    protected void onPause() {
        super.onPause();
        Log.d("ProfileActivity", "onPause() method called");
    }

    protected void onResume() {
        super.onResume();
        Log.d("ProfileActivity", "onResume() method called");
    }

    protected void onStop() {
        super.onStop();
        Log.d("ProfileActivity", "onStop() method called");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ProfileActivity", "onDestroy() method called");
    }
}
