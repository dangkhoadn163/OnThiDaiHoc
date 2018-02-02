package com.example.msi.onthidaihoc.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.msi.onthidaihoc.Activity.Score;
import com.example.msi.onthidaihoc.Activity.Test;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.FolderMoi.Moi;
import com.example.msi.onthidaihoc.FolderMoi.MoiAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFileViewHolder;
import com.example.msi.onthidaihoc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DK on 12/3/2017.
 */

public class Score_Fragment extends Fragment{
    private static final String TAG = "Test";
    DrawerLayout drawer;
    int id,id2,idmonhoc,idmonhoc2;
    String duongdandethi,Link;
    String userid,uid;
    String monhoc,monhoc2,tende;
    ArrayList<String> mois;
    MoiAdapter adapter_moi;
    private RecyclerView rcvDataMoi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.score_fragment,container,false);
        mois = new ArrayList<>();
        adapter_moi = new MoiAdapter(getActivity(), mois);
        id = getActivity().getIntent().getExtras().getInt("id");
        if (id != 0) {
            id = getActivity().getIntent().getExtras().getInt("id");
        }
        else {
            id2 = getActivity().getIntent().getExtras().getInt("id_dethi");
            id=id2;
        }

        idmonhoc = getActivity().getIntent().getExtras().getInt("idmonhoc");
        if(idmonhoc!=0){
            idmonhoc = getActivity().getIntent().getExtras().getInt("idmonhoc");
        }
        else {
            idmonhoc2 = getActivity().getIntent().getExtras().getInt("id_monhoc");
            idmonhoc=idmonhoc2;
        }

        rcvDataMoi = (RecyclerView)view.findViewById(R.id.recyclerViewImage);
/*        rcvDataMoi.setHasFixedSize(true);*/
        rcvDataMoi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvDataMoi.setAdapter(adapter_moi);
        setidmonmySQL();
        getlink();
        return view;
    }
    public void setidmonmySQL(){
        if(idmonhoc==1){
            monhoc="anhvan";
        }else if(idmonhoc==2){
            monhoc="hoahoc";
        }else if(idmonhoc==3){
            monhoc="lichsu";
        }else if(idmonhoc==4){
            monhoc="vatly";
        }else if(idmonhoc==5){
            monhoc="dialy";
        }else if(idmonhoc==6){
            monhoc="sinhoc";
        }else if(idmonhoc==7){
            monhoc="toanhoc";
        }else if(idmonhoc==8){
            monhoc="gdcd";
        }
    }
    public void getlink(){
        String type = "load";
        BackgroundWoker backgroundWoker = new BackgroundWoker(getActivity(), new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                duongdandethi=output;
                load();
            }
        });
        backgroundWoker.execute(type,id+"");
    }
    public void load() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        for (int i = 0; i < duongdandethi.length(); i++) {
            if (duongdandethi != null) {
                try {
                    JSONArray jsonArray = new JSONArray(duongdandethi);
                    JSONObject index = jsonArray.getJSONObject(i);
                    Link = index.getString("Link");
                    MyFileViewHolder viewHolder;
                    final Moi model = new Moi();
                    model.test = Link;
                    mois.add(Link);
                    adapter_moi.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
