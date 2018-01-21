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
import com.example.msi.onthidaihoc.R;


public class LauchActivity extends AppCompatActivity {
    static EditText edtemailuser;
    String checklogin;
    EditText edtpassuser;
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
    public void click(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtemailuser.getText().toString().equals("")||edtpassuser.getText().toString().equals("")){
                    Toast.makeText(LauchActivity.this, "Vui lòng nhập đầy đủ thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String ten_user = edtemailuser.getText().toString();
                    String matkhau_user = edtpassuser.getText().toString();
                    String type = "login";
                    BackgroundWoker backgroundWoker = new BackgroundWoker(LauchActivity.this, new BackgroundWoker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            checklogin=output;
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
        if(checklogin.equals("login success !!!!! Welcome user")){
            Intent i= new Intent(LauchActivity.this,ListTest.class);
            LauchActivity.this.startActivity(i);
        }
        else {
            Toast.makeText(this, "Vui lòng kiểm tra lại thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
        }


    }

}
