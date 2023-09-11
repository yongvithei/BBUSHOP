package com.bbubtb111p16y4s1.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bbubtb111p16y4s1.R;
import com.bbubtb111p16y4s1.models.ItemModel;

import java.text.DecimalFormat;
import java.util.List;

public class ItemAdapter extends ArrayAdapter {
    // data members
    private Context context;
    private List<ItemModel> lstItem;
    // constructor
    public ItemAdapter(@NonNull Context context, int resource, List<ItemModel> lst) {
        super(context, resource, lst);
        this.context = context;
        this.lstItem = lst;
    }
    // getter methods
    @Override
    public int getCount() {
        return lstItem.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.item_model, null);
        ItemModel item = lstItem.get(position);

        ImageView img = v.findViewById(R.id.imgItemModel);
        img.setImageResource(item.getImageID());
        img.setTag(String.valueOf(item.getImageID()));

        TextView tvname = v.findViewById(R.id.tvItemNameModel);
        tvname.setText(item.getItemName());

        DecimalFormat format = new DecimalFormat("#,##0.00");
        TextView tvunitprice = v.findViewById(R.id.tvItemPriceModel);
        tvunitprice.setText("$ " + format.format(item.getUnitPrice()));

        TextView tvstatus = v.findViewById(R.id.tvStatusModel);
        if(item.getStatus().equals("In Stock")) {
            tvstatus.setText(item.getStatus());
            tvstatus.setTextColor(Color.GREEN);
        }else{
            tvstatus.setText(item.getStatus());
            tvstatus.setTextColor(Color.RED);
        }

        return v;
    }
}
