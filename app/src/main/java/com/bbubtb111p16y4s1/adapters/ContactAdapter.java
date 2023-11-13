package com.bbubtb111p16y4s1.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbubtb111p16y4s1.R;
import com.bbubtb111p16y4s1.models.ContactModel;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    // data members
    private Context context;
    private List<ContactModel> lstContact;
    // constructor
    public ContactAdapter(Context context, List<ContactModel> lst){
        this.context = context;
        this.lstContact = lst;
    }
    // getter methods
    @Override
    public int getCount() {
        return lstContact.size();
    }

    @Override
    public Object getItem(int position) {
        // ContactModel contact = lstContact.get(position);
        // return contact;
        return lstContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.contact_model, null);

        ContactModel contact = lstContact.get(position);
        CircularImageView img = v.findViewById(R.id.imgContactModel);
//        img.setImageResource(contact.getImageID());
//        img.setTag(String.valueOf(contact.getImageID()));

        Glide.with(context).load(contact.getImageURL()).into(img);
        TextView tvname = v.findViewById(R.id.tvContactModel);
        tvname.setText(contact.getContactName());

        TextView tvphone = v.findViewById(R.id.tvPhoneModel);
        tvphone.setText(contact.getPhoneNumber());

        return v;
    }
}
