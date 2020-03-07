package com.example.hucklebucklebuckeye.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.model.Account;
import com.example.hucklebucklebuckeye.model.AccountDBHelper;
import com.example.hucklebucklebuckeye.model.DatabaseHandler;
import com.example.hucklebucklebuckeye.ui.mainmenu.MainMenuActivity;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "onCreate(Bundle?) method called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final AccountDBHelper databaseHandler = new AccountDBHelper(this);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });



        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                //ADDED STUFF -- should actually log in first
                //TODO: for real shouldnt be here but yeah
                databaseHandler.insertData(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                boolean validated = validateUser(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            }
        });
    }


    private boolean validateUser(String username, String password){
        AccountDBHelper dbHelper = new AccountDBHelper(getApplicationContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //dbHelper.onCreate(db);
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Account.AccountEntry.COLUMN_NAME_USERNAME, username);
        values.put(Account.AccountEntry.COLUMN_NAME_PASSWORD, password);

        Log.d("Username + password: ", values.toString());
        // Insert the new row, returning the primary key value of the new row
        dbHelper.onCreate(db);
        //db.execSQL("INSERT INTO hucklebucklebuckeye.account VALUES (username, password)");
        //long newRowId = db.insert(Account.AccountEntry.TABLE_NAME, null, values);
        //UserLog.d("insertion result: ", String.valueOf(newRowId));
        return true;
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience (verify this)
        startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        super.onStart();
        Log.d("LoginActivity", "onStart() method called");
    }

    protected void onPause() {
        super.onPause();
        Log.d("LoginActivity", "onPause() method called");
    }

    protected void onResume() {
        super.onResume();
        Log.d("LoginActivity", "onResume() method called");
    }

    protected void onStop() {
        super.onStop();
        Log.d("LoginActivity", "onStop() method called");
    }

}
