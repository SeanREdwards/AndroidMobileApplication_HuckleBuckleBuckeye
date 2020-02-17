package com.example.hucklebucklebuckeye.ui.mainmenu;

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

    }

}
