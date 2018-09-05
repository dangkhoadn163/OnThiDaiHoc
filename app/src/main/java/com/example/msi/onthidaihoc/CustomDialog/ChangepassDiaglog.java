package com.example.msi.onthidaihoc.CustomDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.onthidaihoc.Activity.ChooseActivity;
import com.example.msi.onthidaihoc.Activity.LauchActivity;
import com.example.msi.onthidaihoc.Activity.RegisterActivity;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.Class.MD5;
import com.example.msi.onthidaihoc.R;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DK on 11/26/2017.
 */

public class ChangepassDiaglog {
    public View view;
    String info,uid;
    public AlertDialog.Builder builder;
    public AlertDialog dialog;
    public Context context;
    public TextView txvTitle, txvClose, txvConfirm;
    public EditText edtnewpass,edtconfirm;
    String encodepassword;


    public ChangepassDiaglog(final Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_changepass, null);
        this.builder = new AlertDialog.Builder(context);
        this.txvTitle = (TextView) view.findViewById(R.id.dialog_change_title);
        this.edtnewpass = (EditText) view.findViewById(R.id.edt_newpass);
        this.edtconfirm = (EditText) view.findViewById(R.id.edt_confirm);
        this.txvClose = (TextView) view.findViewById(R.id.dialog_change_cancel);
        this.txvConfirm=(TextView)view.findViewById(R.id.dialog_change_confirm);
        getuid();
        this.txvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtnewpass.getText().toString().equals(edtconfirm.getText().toString()) ) {

                    MD5 md5 = new MD5();
                    if(edtnewpass.getText().equals("")||edtconfirm.getText().equals("")){
                        Toast.makeText(context, "Vui lòng nhập đầy dủ thông tin!", Toast.LENGTH_SHORT).show();
                    }else {
                        encodepassword=md5.encryption(edtnewpass.getText().toString());
                        Log.d("encode",""+encodepassword);
                    }

                    String pass = encodepassword;
                    String type ="changepass";
                    BackgroundWoker backgroundWoker = new BackgroundWoker(context, new BackgroundWoker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Toast.makeText(context, ""+output, Toast.LENGTH_SHORT).show();
                        }
                    });
                    backgroundWoker.execute(type, uid, pass);
                    ghi();
                    Intent intent = new Intent(context, LauchActivity.class);
                    context.startActivity(intent);
                }
                else {
                    Log.d("loooooooooooog","looooooooooi");
                }
            }
        });
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
    public void ghi(){
        SharedPreferences ghi= context.getSharedPreferences("save", context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("code","");
        Toast.makeText(context, "Toast ghi", Toast.LENGTH_SHORT).show();
        editor.commit();
    }
    public void getuid(){
        SharedPreferences setuid = context.getSharedPreferences("uid", MODE_PRIVATE);
        uid=setuid.getString("uid", "");
        Toast.makeText(context, "user ID: "+uid, Toast.LENGTH_SHORT).show();
    }
}
