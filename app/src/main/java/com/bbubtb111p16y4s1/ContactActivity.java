package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bbubtb111p16y4s1.adapters.ContactAdapter;
import com.bbubtb111p16y4s1.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactModel> lstContact = new ArrayList<ContactModel>();
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        lstContact.add(new ContactModel(R.drawable.icon_contact_72,"Heng Dara","012345678"));
        lstContact.add(new ContactModel(R.drawable.icon_group_72,"Phal Samnang","012345688"));
        lstContact.add(new ContactModel(R.drawable.icon_category_72,"Samphors","012345677"));
        lstContact.add(new ContactModel(R.drawable.icon_settings_72,"Sean Sann","012345697"));

        ContactAdapter adapter = new ContactAdapter(ContactActivity.this,lstContact);
        ListView LV = findViewById(R.id.LvContacts);
        LV.setAdapter(adapter);
        LV.setOnItemClickListener(this);
        LV.setOnItemLongClickListener(this);

        // Find the button by its ID
        Button btnAdd = findViewById(R.id.btnAdd);
        // Set an OnClickListener for the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to SecondActivity
                Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView img = view.findViewById(R.id.imgContactModel);
        TextView tvname = view.findViewById(R.id.tvContactModel);
        TextView tvphone = view.findViewById(R.id.tvPhoneModel);

        Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
        in.putExtra("CONTACT_IMAGE", img.getTag().toString());
        in.putExtra("CONTACT_NAME", tvname.getText().toString());
        in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
        startActivity(in);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String[] actions = {"Add New","Edit","Delete","View","Add to Favorites"};
        AlertDialog.Builder ad = new AlertDialog.Builder(ContactActivity.this);
        ad.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(actions[which].equals("View")){
                    ImageView img = view.findViewById(R.id.imgContactModel);
                    TextView tvname = view.findViewById(R.id.tvContactModel);
                    TextView tvphone = view.findViewById(R.id.tvPhoneModel);

                    Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
                    in.putExtra("CONTACT_IMAGE", img.getTag().toString());
                    in.putExtra("CONTACT_NAME", tvname.getText().toString());
                    in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
                    startActivity(in);
                }
            }
        });
        ad.show();
        return true;
    }
}