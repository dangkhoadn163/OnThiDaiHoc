package com.example.msi.onthidaihoc.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.msi.onthidaihoc.Activity.RegisterActivity;
import com.example.msi.onthidaihoc.Activity.Score;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.Class.RegularExpressions;
import com.example.msi.onthidaihoc.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DK on 11/1/2017.
 */

public class FragmentAccount extends Fragment {
    EditText editName,editSchool,editClass,editAddress,editPhone,editGrade,editBirth;
    ImageButton imgbName,imgSchool,imgbClass,imgbAddress,imgbPhone,imgbGrade,imgbBirth;
    Button btnCancel,btnSave;
    String uid;
    String nameuser="";
    String classuser="";
    String schooluser="";
    String addressuser="";
    String phoneuser="";
    String gradleuser="";
    String info;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentacount,container,false);
        editName= (EditText) view.findViewById(R.id.edt_name);
        editSchool= (EditText) view.findViewById(R.id.edt_school);
        editClass= (EditText) view.findViewById(R.id.edt_class);
        editAddress= (EditText) view.findViewById(R.id.edt_address);
        editPhone= (EditText) view.findViewById(R.id.edt_phone);
        editGrade= (EditText) view.findViewById(R.id.edt_grade);
        editBirth= (EditText) view.findViewById(R.id.edt_birth);
        btnSave=(Button)view.findViewById(R.id.btn_save);
        btnCancel=(Button)view.findViewById(R.id.btn_cancel);
        imgbName=(ImageButton)view.findViewById(R.id.btn_editname);
        imgSchool=(ImageButton)view.findViewById(R.id.btn_editschool);
        imgbClass=(ImageButton)view.findViewById(R.id.btn_editclass);
        imgbAddress=(ImageButton)view.findViewById(R.id.btn_editaddress);
        imgbPhone=(ImageButton)view.findViewById(R.id.btn_editphone);
        imgbGrade=(ImageButton)view.findViewById(R.id.btn_editgrade);
        imgbBirth=(ImageButton)view.findViewById(R.id.btn_editbirth);


        uid = getActivity().getIntent().getExtras().getString("iduser");
        click();

        btnSave();
        getinfouser();
        btnCancel();
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

    public void click(){
        imgbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setEnabled(true);
            }
        });
        imgSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSchool.setEnabled(true);
            }
        });
        imgbClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editClass.setEnabled(true);
            }
        });
        imgbAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress.setEnabled(true);
            }
        });
        imgbPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhone.setEnabled(true);
            }
        });
        imgbGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editGrade.setEnabled(true);
            }
        });
        imgbBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });

    }
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            Toast.makeText(getActivity(), mFormatter.format(date), Toast.LENGTH_SHORT).show();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(getActivity(),
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
    public void getinfouser(){
        String type ="infouser";
        BackgroundWoker backgroundWoker=new BackgroundWoker(getActivity(), new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                info=output;
                Log.d("infomation",""+info);
                loadinfouser();
            }
        });
        backgroundWoker.execute(type,uid);
    }
    public void loadinfouser(){
        for (int i = 0; i < info.length(); i++) {
            if (info != null) {
                try {
                    JSONArray jsonArray = new JSONArray(info);
                    JSONObject index = jsonArray.getJSONObject(i);
                    nameuser = index.getString("Tenuser");
                    schooluser = index.getString("Truong");
                    classuser = index.getString("Lop");
                    addressuser = index.getString("Diachi");
                    phoneuser = index.getString("Sdt");
                    gradleuser = index.getString("Khoi");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                load();
            }
            else{
                Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void btnSave(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String ten = editName.getText().toString();
                    String truong = editSchool.getText().toString();
                    String lop = editClass.getText().toString();
                    String diachi = editAddress.getText().toString();
                    String sdt = editPhone.getText().toString();
                    String khoi = editGrade.getText().toString();
                    String type = "editinfo";
                    BackgroundWoker backgroundWoker = new BackgroundWoker(getActivity(), new BackgroundWoker.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Toast.makeText(getActivity(), ""+output, Toast.LENGTH_SHORT).show();
                        }
                    });
                    backgroundWoker.execute(type, ten, truong, lop, diachi, sdt,khoi,uid);
                getinfouser();
            }
        });
    }
    public void load(){
        editName.setText(nameuser);
        editSchool.setText(schooluser);
        editClass.setText(classuser);
        editAddress.setText(addressuser);
        editPhone.setText(phoneuser);
        editGrade.setText(gradleuser);
        editName.setEnabled(false);
        editSchool.setEnabled(false);
        editClass.setEnabled(false);
        editAddress.setEnabled(false);
        editPhone.setEnabled(false);
        editGrade.setEnabled(false);
    }
    public void btnCancel(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setEnabled(false);
                editSchool.setEnabled(false);
                editPhone.setEnabled(false);
                editAddress.setEnabled(false);
                editClass.setEnabled(false);
                load();
            }
        });
    }
}

