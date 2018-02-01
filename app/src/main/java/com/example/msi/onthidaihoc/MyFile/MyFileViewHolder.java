package com.example.msi.onthidaihoc.MyFile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msi.onthidaihoc.R;
import com.squareup.picasso.Picasso;

/**
 * Created by DK on 8/23/2017.
 */

public final class MyFileViewHolder extends RecyclerView.ViewHolder {

/*    public ImageView imvHinhAnh;*/
    public TextView txvId,txvTen,txvKhuvuc,txvNam,txvLan,txvDapan;

    public MyFileViewHolder(View itemView) {
        super(itemView);
        /*this.imvHinhAnh = (ImageView) itemView.findViewById(R.id.imvHinhAnh);*/
        this.txvId = (TextView) itemView.findViewById(R.id.txvId);
        this.txvTen = (TextView) itemView.findViewById(R.id.txvTen);
        this.txvKhuvuc = (TextView) itemView.findViewById(R.id.txvKhuvuc);
        this.txvNam=(TextView)itemView.findViewById(R.id.txvNam);
        this.txvLan=(TextView)itemView.findViewById(R.id.txvLan);
        this.txvDapan=(TextView)itemView.findViewById(R.id.txvDapan);

    }
    public void loadHinhAnh(Context context, String duongDan) {
/*        Picasso.with(context).load(duongDan).into(imvHinhAnh);*/
    }
    public void loadId(Integer id) {
        txvTen.setText(id);
    }
    public void loadTenFile(String ten) {
        txvTen.setText(ten);
    }
    public void loadKhuvuc(String khuvuc) {

        txvKhuvuc.setText(khuvuc);
    }
    public void loadNam(String nam) {
        txvNam.setText(nam);
    }
    public void loadLan(String lan) {
        txvLan.setText(lan);
    }
    public void loadDapan(String dapan) {
        txvDapan.setText(dapan);
    }
    public void setActionClick(final String t){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),t +  "", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
