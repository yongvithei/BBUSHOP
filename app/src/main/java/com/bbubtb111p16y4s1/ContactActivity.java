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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbubtb111p16y4s1.adapters.ContactAdapter;
import com.bbubtb111p16y4s1.models.ContactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactModel> lstContact = new ArrayList<ContactModel>();
    RequestQueue queue;
    ListView LV;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);

        String strUri = getString(R.string.AppURL).toString()+ "get_contact.php";
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("contacts");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject obj =jsonArray.getJSONObject(i);
                        String strName = obj.getString("contactName");
                        String strPhone = obj.getString("contactNumber");
                        String strImage = getText(R.string.ImageURL).toString() + obj.getString("contactImage");
                        lstContact.add(new ContactModel(strImage,strName,strPhone));
                    }

                    ContactAdapter adapter = new ContactAdapter(ContactActivity.this,lstContact);
                    LV.setAdapter(adapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new AlertDialog.Builder(ContactActivity.this)
                        .setMessage("Error" + error.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        queue.add(stringRequest);
        LV = findViewById(R.id.LvContacts);
        LV.setOnItemLongClickListener(this);
        LV.setOnItemClickListener(this);


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

        if (img != null && tvname != null && tvphone != null) {
            // Check if the tag and text values are not null before accessing them
            String contactImage = (img.getTag() != null) ? img.getTag().toString() : "";
            String contactName = (tvname.getText() != null) ? tvname.getText().toString() : "";
            String contactPhone = (tvphone.getText() != null) ? tvphone.getText().toString() : "";

            Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
            in.putExtra("CONTACT_IMAGE", contactImage);
            in.putExtra("CONTACT_NAME", contactName);
            in.putExtra("CONTACT_PHONE", contactPhone);
            startActivity(in);
        } else {
            // Handle the case where any of the views are null
        }
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