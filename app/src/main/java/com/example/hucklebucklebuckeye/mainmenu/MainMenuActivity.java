package com.example.hucklebucklebuckeye.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.mainmenu.ui.mainmenu.FirstFragment;
import com.example.hucklebucklebuckeye.mainmenu.ui.mainmenu.MainMenuFragment;
import com.example.hucklebucklebuckeye.mainmenu.ui.mainmenu.SecondFragment;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.firstLayout, FirstFragment.newInstance())
                    .commitNow();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.secondLayout, SecondFragment.newInstance())
                    .commitNow();
        }
    }
}
