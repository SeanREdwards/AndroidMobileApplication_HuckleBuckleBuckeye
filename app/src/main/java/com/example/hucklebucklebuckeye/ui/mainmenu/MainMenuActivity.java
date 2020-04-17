package com.example.hucklebucklebuckeye.ui.mainmenu;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import com.example.hucklebucklebuckeye.R;



public class MainMenuActivity extends AppCompatActivity {

    final int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainMenuFragment.newInstance())
                    .commitNow();
        }

        Log.d(getString(R.string.MainMenuActivity), getString(R.string.onCreate));

    }

    protected void onStart() {
        super.onStart();
        Log.d(getString(R.string.MainMenuActivity), getString(R.string.onStart));
    }

    protected void onPause() {
        super.onPause();
        Log.d(getString(R.string.MainMenuActivity), getString(R.string.onPause));
    }

    protected void onResume() {
        super.onResume();
        Log.d(getString(R.string.MainMenuActivity), getString(R.string.onResume));
    }

    protected void onStop() {
        super.onStop();
        Log.d(getString(R.string.MainMenuActivity), getString(R.string.onStop));
    }


}