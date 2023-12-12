package com.bbubtb111p16y4s1;

import static com.bbubtb111p16y4s1.R.id.LvContacts;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbubtb111p16y4s1.adapters.ContactAdapter;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bbubtb111p16y4s1.models.ContactModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    List<ContactModel> lstContact = new ArrayList<ContactModel>();
    RequestQueue queue;
    ListView LV;
    Button btnAdd;
    ProgressBarDialog dialog;
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
                    // Clear the existing data before adding new data
                    lstContact.clear();
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("contacts");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject obj =jsonArray.getJSONObject(i);
                        String strId = obj.getString("contactID");
                        String strName = obj.getString("contactName");
                        String strPhone = obj.getString("contactNumber");
                        String strEmail = obj.getString("contactEmail");
                        String strImage = getText(R.string.ImageURL).toString() + obj.getString("contactImage");
                        lstContact.add(new ContactModel(strId,strImage,strName,strPhone, strEmail));
                    }

                    // Notify the adapter that the underlying data has changed
                    if (LV.getAdapter() == null) {
                        ContactAdapter adapter = new ContactAdapter(ContactActivity.this, lstContact);
                        LV.setAdapter(adapter);
                    } else {
                        ((ContactAdapter) LV.getAdapter()).notifyDataSetChanged();
                    }

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
        LV = findViewById(LvContacts);
        LV.setOnItemLongClickListener(this);
        LV.setOnItemClickListener(this);


        // Find the button by its ID
        Button btnAdd = findViewById(R.id.btnAddPro);
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
        TextView tvemail = view.findViewById(R.id.tvEmail);

        if (img != null && tvname != null && tvphone != null) {
            // Check if the tag and text values are not null before accessing them
            String contactImage = (img.getTag() != null) ? img.getTag().toString() : "";
            String contactName = (tvname.getText() != null) ? tvname.getText().toString() : "";
            String contactPhone = (tvphone.getText() != null) ? tvphone.getText().toString() : "";

            Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
            in.putExtra("CONTACT_IMAGE", contactImage);
            in.putExtra("CONTACT_NAME", contactName);
            in.putExtra("CONTACT_PHONE", contactPhone);
            in.putExtra("CONTACT_EMAIL", tvemail.getText().toString());
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
                    TextView tvemail = view.findViewById(R.id.tvEmail);

                    Intent in = new Intent(ContactActivity.this, ContactDetailActivity.class);
                    in.putExtra("CONTACT_IMAGE", img.getTag().toString());
                    in.putExtra("CONTACT_NAME", tvname.getText().toString());
                    in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
                    in.putExtra("CONTACT_EMAIL", tvemail.getText().toString());
                    startActivity(in);
                }else if(actions[which].equals("Add New")){
                    Intent intent = new Intent(ContactActivity.this, AddContactActivity.class);
                    startActivity(intent);
                } else if (actions[which].equals("Edit")) {
                    TextView tvId = view.findViewById(R.id.tvId);
                    ImageView img = view.findViewById(R.id.imgContactModel);
                    TextView tvname = view.findViewById(R.id.tvContactModel);
                    TextView tvphone = view.findViewById(R.id.tvPhoneModel);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);

                    Intent in = new Intent(ContactActivity.this, EditContactActivity.class);
                    in.putExtra("CONTACT_ID", tvId.getText().toString());
                    in.putExtra("CONTACT_IMAGE", img.getTag().toString());
                    in.putExtra("CONTACT_NAME", tvname.getText().toString());
                    in.putExtra("CONTACT_PHONE", tvphone.getText().toString());
                    in.putExtra("CONTACT_EMAIL", tvEmail.getText().toString());
                    startActivity(in);
                } else if (actions[which].equals("Delete")) {
                    final ProgressBarDialog[] dialogHolder = new ProgressBarDialog[1];
                    TextView tvId = view.findViewById(R.id.tvId);
                    String contactId = tvId.getText().toString();
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            // preExecute
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogHolder[0] = new ProgressBarDialog(ContactActivity.this);
                                    dialogHolder[0].setMessage(("Deleting"));
                                    dialogHolder[0].show();
                                }
                            });
                            // doInBackground
                            String strUri = getText(R.string.AppURL).toString() + "delete_contact.php";
                            String[] params = {"ContactID"};
                            String[] values = {contactId};
                            RequestHelper delete = new RequestHelper();
                            String result = delete.Execute(strUri, params, values);
                            // postExecute
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogHolder[0].close();

                                    try {
                                        JSONObject object = new JSONObject(result);
                                        if (object.getInt("success") == 1) {
                                            // Contact deleted successfully
                                            Toast.makeText(ContactActivity.this, "Contact deleted successfully", Toast.LENGTH_SHORT).show();
                                            // Optionally, update your UI or perform other actions
                                            // Update the adapter with the new list of contacts
                                            // Assuming lstContact is a class variable
                                            lstContact.remove(position);
                                            ((ContactAdapter) LV.getAdapter()).notifyDataSetChanged();
                                        } else {
                                            // Error deleting contact
                                            Toast.makeText(ContactActivity.this, "Error deleting contact", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                } else if (actions[which].equals("Add to Favorites")) {
                    final ProgressBarDialog[] dialogHolder = new ProgressBarDialog[1];
                    TextView tvId = view.findViewById(R.id.tvId);
                    String contactId = tvId.getText().toString();
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            // preExecute
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogHolder[0] = new ProgressBarDialog(ContactActivity.this);
                                    dialogHolder[0].setMessage(("Deleting"));
                                    dialogHolder[0].show();
                                }
                            });
                            // doInBackground
                            String strUri = getText(R.string.AppURL).toString() + "add_favorite.php";
                            String[] params = {"ContactAddFavorite","ContactID"};
                            String[] values = {"1",contactId};
                            RequestHelper delete = new RequestHelper();
                            String result = delete.Execute(strUri, params, values);
                            // postExecute
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialogHolder[0].close();

                                    try {
                                        JSONObject object = new JSONObject(result);
                                        if (object.getInt("success") == 1) {
                                            // Contact deleted successfully
                                            Toast.makeText(ContactActivity.this, "Contact Add To Favorite successfully", Toast.LENGTH_SHORT).show();
                                            // Optionally, update your UI or perform other actions
                                            // Update the adapter with the new list of contacts
                                            // Assuming lstContact is a class variable

                                        } else {
                                            // Error deleting contact
                                            Toast.makeText(ContactActivity.this, "Error contact Add To Favorite", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });
        ad.show();
        return true;
    }
}