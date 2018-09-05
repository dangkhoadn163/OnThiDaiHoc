package com.example.msi.onthidaihoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.Class.MD5;
import com.example.msi.onthidaihoc.CustomDialog.LogoutDialog;
import com.example.msi.onthidaihoc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class LauchActivity extends AppCompatActivity {
    static EditText edtemailuser;
    String iduser,code,email,pass,rememberemail,remembercode,rememberpass;
    EditText edtpassuser;
    String encodepass;
    int dk=1;
    Button btnlogin,btnregister,btnforgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch);
        edtemailuser=(EditText)findViewById(R.id.emailuser);
        edtpassuser=(EditText)findViewById(R.id.passuser);
        btnlogin=(Button)findViewById(R.id.loginuser);
        btnregister=(Button)findViewById(R.id.registeruser);
        // đọc lại từ logoutdialog
        doc();
/*        LogoutDialog lg = new LogoutDialog();
        dk =  lg.getCheck();*/

        //-------------------------------------------//
/*        try {
           // dk = getIntent().getExtras().getInt("dieukien");
        }catch (Exception e){

            Log.d("loi",e + "");
        }
        Log.d("dk",dk + "");
        //------------------------------------------//
        if(dk==0){
            dk=1;
            ghi(rememberemail, "");
            doc();
        }*/
        //-----------------------------------------//
        edtemailuser.setText(rememberemail);
        edtpassuser.setText(remembercode);
        Log.d("asd",""+remembercode+rememberemail);
        if(!edtemailuser.getText().toString().equals("")&&!edtpassuser.getText().toString().equals("")){
            String ten_user = edtemailuser.getText().toString();
            String matkhau_user = edtpassuser.getText().toString();
            String type = "login";
            BackgroundWoker backgroundWoker = new BackgroundWoker(LauchActivity.this, new BackgroundWoker.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    iduser=output;
                    Toast.makeText(LauchActivity.this, ""+output, Toast.LENGTH_SHORT).show();
                    changeActive();
                }
            });
            backgroundWoker.execute(type, ten_user, matkhau_user);

        }
        click();

    }
    public void encodepass(){
        MD5 md5 = new MD5();
        if(edtpassuser.getText().toString().equals("")){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();

        }else{
            encodepass=md5.encryption(edtpassuser.getText().toString());
            Log.d("encode",""+encodepass);
        }

    }
    public void click(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtemailuser.getText().toString().equals("")||edtpassuser.getText().toString().equals("")){
                    Toast.makeText(LauchActivity.this, "Vui lòng nhập đầy đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    encodepass();
                    String ten_user = edtemailuser.getText().toString();
                    String matkhau_user = encodepass;
                    String type = "login";
                    BackgroundWoker backgroundWoker = new BackgroundWoker(LauchActivity.this, new BackgroundWoker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            iduser=output;
                            Toast.makeText(LauchActivity.this, ""+output, Toast.LENGTH_SHORT).show();
                            changeActive();
                        }
                    });
                    backgroundWoker.execute(type, ten_user, matkhau_user);

                }
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LauchActivity.this,RegisterActivity.class);
                LauchActivity.this.startActivity(i);
            }
        });
    }
    public void changeActive(){
        if(!iduser.equals("login not success")){
            getemailpass();


            Intent i= new Intent(LauchActivity.this,ChooseActivity.class);
            i.putExtra("iduser",iduser);
            LauchActivity.this.startActivity(i);

        }
        else {
            Toast.makeText(this, "not sucess! ", Toast.LENGTH_SHORT).show();
        }


    }
    //ghi lại email và encodepass đề lưu đăng nhập
    public void ghi(String emailstr, String passstr){
        SharedPreferences ghi=getSharedPreferences("save",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("email",emailstr);
        editor.putString("code", passstr);
        editor.commit();
    }
    //đọc email và encode sau khi đăng nhập
    public void doc(){
        SharedPreferences lay = getSharedPreferences("save", MODE_PRIVATE);
         rememberemail=lay.getString("email", "");
         remembercode=lay.getString("code", "");
        Toast.makeText(this, ""+remembercode+"o.O"+rememberemail, Toast.LENGTH_SHORT).show();
    }
    public void getemailpass(){

        String type = "remember";
        BackgroundWoker backgroundWoker = new BackgroundWoker(LauchActivity.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                code=output;
                emailpass();
            }
        });
        backgroundWoker.execute(type,iduser);
    }
    public void emailpass(){
        for (int i = 0; i < code.length(); i++) {
            if (code != null) {
                try {
                    JSONArray jsonArray = new JSONArray(code);
                    JSONObject index = jsonArray.getJSONObject(i);
                    email = index.getString("Email");
                    pass = index.getString("Code");
                    Log.d("emailpass",""+email+pass);
                    ghi(email, pass);
                    doc();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(LauchActivity.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
