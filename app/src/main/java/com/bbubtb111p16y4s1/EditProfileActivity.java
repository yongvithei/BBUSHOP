package com.bbubtb111p16y4s1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bbubtb111p16y4s1.functions.ConvertImage;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bbubtb111p16y4s1.functions.Sessions;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText txtFullName,txtUserName,txtEmail;
    CircularImageView imgProfilePhoto;
    Button btnSaveChange;
    Sessions sessions;
    ImageButton btnBrowse;
    Bitmap bitmap;
    ProgressBarDialog dialog;
    boolean isChange = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessions=new Sessions(this);
        setContentView(R.layout.activity_edit_profile);
        txtFullName=findViewById(R.id.txtEditFullName);
        txtFullName.setText(sessions.getUserFullName());
        txtUserName=findViewById(R.id.txtEditUserName);
        txtUserName.setText(sessions.getUserName());
        txtEmail=findViewById(R.id.txtEditEmail);
        txtEmail.setText(sessions.getUserEmail());

        btnSaveChange = findViewById(R.id.btnSaveChange);
        btnSaveChange.setOnClickListener(this);

        bitmap= ConvertImage.StringToImage(sessions.getUserImage());
        imgProfilePhoto=findViewById(R.id.imgEditProfilePhoto);
        imgProfilePhoto.setImageBitmap(bitmap);

        btnBrowse=findViewById(R.id.btnBrowsePhoto);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options ={"From Gallery","Take a photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(EditProfileActivity.this);
                ad.setTitle("Please choose");
                ad.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(options[which].equals("From Gallery")){
                            ShowGallery(); //call the method
                        }else {
                            ShowCamera(); //call the method
                        }
                    }
                });
                ad.show();
            }
        });
    }
    ActivityResultLauncher<Intent> takePhoto= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                        try {
                            Intent data = result.getData();
                            bitmap = (Bitmap) data.getExtras().get("data");
                            imgProfilePhoto.setImageBitmap(bitmap);
                            isChange=true;
                        }catch (Exception ex){
                            ex.printStackTrace();

                        }
                    }
                }
            }
    );
    private void ShowCamera() {
        Intent camera = new Intent();
        camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhoto.launch(camera);

    }
    ActivityResultLauncher<Intent> gallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                        try {
                            Intent data = result.getData();
                            Uri uri= data.getData();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgProfilePhoto.setImageBitmap(bitmap);
                            isChange = true;
                        }catch (Exception ex){
                            ex.printStackTrace();

                        }
                    }
                }
            }
    );
    private void ShowGallery() {
        Intent gal = new Intent();
        gal.setType("image/*");
        gal.setAction(Intent.ACTION_GET_CONTENT);
        gallery.launch(gal);
        
    }

    @Override
    public void onClick(View view) {
        String strfullname = txtFullName.getText().toString();
        String strusername = txtUserName.getText().toString().trim();
        String stremail = txtEmail.getText().toString();

        String strId= String.valueOf(sessions.getUserID());
            if(TextUtils.isEmpty(strfullname)){
                txtFullName.setError("Required Name");
                txtFullName.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(strusername)){
                txtUserName.setError("Required UserName");
                txtUserName.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(stremail)){
                txtEmail.setError("Required Email");
                txtEmail.requestFocus();
                return;
            }

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {
                    //preExecute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ProgressBarDialog(EditProfileActivity.this);
                            dialog.setMessage(("Updating"));
                            dialog.show();
                        }
                    });
                    //doInBackground
                    String strimage= "NoChange";
                    if(isChange==true){
                        strimage= ConvertImage.ImageToString(bitmap);
                    }
                    String strUri=getText(R.string.AppURL).toString()+ "edit_user_profile.php";
                    String[] params= {"UserFullName","UserName","UserEmail","UserImage","UserID"};
                    String[] values = {strfullname, strusername, stremail, strimage ,strId };
                    RequestHelper update= new RequestHelper();
                    String result = update.Execute(strUri,params,values);
                    //postExecute
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.close();
                           // new AlertDialog.Builder(EditProfileActivity.this).setMessage(result).show();
                            try{
                                JSONObject object = new JSONObject(result);
                                if(object.getInt("success")==1){
                                    //update user info in session

                                    sessions.setUserFullName(object.getString("UserFullName"));
                                    sessions.setUserEmail(object.getString("UserEmail"));
                                    sessions.setUserImage(object.getString("UserName"));
                                    if(object.getString("UserImage") != "NoChange"){
                                        sessions.setUserImage(object.getString("UserImage"));
                                    }
                                    Toast.makeText(EditProfileActivity.this,
                                            object.getString("msg_success"), Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(EditProfileActivity.this,
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