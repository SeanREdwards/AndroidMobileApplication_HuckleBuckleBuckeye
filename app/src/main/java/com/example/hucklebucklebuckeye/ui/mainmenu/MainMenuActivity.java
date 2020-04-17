package com.example.hucklebucklebuckeye.ui.mainmenu;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.hucklebucklebuckeye.R;



public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainMenuFragment.newInstance())
                    .commitNow();
        }

        Log.d(getString(R.string.MainMenuActivity), "onCreate(Bundle) method called");

    }

    protected void onStart() {
        super.onStart();
        Log.d(getString(R.string.MainMenuActivity), "onStart() method called");
    }

    protected void onPause() {
        super.onPause();
        Log.d(getString(R.string.MainMenuActivity), "onPause() method called");
    }

    protected void onResume() {
        super.onResume();
        Log.d(getString(R.string.MainMenuActivity), "onResume() method called");
    }

    protected void onStop() {
        super.onStop();
        Log.d(getString(R.string.MainMenuActivity), "onStop() method called");
    }

}
