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

public class ContactDetailActivity extends AppCompatActivity {
    String strPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_layout);
        String strName = getIntent().getStringExtra("CONTACT_NAME");
        strPhone = getIntent().getStringExtra("CONTACT_PHONE");
        int imgID = Integer.parseInt(getIntent().getStringExtra("CONTACT_IMAGE"));

        ImageView img = findViewById(R.id.imgContactDetail);
        img.setImageResource(imgID);

        TextView tvname = findViewById(R.id.tvContactNameDetail);
        tvname.setText(strName);

        TextView tvphone = findViewById(R.id.tvPhoneDetail);
        tvphone.setText(strPhone);
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