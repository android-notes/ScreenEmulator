package com.wanjian.emulator.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wanjian.screenemulator.SettingActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(LoginActivity.this, "hello", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
//                new AlertDialog.Builder(LoginActivity.this)
//                        .setTitle("hello")
//                        .setMessage("scale screen")
//                        .show();
            }
        });

    }


}

