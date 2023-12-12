package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bbubtb111p16y4s1.functions.ProgressBarDialog;
import com.bbubtb111p16y4s1.functions.RequestHelper;
import com.bbubtb111p16y4s1.models.Category;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewProductActivity extends AppCompatActivity implements View.OnClickListener {
    RequestQueue queue;
    List<Category> categiryList = new ArrayList<Category>();
    Spinner spCategory;
    Button btnSaveProduct;
    ProgressBarDialog dialog;
    TextInputEditText txtProductName,txtBarcode,txtQTY,txtUnitPriceIn,txtUnitPriceOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_layout);
        ShowCategories();
        spCategory = findViewById(R.id.spSpinnerCategory);
        txtProductName = findViewById(R.id.tvProductName);
        txtBarcode = findViewById(R.id.tvBarcode);
        txtQTY = findViewById(R.id.tvQTY);
        txtUnitPriceIn = findViewById(R.id.tvUnitPriceIn);
        txtUnitPriceOut = findViewById(R.id.tvUnitPriceOut);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);
        btnSaveProduct.setOnClickListener(this);
    }
    private void ShowCategories(){
        String url =getText(R.string.AppURL).toString()+ "show_category.php";
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("categories");
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject obj =  jsonArray.getJSONObject(i);
                        String cName = obj.getString("CategoryName");
                        int id = obj.getInt("CategoryID");
                        categiryList.add(new Category(id, cName));
                    }
                    ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(NewProductActivity.this,
                            android.R.layout.simple_dropdown_item_1line,categiryList);
                            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                            spCategory.setAdapter(adapter)  ;

                }catch (JSONException e){
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewProductActivity.this,"Error: "+ error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    public void onClick(View v){
        Category cate = (Category) spCategory.getSelectedItem();
        int Catid = cate.getCategoryID();
        String strCatID = String.valueOf(Catid);
        String strProductName = txtProductName.getText().toString();
        String strBarcode = txtBarcode.getText().toString();
        String strQTY = txtQTY.getText().toString();
        String strPriceIn = txtUnitPriceIn.getText().toString();
        String strQTYPriceOut = txtUnitPriceOut.getText().toString();


        // Validation for strProductName
        if (TextUtils.isEmpty(strProductName)) {
            txtProductName.setError("Required Name");
            txtProductName.requestFocus();
            return;
        }

        // Validation for strBarcode
        if (TextUtils.isEmpty(strBarcode)) {
            txtBarcode.setError("Required Barcode");
            txtBarcode.requestFocus();
            return;
        }

        // Validation for strQTY
        if (TextUtils.isEmpty(strQTY)) {
            txtQTY.setError("Required Quantity");
            txtQTY.requestFocus();
            return;
        }
        // Validation for strPriceIn
        if (TextUtils.isEmpty(strPriceIn)) {
            txtUnitPriceIn.setError("Required PriceIn");
            txtUnitPriceIn.requestFocus();
            return;
        }
        // Validation for strQTYPriceOut
        if (TextUtils.isEmpty(strQTYPriceOut)) {
            txtUnitPriceOut.setError("Required QTY PriceOut");
            txtUnitPriceOut.requestFocus();
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
                        dialog = new ProgressBarDialog(NewProductActivity.this);
                        dialog.setMessage(("Create"));
                        dialog.show();
                    }
                });
                //doInBackground
                String strUri=getText(R.string.AppURL).toString()+ "create_product.php";
                String[] params= {"ProductName","CategoryID","Barcode","Qty","UnitPrice","UnitPriceOut"};
                String[] values = {strProductName, strCatID, strBarcode, strQTY, strPriceIn,strQTYPriceOut };
                RequestHelper update= new RequestHelper();
                String result = update.Execute(strUri,params,values);
                //postExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.close();
                        //new AlertDialog.Builder(NewProductActivity.this).setMessage(result).show();
                        try{
                            JSONObject object = new JSONObject(result);
                            if(object.getInt("success")==1){
                                Toast.makeText(NewProductActivity.this,
                                        object.getString("msg_success"), Toast.LENGTH_LONG).show();
                                // go to
                                Intent intent = new Intent(NewProductActivity.this,ProductActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(NewProductActivity.this,
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