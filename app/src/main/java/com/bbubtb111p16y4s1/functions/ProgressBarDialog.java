package com.bbubtb111p16y4s1.functions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bbubtb111p16y4s1.R;

public class ProgressBarDialog {
    private AlertDialog alertDialog;
    private TextView tvmessage;
    public ProgressBarDialog(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progress_bar_layout, null);
        tvmessage = v.findViewById(R.id.tvDialogMessage);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        alertDialog = dialog.create();
        alertDialog.setView(v);
    }
    public void setMessage(String message){
        tvmessage.setText(message);
    }
    public void show(){
        alertDialog.show();
    }
    public void close(){
        alertDialog.dismiss();
    }
}
