package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bbubtb111p16y4s1.functions.EncryptPassword;
import com.bbubtb111p16y4s1.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;

public class CurrentPasswordActivity extends AppCompatActivity {

    Button btnCreate;
    TextInputEditText txtCurrentPassword;
    Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_password_layout);
        sessions = new Sessions(this);
        txtCurrentPassword=findViewById(R.id.txtCurrentPassword);
        // Find the button by its ID
        Button btnAdd = findViewById(R.id.btnSave);
        // Set an OnClickListener for the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the verifyCurrentPassword method here
                verifyCurrentPassword();
            }
        });
    }
    private void verifyCurrentPassword() {
        String currentPassword = txtCurrentPassword.getText().toString();
        String currentPasswordHash = EncryptPassword.MD5(currentPassword);


        // Retrieve the stored user password from sessions
        String storedPasswordHash= sessions.getUserPassword();

        if (currentPasswordHash.equals(storedPasswordHash)) {
            // Passwords match, proceed to the New Password Activity
            Intent intent = new Intent(CurrentPasswordActivity.this, NewPasswordActivity.class);
            startActivity(intent);
        } else {
            // Incorrect password, display an error message
            Toast.makeText(this, "Incorrect current password", Toast.LENGTH_SHORT).show();
        }
    }
}