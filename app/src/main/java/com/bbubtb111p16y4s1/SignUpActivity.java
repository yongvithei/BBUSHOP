package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText txtfullname, txtusername, txtpassword, txtconfirmpwd;
    Button btnregister;
    ProgressBarDialog dialog;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);
        txtfullname = findViewById(R.id.txtFullNameRegister);
        txtusername = findViewById(R.id.txtUserNameRegister);
        txtpassword = findViewById(R.id.txtPasswordRegister);
        txtconfirmpwd = findViewById(R.id.txtConfirmPassword);
        btnregister = findViewById(R.id.btnSignUpOK);
        btnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String strfullName = txtfullname.getText().toString();
        String struserName = txtusername.getText().toString().trim();
        String strpassword = txtpassword.getText().toString().trim();
        String strconfirm = txtconfirmpwd.getText().toString().trim();
        if(TextUtils.isEmpty(strfullName)){
            txtfullname.setError("Required Full Name!");
            txtfullname.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(struserName)){
            txtusername.setError("Required UserName!");
            txtusername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strpassword)){
            txtpassword.setError("Required Password!");
            txtpassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strconfirm)){
            txtconfirmpwd.setError("Required Confirm Password!");
            txtconfirmpwd.requestFocus();
            return;
        }
        if(strpassword.equals(strconfirm)){
            ExecutorService service = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            service.execute(new Runnable() {
                @Override
                public void run() {
                    // PreExecute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ProgressBarDialog(SignUpActivity.this);
                            dialog.setMessage("Loading...");
                            dialog.show();
                        }
                    });
                    // doInBackground

                        String strUri = getText(R.string.AppURL)+ "register_user.php";
                        String[] params={"FullNameRegister","UserNameRegister","PasswordRegister"};
                        String[] values={strfullName,struserName,strpassword};
                        RequestHelper signup=new RequestHelper();
                        result = signup.Execute(strUri,params,values);


                   //post execute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.close();
                                    //new AlertDialog.Builder(SignUpActivity.this)
                                    //.setMessage("Result: " + result.toString())
                                    //.show();
                                    try {
                                        JSONObject object = new JSONObject(result.toString());
                                        if(object.getInt("success")==1){
                                            Toast.makeText(SignUpActivity.this,
                                                    object.getString("msg_success"),
                                                    Toast.LENGTH_LONG).show();
                                            // go to login
                                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(SignUpActivity.this,
                                                    object.getString("msg_error"),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            },1000);
                        }
                    });
                }
            });
        }else{
            //Toast.makeText(SignUpActivity.this,"Confirm password does not match Password!",
                    //Toast.LENGTH_LONG).show();
            AlertDialog.Builder ad = new AlertDialog.Builder(SignUpActivity.this);
            ad.setMessage("Confirm password does not match Password!");
            ad.setPositiveButton("OK",null);
            ad.show();
        }
    }
}