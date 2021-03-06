package com.example.msi.onthidaihoc.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.Class.MD5;
import com.example.msi.onthidaihoc.Class.RegularExpressions;
import com.example.msi.onthidaihoc.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName;
    EditText edtPass;
    EditText edtEmail;
    Button btnConfirm;
    String encodepassword;
    String REGEX="\\w+@\\w+[.]\\w+([.]\\w+)?";

    RegularExpressions CheckEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtEmail=(EditText)findViewById(R.id.emailuser);
        edtName=(EditText)findViewById(R.id.nameuser);
        edtPass=(EditText)findViewById(R.id.passuser);
        btnConfirm=(Button) findViewById(R.id.confirmuser);
        click();
    }
    public void encodepass(){
        MD5 md5 = new MD5();
        if(edtPass.getText().equals("")){
            Toast.makeText(this, "Vui lòng nhập đầy dủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            encodepassword=md5.encryption(edtPass.getText().toString());
            Log.d("encode",""+encodepassword);
        }

    }
    public void click(){
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String INPUT= edtEmail.getText().toString();
                CheckEmail= new RegularExpressions();
                if(edtPass.getText().toString().equals("")||edtName.getText().toString().equals("")||CheckEmail.check(REGEX,INPUT)==false){
                    Log.d("DK","email: "+INPUT);
                }
                else {
                    encodepass();
                    String name = edtName.getText().toString();
                    String email = edtEmail.getText().toString();
                    String pass = encodepassword;
                    String type = "register";
                    BackgroundWoker backgroundWoker = new BackgroundWoker(RegisterActivity.this, new BackgroundWoker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Toast.makeText(RegisterActivity.this, ""+output, Toast.LENGTH_SHORT).show();
                        }
                    });
                    backgroundWoker.execute(type, name, email, pass);
                }
            }
        });
    }
}
