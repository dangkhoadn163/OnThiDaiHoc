package com.example.msi.onthidaihoc.CustomDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.onthidaihoc.Activity.ChooseActivity;
import com.example.msi.onthidaihoc.Activity.LauchActivity;
import com.example.msi.onthidaihoc.Fragment.FragmentMain;
import com.example.msi.onthidaihoc.R;


import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DK on 11/26/2017.
 */

public class LogoutDialog {
    public View view;
    public AlertDialog.Builder builder;
    public AlertDialog dialog;
    public Context context;
    public TextView txvTitle, txvClose, txvConfirm;
    private int i;
    public LogoutDialog(final Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        this.builder = new AlertDialog.Builder(context);
        this.txvTitle = (TextView) view.findViewById(R.id.dialog_logout_title);
        this.txvClose = (TextView) view.findViewById(R.id.dialog_logout_cancel);
        this.txvConfirm=(TextView)view.findViewById(R.id.dialog_logout_confirm);
        this.txvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ghi();
                Intent intent = new Intent(context, LauchActivity.class);
                context.startActivity(intent);

            }
        });
    }
    public LogoutDialog(){}
/*    public int getCheck()
    {
        return i;
    }*/
    public void ghi(){
        SharedPreferences ghi= context.getSharedPreferences("save", context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("code","");
        Toast.makeText(context, "Toast ghi", Toast.LENGTH_SHORT).show();
        editor.commit();
    }
    public void doc(){
        SharedPreferences lay=PreferenceManager.getDefaultSharedPreferences(context);
        Toast.makeText(context, "Logout: " + lay.getString("code", ""), Toast.LENGTH_SHORT).show();
    }
    public void setTitle(CharSequence title) {
        if (title != null)
            txvTitle.setText(title);
    }

    public void show() {
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

    }

    public void dismiss() {
        dialog.dismiss();
    }
}
