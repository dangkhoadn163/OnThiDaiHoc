package com.example.msi.onthidaihoc.MyFile;

/**
 * Created by DK on 10/6/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msi.onthidaihoc.R;

import java.util.ArrayList;
import java.util.List;

public class MyFileAdapter extends RecyclerView.Adapter<MyFileAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MyFile> files;
    private static OnItemClickListener listener;

    public MyFileAdapter(Context mContext, ArrayList<MyFile> filesr) {
        this.mContext = mContext;
        this.files = files;
    }
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyFile p = files.get(position);
        holder.txtten.setText(p.ten);
//        Picasso.with(mContext).load(p.image).into(holder.img);
    }

    @Override
    public int getItemCount() {

        return files == null ? 0 : files.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtid,txtten,txtkhuvuc,txtnam,txtlan;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtid = (TextView) itemView.findViewById(R.id.txvId);
            txtten = (TextView) itemView.findViewById(R.id.txvTen);
            txtkhuvuc = (TextView) itemView.findViewById(R.id.txvKhuvuc);
            txtnam = (TextView) itemView.findViewById(R.id.txvNam);
            txtlan = (TextView) itemView.findViewById(R.id.txvLan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });

        }
    }
    public void setfilter(ArrayList<MyFile> newList){
        this.files = new ArrayList<>();
        this.files.addAll(newList);
        Log.d("filesssssssssssss", files.size() + "");
        this.notifyDataSetChanged();
    }
}
