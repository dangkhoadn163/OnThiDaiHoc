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

import com.example.msi.onthidaihoc.FolderMoi.MoiAdapter;
import com.example.msi.onthidaihoc.R;

import java.util.ArrayList;

/**
 * Created by DK on 12/3/2017.
 */

public class DetailResult_Fragment extends Fragment {

    private static final String TAG = "Test";
    DrawerLayout drawer;
    String keyt,key;
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
        keyt = getActivity().getIntent().getExtras().getString("keyt111");
        if (keyt != null) {
            keyt = getActivity().getIntent().getExtras().getString("keyt111");
        }
        else {
            key = getActivity().getIntent().getExtras().getString("keyt222");
            keyt=key;
        }

        monhoc = getActivity().getIntent().getExtras().getString("monhoc");
        if(monhoc!=null){
            monhoc = getActivity().getIntent().getExtras().getString("monhoc");
        }
        else {
            monhoc2 = getActivity().getIntent().getExtras().getString("monhoc2");
            monhoc=monhoc2;
        }

        rcvDataMoi = (RecyclerView)view.findViewById(R.id.recyclerViewImage);
/*        rcvDataMoi.setHasFixedSize(true);*/
        rcvDataMoi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvDataMoi.setAdapter(adapter_moi);
        loaddetailresult(keyt);
        return view;
    }
     public void loaddetailresult(String keyt) {

    }

}
