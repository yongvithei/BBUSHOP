package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.bbubtb111p16y4s1.adapters.ItemAdapter;
import com.bbubtb111p16y4s1.models.ItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    List<ItemModel> lstItem = new ArrayList<ItemModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        // Find the button by its ID
        FloatingActionButton btnAdd = findViewById(R.id.btnAddPro);
        // Set an OnClickListener for the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to SecondActivity
                Intent intent = new Intent(ProductActivity.this, NewProductActivity.class);
                startActivity(intent);
            }
        });

        lstItem.add(new ItemModel(R.drawable.abc_beer,"ABC Beer",25.5,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.coca_cola,"Coca Cola",10.2,"Out of Stock"));
        lstItem.add(new ItemModel(R.drawable.anchor_beer,"Anchor Beer",11.3,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.tiger_beer,"Tiger Beer",17.4,"Out of Stock"));

        ItemAdapter adapter = new ItemAdapter(ProductActivity.this,R.layout.item_model,lstItem);
        GridView Gv = findViewById(R.id.GvProducts);
        Gv.setAdapter(adapter);


    }
}