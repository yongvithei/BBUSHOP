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

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText txtName,txtPhone,txtEmail;
    CircularImageView imgPhoto;
    Button btnCreate;
    ImageButton btnBrowse;
    Bitmap bitmap;
    Sessions sessions;
    ProgressBarDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_layout);
        txtName = findViewById(R.id.txtEditContactName);
        txtPhone = findViewById(R.id.txtEditPhoneNumber);
        txtEmail = findViewById(R.id.txtEditContactEmail);
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
        bitmap = ConvertImage.StringToImage(null);


        imgPhoto=findViewById(R.id.imgContactPhoto);


        btnBrowse=findViewById(R.id.btnBrowsePhoto);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options ={"From Gallery","Take a photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(AddContactActivity.this);
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
                            imgPhoto.setImageBitmap(bitmap);
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
                            imgPhoto.setImageBitmap(bitmap);
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
        String strContactName = txtName.getText().toString();
        String strPhoneNumber = txtPhone.getText().toString().trim();
        String strEmail = txtEmail.getText().toString();
        String strimage= ConvertImage.ImageToString(bitmap);
        if(TextUtils.isEmpty(strContactName)){
            txtName.setError("Required Name");
            txtName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strPhoneNumber)){
            txtPhone.setError("Required Phone");
            txtPhone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strEmail)){
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
                        dialog = new ProgressBarDialog(AddContactActivity.this);
                        dialog.setMessage(("Create"));
                        dialog.show();
                    }
                });
                //doInBackground
                String strUri=getText(R.string.AppURL).toString()+ "create_contact.php";
                String[] params= {"ContactName","UserPhone","UserEmail","UserImage"};
                String[] values = {strContactName, strPhoneNumber, strEmail, strimage };
                RequestHelper update= new RequestHelper();
                String result = update.Execute(strUri,params,values);
                //postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.close();
                         //new AlertDialog.Builder(AddContactActivity.this).setMessage(result).show();
                        try{
                            JSONObject object = new JSONObject(result);
                            if(object.getInt("success")==1){


                                Toast.makeText(AddContactActivity.this,
                                        object.getString("msg_success"), Toast.LENGTH_LONG).show();

                                // go to
                                Intent intent = new Intent(AddContactActivity.this,ContactActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(AddContactActivity.this,
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