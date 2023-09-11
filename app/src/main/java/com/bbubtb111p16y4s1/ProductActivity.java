package com.bbubtb111p16y4s1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.bbubtb111p16y4s1.adapters.ItemAdapter;
import com.bbubtb111p16y4s1.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    List<ItemModel> lstItem = new ArrayList<ItemModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);

        lstItem.add(new ItemModel(R.drawable.abc_beer,"ABC Beer",25.5,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.coca_cola,"Coca Cola",10.2,"Out of Stock"));
        lstItem.add(new ItemModel(R.drawable.anchor_beer,"Anchor Beer",11.3,"In Stock"));
        lstItem.add(new ItemModel(R.drawable.tiger_beer,"Tiger Beer",17.4,"Out of Stock"));

        ItemAdapter adapter = new ItemAdapter(ProductActivity.this,R.layout.item_model,lstItem);
        GridView Gv = findViewById(R.id.GvProducts);
        Gv.setAdapter(adapter);
    }
}