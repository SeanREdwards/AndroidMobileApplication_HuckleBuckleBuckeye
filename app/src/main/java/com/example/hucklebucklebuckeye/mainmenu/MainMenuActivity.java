package com.example.hucklebucklebuckeye.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.mainmenu.ui.mainmenu.MainMenuFragment;

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
