package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_layout);

        String strName = getIntent().getStringExtra("CONTACT_NAME");
        String strPhone = getIntent().getStringExtra("CONTACT_PHONE");
        String strImage = getIntent().getStringExtra("CONTACT_IMAGE");
        String strEmail = getIntent().getStringExtra("CONTACT_EMAIL");


        ImageView img = findViewById(R.id.imgContactDetail);
        Glide.with(this).load(strImage).into(img);


        TextView tvname = findViewById(R.id.tvContactNameDetail);
        tvname.setText(strName);

        TextView tvphone = findViewById(R.id.tvPhoneDetail);
        tvphone.setText(strPhone);

        TextView tvemail = findViewById(R.id.tvEmailContactDetail);
        tvemail.setText(strEmail);

        getSupportActionBar().setTitle(strName);


    Button btncall = findViewById(R.id.btnCall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent incall = new Intent(Intent.ACTION_CALL);
                incall.setData(Uri.parse("tel:"+strPhone));
                if(ActivityCompat.checkSelfPermission(ContactDetailActivity.this,
                       Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(incall);
            }
        });

    }
}