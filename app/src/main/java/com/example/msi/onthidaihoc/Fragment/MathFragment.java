package com.example.msi.onthidaihoc.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.onthidaihoc.MyFile.MyFile;
import com.example.msi.onthidaihoc.MyFile.MyFileAdapter;
import com.example.msi.onthidaihoc.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MathFragment extends Fragment {
    String uid;
    ArrayList<MyFile> files;
    MyFileAdapter adapter;
    private RecyclerView rcvData;
    DatabaseReference rootDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_math,container,false);
        uid = getActivity().getIntent().getExtras().getString("iduser");
        rcvData = (RecyclerView)view.findViewById(R.id.recyclerViewImage);
        files = new ArrayList<>();
        adapter = new MyFileAdapter(getActivity(), files);
        // rcvData.setHasFixedSize(true);
        //Linear
        rcvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvData.setAdapter(adapter);
        Log.d("context", getActivity() + "");
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }

}