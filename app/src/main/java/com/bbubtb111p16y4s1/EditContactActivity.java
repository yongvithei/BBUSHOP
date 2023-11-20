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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbubtb111p16y4s1.functions.ConvertImage;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnBrowse;
    Bitmap bitmap;
    CircularImageView imgContactPhoto;
    boolean isChange = false;
    Button btnUpdate;
    ProgressBarDialog dialog;
    TextInputEditText txtContactName,txtContactNumber,txtContactEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact_layout);

        String strName = getIntent().getStringExtra("CONTACT_NAME");
        String strPhone = getIntent().getStringExtra("CONTACT_PHONE");
        String strImage = getIntent().getStringExtra("CONTACT_IMAGE");
        String strEmail = getIntent().getStringExtra("CONTACT_EMAIL");

        imgContactPhoto = findViewById(R.id.imgContactPhoto);
        Glide.with(this).load(strImage).into(imgContactPhoto);

        txtContactName = findViewById(R.id.txtEditContactName);
        txtContactName.setText(strName);

        txtContactNumber = findViewById(R.id.txtEditPhoneNumber);
        txtContactNumber.setText(strPhone);

        txtContactEmail = findViewById(R.id.txtEditContactEmail);
        txtContactEmail.setText(strEmail);




        btnBrowse=findViewById(R.id.btnBrowsePhoto);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options ={"From Gallery","Take a photo"};
                AlertDialog.Builder ad = new AlertDialog.Builder(EditContactActivity.this);
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
                            imgContactPhoto.setImageBitmap(bitmap);
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
                            imgContactPhoto.setImageBitmap(bitmap);
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
        String strName = txtContactName.getText().toString();
        String strNumber = txtContactNumber.getText().toString().trim();
        String strEmail = txtContactEmail.getText().toString();

        String strId = getIntent().getStringExtra("CONTACT_ID");

        if(TextUtils.isEmpty(strName)){
            txtContactName.setError("Required Name");
            txtContactName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strNumber)){
            txtContactNumber.setError("Required Phone Number");
            txtContactNumber.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(strEmail)){
            txtContactEmail.setError("Required Email");
            txtContactEmail.requestFocus();
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
                        dialog = new ProgressBarDialog(EditContactActivity.this);
                        dialog.setMessage(("Updating"));
                        dialog.show();
                    }
                });
                //doInBackground
                String strimage= "NoChange";
                if(isChange==true){
                    strimage= ConvertImage.ImageToString(bitmap);
                }
                String strUri=getText(R.string.AppURL).toString()+ "edit_contact.php";
                String[] params= {"ContactName","ContactPhone","ContactEmail","ContactImage","ContactID"};
                String[] values = {strName, strNumber, strEmail, strimage ,strId };
                RequestHelper update= new RequestHelper();
                String result = update.Execute(strUri,params,values);
                //postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.close();
                        // new AlertDialog.Builder(EditContactActivity.this).setMessage(result).show();
                        try{
                            JSONObject object = new JSONObject(result);
                            if(object.getInt("success")==1){

                                Toast.makeText(EditContactActivity.this,
                                        object.getString("msg_success"), Toast.LENGTH_LONG).show();
                                // go to
                                Intent intent = new Intent(EditContactActivity.this,ContactActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(EditContactActivity.this,
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