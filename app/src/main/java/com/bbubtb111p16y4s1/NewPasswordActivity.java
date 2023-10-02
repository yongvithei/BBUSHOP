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

import com.bbubtb111p16y4s1.functions.EncryptPassword;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bbubtb111p16y4s1.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave;
    TextInputEditText txtNewPassword,txtConfirmPassword;
    ProgressBarDialog dialog;
    Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password_layout);
        sessions = new Sessions(this);
        txtNewPassword = findViewById(R.id.txtNewPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSave = findViewById(R.id.btnSaveChanges);
        btnSave.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        String strNewPassword = txtNewPassword.getText().toString().trim();
        String strConfirmPassword = txtConfirmPassword.getText().toString().trim();
        String strPasswordHash = EncryptPassword.MD5(strNewPassword);
        String strId= String.valueOf(sessions.getUserID());
        if(TextUtils.isEmpty(strNewPassword)){
            txtNewPassword.setError("Required UserName!");
            txtNewPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strConfirmPassword)){
            txtConfirmPassword.setError("Required UserName!");
            txtConfirmPassword.requestFocus();
            return;
        }
        if(strNewPassword.equals(strConfirmPassword)){
            ExecutorService service = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            service.execute(new Runnable() {
                @Override
                public void run() {
                    //preExecute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ProgressBarDialog(NewPasswordActivity.this);
                            dialog.setMessage("Loading...");
                            dialog.show();
                        }
                    });
                    //doInBackground
                    String strUri=getText(R.string.AppURL).toString()+ "update_password.php";
                    String[] params= {"UserPassword","UserID"};
                    String[] values = {strPasswordHash ,strId };
                    RequestHelper update= new RequestHelper();
                    String result = update.Execute(strUri,params,values);
                    //postExecute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.close();
                            // new AlertDialog.Builder(NewPasswordActivity.this).setMessage(result).show();
                            try{
                                JSONObject object = new JSONObject(result);
                                if(object.getInt("success")==1){
                                    Toast.makeText(NewPasswordActivity.this,
                                            object.getString("msg_success"),
                                            Toast.LENGTH_LONG).show();
                                    //main
                                    Intent intent = new Intent(NewPasswordActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(NewPasswordActivity.this,
                                            object.getString("msg_errors"), Toast.LENGTH_LONG).show();
                                }

                            }catch (JSONException e){
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            });




        }

    }
}