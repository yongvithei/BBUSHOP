package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show layout
        setContentView(R.layout.login_layout);
    }

    public void GoToSignUp(View view){
        Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(signup);
    }
    public void GoToMainApp(View view){
        Intent mainapp = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainapp);
        finish();
    }
}