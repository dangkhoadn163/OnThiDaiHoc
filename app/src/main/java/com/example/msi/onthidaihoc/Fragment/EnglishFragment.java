package com.example.msi.onthidaihoc.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.msi.onthidaihoc.Activity.ListTest;
import com.example.msi.onthidaihoc.Activity.Score;
import com.example.msi.onthidaihoc.Activity.Test;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.MyFile.MyFile;
import com.example.msi.onthidaihoc.MyFile.MyFileAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFileViewHolder;
import com.example.msi.onthidaihoc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EnglishFragment extends Fragment{
    int idmonhoc=1,Iddethi;
    String uid,Tendethi;
    String listoldtest;
    ArrayList<MyFile> files;
    MyFileAdapter adapter;
    private RecyclerView rcvData;
    public EnglishFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english, container, false);
        uid = getActivity().getIntent().getExtras().getString("iduser");
        rcvData = (RecyclerView) view.findViewById(R.id.recyclerViewImage);
        files = new ArrayList<>();
        adapter = new MyFileAdapter(getActivity(), files);
        rcvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvData.setAdapter(adapter);
        getlistoldtest();
        return view;
    }
    public void getlistoldtest(){
        String type = "getlistoldtest";
        BackgroundWoker backgroundWoker = new BackgroundWoker(getActivity(), new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                listoldtest=output;
                Log.d("check","lấy kết quả thi: "+output);
                loaddapanuser();
            }
        });
        backgroundWoker.execute(type,uid,idmonhoc+"");
    }
    public void loaddapanuser(){
        for (int i = 0; i < listoldtest.length(); i++) {
            if (listoldtest != null) {
                try {
                    JSONArray jsonArray = new JSONArray(listoldtest);
                    JSONObject index = jsonArray.getJSONObject(i);
                    Iddethi = index.getInt("Iddethi");
                    Tendethi = index.getString("Tendethi");
                    MyFileViewHolder viewHolder;
                    final MyFile model = new MyFile();
                    model.ten = Tendethi;
                    files.add(model);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            model.id = files.get(position).id;
                            Intent intent= new Intent(getActivity(),Score.class);
                            Log.d("postion", position + "/" + "/" + model.id);
                            intent.putExtra("id_dethi",Iddethi);
                            intent.putExtra("id_monhoc", idmonhoc);
                            intent.putExtra("id_user", uid);
                            getActivity().startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.setfilter(files);
                rcvData.setAdapter(adapter);
                rcvData.invalidate();
            }
            else{
                Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}