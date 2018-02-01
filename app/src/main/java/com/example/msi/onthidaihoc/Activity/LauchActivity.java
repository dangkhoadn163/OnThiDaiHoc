package com.example.msi.onthidaihoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.Class.MD5;
import com.example.msi.onthidaihoc.R;


public class LauchActivity extends AppCompatActivity {
    static EditText edtemailuser;
    String iduser;
    EditText edtpassuser;
    String encodepass;
    Button btnlogin,btnregister,btnforgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lauch);
        edtemailuser=(EditText)findViewById(R.id.emailuser);
        edtpassuser=(EditText)findViewById(R.id.passuser);
        btnlogin=(Button)findViewById(R.id.loginuser);
        btnregister=(Button)findViewById(R.id.registeruser);
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
/*                            Toast.makeText(LauchActivity.this, ""+output, Toast.LENGTH_SHORT).show();*/
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
            Intent i= new Intent(LauchActivity.this,ChooseActivity.class);
            i.putExtra("iduser",iduser);
            LauchActivity.this.startActivity(i);
        }
        else {
            Toast.makeText(this, "not sucess! ", Toast.LENGTH_SHORT).show();
        }


    }

}
