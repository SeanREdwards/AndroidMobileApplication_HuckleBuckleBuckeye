package com.example.hucklebucklebuckeye.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.AccountDBHelper;

public class ProfileActivity extends AppCompatActivity {
    private TextView newPasswordText;
    private TextView usernameDisplay;
    private Button changePasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LoginFormState loginFormState = new LoginFormState();
        setContentView(R.layout.activity_profile);
        usernameDisplay = findViewById(R.id.username_view);
        newPasswordText = findViewById(R.id.password_input);
        changePasswordButton = findViewById(R.id.reset_button);
        usernameDisplay.setText(getString(R.string.Username) + AccountDBHelper.getUsername());

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPasswordText.getText().length() < 6){
                    Toast.makeText(getApplicationContext(), getString(R.string.tooShort), Toast.LENGTH_LONG).show();
                } else {
                    AccountDBHelper db = new AccountDBHelper(getApplicationContext());
                    String password = newPasswordText.getText()+"";
                    db.updatePassword(password);
                    Toast.makeText(getApplicationContext(), getString(R.string.resetPass), Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.d(getString(R.string.ProfileActivity), getString(R.string.onCreate));
    }

    protected void onStart() {
        super.onStart();
        Log.d(getString(R.string.ProfileActivity), getString(R.string.onStart));

    }

    protected void onPause() {
        super.onPause();
        Log.d(getString(R.string.ProfileActivity), getString(R.string.onPause));
    }

    protected void onResume() {
        super.onResume();
        Log.d(getString(R.string.ProfileActivity), getString(R.string.onResume));
    }

    protected void onStop() {
        super.onStop();
        Log.d(getString(R.string.ProfileActivity), getString(R.string.onStop));
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getString(R.string.ProfileActivity), getString(R.string.onDestroy));
    }
}
