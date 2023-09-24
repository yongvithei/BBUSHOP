package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bbubtb111p16y4s1.functions.Sessions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername, txtPassword;
    Button btnLogin;
    ProgressBarDialog dialog;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // show layout
        setContentView(R.layout.login_layout);
        txtUsername = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLoginUser);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String strUsername = txtUsername.getText().toString().trim();
        String strPassword = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(strUsername)) {
            txtUsername.setError("Required Username");
            txtUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(strPassword)) {
            txtPassword.setError("Required Password!");
            txtPassword.requestFocus();
            return;
        }
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog = new ProgressBarDialog(LoginActivity.this);
                        dialog.setMessage("Logging in...");
                        dialog.show();
                    }
                });
                //doInBackground

                String strUri = getText(R.string.AppURL).toString() + "login_user.php";
                String[] params = {"UserNameLogin", "UserPassLogin"};
                String[] values = {strUsername, strPassword};
                RequestHelper login = new RequestHelper();
                result = login.Execute(strUri, params, values);
                //PostExecute

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.close();
                                //New AlertDialog.Builder(LoginActivity.this)
                                //.setMessage("Result: " + result.toString())
                                //.show();
                                try {
                                    JSONObject object = new JSONObject(result.toString());
                                    if(object.getInt("success")==1){
                                        //save user login info
                                        Sessions sessions=new Sessions(LoginActivity.this);
                                        sessions.setUserID(object.getInt("UserID"));
                                        sessions.setUserName(object.getString("UserName"));
                                        sessions.setUserPassword(object.getString("UserPassword"));
                                        sessions.setUserFullName(object.getString("UserFullname"));
                                        sessions.setUserType(object.getString("UserType"));
                                        sessions.setUserEmail(object.getString("UserEmail"));
                                        sessions.setUserImage(object.getString("UserImage"));
                                        Toast.makeText(LoginActivity.this,
                                                object.getString("msg_success"),
                                                Toast.LENGTH_LONG).show();
                                        // go to login
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this,
                                                object.getString("msg_error"),
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }, 700);
                    }
                });


            }
        });
    }

    public void GoToSignUp(View view){
        Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(signup);
    }
}