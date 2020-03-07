package com.example.hucklebucklebuckeye.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hucklebucklebuckeye.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .commitNow();
        }
    }
}
