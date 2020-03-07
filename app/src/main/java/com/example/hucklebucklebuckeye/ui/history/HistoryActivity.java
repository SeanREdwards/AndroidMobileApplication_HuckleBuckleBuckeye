package com.example.hucklebucklebuckeye.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hucklebucklebuckeye.Log;
import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final LogBaseHelper logHandler = new LogBaseHelper(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .commitNow();
        }
    }
}
