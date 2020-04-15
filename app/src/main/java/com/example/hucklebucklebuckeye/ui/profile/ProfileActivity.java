package com.example.hucklebucklebuckeye.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
        usernameDisplay.setText("Username: " + AccountDBHelper.getUsername());

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPasswordText.getText().length() < 6){
                    Toast.makeText(getApplicationContext(), "Oops - too short!", Toast.LENGTH_LONG).show();
                } else {
                    AccountDBHelper db = new AccountDBHelper(getApplicationContext());
                    String password = newPasswordText.getText()+"";
                    db.updatePassword(password);
                    Toast.makeText(getApplicationContext(), "Congrats - you reset your password!", Toast.LENGTH_LONG).show();
                }
            }
        });

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
