package com.example.hucklebucklebuckeye.ui.playgame;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.ui.history.HistoryFragment;


public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapFragment.newInstance())
                    .commitNow();
        }
    }

}