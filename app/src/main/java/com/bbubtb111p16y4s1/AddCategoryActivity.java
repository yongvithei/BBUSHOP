package com.bbubtb111p16y4s1;

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
import com.bbubtb111p16y4s1.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText txtCategoryName, txtDescription;
    Button btnsavecategory;
    ProgressBarDialog dialog;
    String result;
    Sessions sessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category_layout);
        sessions = new Sessions(this);
        txtCategoryName = findViewById(R.id.txtCategoryName);
        txtDescription = findViewById(R.id.txtDescription);
        btnsavecategory = findViewById(R.id.btnSaveCategory);
        btnsavecategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String strCategoryName = txtCategoryName.getText().toString().trim();
        String strDescription = txtDescription.getText().toString().trim();

        if(TextUtils.isEmpty(strCategoryName)){
            txtCategoryName.setError("Required Category Name!");
            txtCategoryName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strDescription)){
            txtDescription.setError("Required Description!");
            txtDescription.requestFocus();
            return;

        }
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

            service.execute(new Runnable() {
                @Override
                public void run() {
                    //pre execute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ProgressBarDialog(AddCategoryActivity.this);
                            dialog.setMessage("Logging in...");
                            dialog.show();
                        }
                    });
                    //doInBackground
                    String strUri = getText(R.string.AppURL)+ "create_category.php";
                    String[] params={"CategoryName","Description","CreateBy"};
                    String[] values={strCategoryName,strDescription,sessions.getUserName()};
                    RequestHelper create=new RequestHelper();
                    result = create.Execute(strUri,params,values);
                    //post execute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.close();
                                    try {
                                        JSONObject object = new JSONObject(result.toString());
                                        if(object.getInt("success")==1){
                                            Toast.makeText(AddCategoryActivity.this,
                                                    object.getString("msg_success"),
                                                    Toast.LENGTH_LONG).show();
                                            // go to login
                                            Intent intent = new Intent(AddCategoryActivity.this,AddCategoryActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(AddCategoryActivity.this,
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

    }
}