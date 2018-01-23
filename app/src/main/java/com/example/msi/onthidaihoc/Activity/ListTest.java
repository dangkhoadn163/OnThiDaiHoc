package com.example.msi.onthidaihoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.FolderMoi.Moi;
import com.example.msi.onthidaihoc.MyFile.MyFile;
import com.example.msi.onthidaihoc.MyFile.MyFileAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFileViewHolder;
import com.example.msi.onthidaihoc.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;


public class ListTest extends AppCompatActivity {
    private static final String listTest = "ListTest";
    private MaterialSearchView searchviewww;
    ArrayList<MyFile> files;
    MyFileAdapter adapter;
    ArrayList<String> dethi;
    private RecyclerView rcvData;
    String monhoc;
    Toolbar toolbar;
    Integer Id_dethi;
    String Ten_dethi,Khuvuc_dethi,Nam_dethi,Lan_dethi,dethimon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);
        monhoc = getIntent().getExtras().getString("monhoc");
        anhXa();
        Nav();
//        loadList();
        getmon();
        search();
    }
    public  static final String TAG = ListTest.class.getSimpleName();
    public void setidmonmySQL(){
        if(monhoc.equals("anhvan")){
            monhoc="1";
        }else if(monhoc.equals("hoahoc")){
            monhoc="2";
        }else if(monhoc.equals("lichsu")){
            monhoc="3";
        }else if(monhoc.equals("vatly")){
            monhoc="4";
        }else if(monhoc.equals("dialy")){
            monhoc="5";
        }else if(monhoc.equals("sinhhoc")){
            monhoc="6";
        }else if(monhoc.equals("toanhoc")){
            monhoc="7";
        }else if(monhoc.equals("gdcd")){
            monhoc="8";
        }
    }
    public void setmonapp(){
        if(monhoc.equals("1")){
            monhoc="anhvan";
        }else if(monhoc.equals("2")){
            monhoc="hoahoc";
        }else if(monhoc.equals("3")){
            monhoc="lichsu";
        }else if(monhoc.equals("4")){
            monhoc="vatly";
        }else if(monhoc.equals("5")){
            monhoc="dialy";
        }else if(monhoc.equals("6")){
            monhoc="sinhhoc";
        }else if(monhoc.equals("7")){
            monhoc="toanhoc";
        }else if(monhoc.equals("8")){
            monhoc="gdcd";
        }
    }
    public void getmon(){
        setidmonmySQL();
        String type = "getmon";
        BackgroundWoker backgroundWoker = new BackgroundWoker(ListTest.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(ListTest.this, ""+output, Toast.LENGTH_SHORT).show();
                dethimon=output;
                load();
            }
        });
        backgroundWoker.execute(type,monhoc);
    }
    public void load(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        for (int i = 0; i < dethimon.length(); i++) {
            if (dethimon != null) {
                try {
                    JSONArray jsonArray = new JSONArray(dethimon);
                    JSONObject index = jsonArray.getJSONObject(i);
                    Id_dethi = index.getInt("Id");
                    Ten_dethi = index.getString("Ten");
                    Khuvuc_dethi = index.getString("Khuvuc");
                    Nam_dethi = index.getString("Nam");
                    Lan_dethi = index.getString("Lan");
                    MyFileViewHolder viewHolder;
                    final MyFile model = new MyFile();
                    model.id = Id_dethi;
                    model.ten = Ten_dethi;
                    model.khuvuc = Khuvuc_dethi;
                    model.nam = Nam_dethi;
                    model.lan = Lan_dethi;
                    files.add(model);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            setmonapp();
                            model.id = files.get(position).id;
                            Intent intent= new Intent(ListTest.this,Test.class);
                            Log.d("postion", position + "/" + "/" + model.id);
                            intent.putExtra("id_dethi", model.id);
                            intent.putExtra("monhoc", monhoc);
                            ListTest.this.startActivity(intent);
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
                Toast.makeText(ListTest.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loadList()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://khoaduong007.000webhostapp.com/getnametest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                for(int i=0; i<response.length();i++){
                    if(response != null){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject index = jsonArray.getJSONObject(i);
                            Id_dethi = index.getInt("Id");
                            Ten_dethi = index.getString("Ten");
                            Khuvuc_dethi = index.getString("Khuvuc");
                            Nam_dethi = index.getString("Nam");
                            Lan_dethi = index.getString("Lan");
                            MyFileViewHolder viewHolder;
                            final MyFile model = new MyFile();
                            model.id = Id_dethi;
                            model.ten = Ten_dethi;
                            model.khuvuc = Khuvuc_dethi;
                            model.nam = Nam_dethi;
                            model.lan = Lan_dethi;
                            files.add(model);
                            Log.d("DK","ten: "+Ten_dethi);
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View itemView, int position) {
                                    model.id = files.get(position).id;
                                    Intent intent= new Intent(ListTest.this,Test.class);
                                    Log.d("postion", position + "/" + "/" + model.id);
                                    intent.putExtra("id_dethi", model.id);
                                    ListTest.this.startActivity(intent);
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
                        Toast.makeText(ListTest.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListTest.this, "Kiểm tra kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
        rcvData.setAdapter(adapter);
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String flattenToAscii(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        for (char c : string.toCharArray()) {
            if (c > '\u007F')
            {
                sb.append(c);
                Log.d("char", c + "");
            }
        }
        return sb.toString();
    }
/*    private  void loadSearch(final String nameTest, final ArrayList<MyFile> newList)
    {
        int length = mois.size();
        for (int z = 0; z < length; z++)
        {
            final int finalZ = z;
            List<String> temp2Arr = new ArrayList<String>();
            List<String> tempArr = new ArrayList<String>();

            final MyFile model = new MyFile();
            String temp = mois.get(z).text.toLowerCase();
            String temp2 = nameTest.toLowerCase();
            String comparetemp = removeDiacriticalMarks(temp);
            String comparetemp2 = removeDiacriticalMarks(temp2);

            String accentTemp2 = flattenToAscii(temp2).toString();
            accentTemp2 = accentTemp2.replaceAll("̂", "̀̂");
            int nTemp2 = accentTemp2.length();
            boolean check = false;

            String replaceD = comparetemp.replaceAll("đ", "d");
            String replaceD2 = comparetemp2.replaceAll("đ", "d");
            if ((temp.contains(temp2) || (replaceD.contains(replaceD2) && temp2.contains(replaceD2))) && !check)
            {
                check = true;
                model.text = mois.get(z).text;
                newList.add(model);
                adapter.notifyDataSetChanged();

                adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent= new Intent(ListTest.this,Test.class);
                        intent.putExtra("keyt", dethi.get(finalZ));
                        intent.putExtra("Uid2", uid);
                        intent.putExtra("monhoc",monhoc);
                        intent.putExtra("tende", model.text);
                        ListTest.this.startActivity(intent);
                    }
                });
            }
            if(replaceD.contains(replaceD2) && nTemp2 != 0 && !check)
            {
                int pos = replaceD.indexOf(replaceD2);
                String sb = temp.substring(pos, temp2.length() + pos);
                String accentTemp = flattenToAscii(sb).toString();
                accentTemp = accentTemp.replaceAll("̂", "̀̂");
                Log.d("sb", sb);
                Log.d("accentTemp", accentTemp);
                Log.d("accentTemp2", accentTemp2);
                if(accentTemp.contains(accentTemp2) && !check)
                {
                    check = true;
                    model.text = mois.get(z).text;
                    newList.add(model);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent= new Intent(ListTest.this,Test.class);
                            intent.putExtra("keyt", dethi.get(finalZ));
                            intent.putExtra("Uid2", uid);
                            intent.putExtra("monhoc",monhoc);
                            intent.putExtra("tende", model.text);
                            ListTest.this.startActivity(intent);
                        }
                    });
                }
                else if(!accentTemp.contains(accentTemp2) && !check)
                {
                    check = true;
                    accentTemp = accentTemp.replaceAll("̉đ", "đ");
                    int nTemp = accentTemp.length();
                    for(int i = 0; i < nTemp2; i++)
                    {
                        for(int j = 0; j < nTemp; j++)
                        {
                            if (accentTemp2.charAt(i) == accentTemp.charAt(j) && !check)
                            {
                                check = true;
                                model.text = mois.get(z).text;
                                newList.add(model);
                                adapter.notifyDataSetChanged();
                                adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent = new Intent(ListTest.this, Test.class);
                                        intent.putExtra("keyt", dethi.get(finalZ));
                                        intent.putExtra("Uid2", uid);
                                        intent.putExtra("monhoc", monhoc);
                                        intent.putExtra("tende", model.text);
                                        ListTest.this.startActivity(intent);
                                    }
                                });
                                break;
                            }
                        }
                    }
                }
            }

            if(replaceD2.contains(" ") && !replaceD.contains(replaceD2))
            {
                int posStartTemp2 = 0;
                int lengthTemp2 = temp2.length();
                //Temp2
                for (int i = 0; i < lengthTemp2; i++)
                {
                    String subStrTemp2;
                    if (temp2.charAt(i) == (char) 32) {
                        subStrTemp2 = temp2.substring(posStartTemp2, i);
                        temp2Arr.add(subStrTemp2);
                        posStartTemp2 = i + 1;
                        Log.d("posStart", posStartTemp2 + "");
                        i = posStartTemp2;
                    }
                    if (i == lengthTemp2 - 1) {
                        subStrTemp2 = temp2.substring(posStartTemp2);
                        temp2Arr.add(subStrTemp2);
                        break;
                    }
                }

                int lengthTemp2tArr = temp2Arr.size();
                int countContains = 0;
                for(int i = 0; i < lengthTemp2tArr; i++)
                {
                    if((i != 0 && temp2Arr.get(i).contains("1"))
                            || (i != 0 && temp2Arr.get(i).contains("2"))
                            || (i != 0 && temp2Arr.get(i).contains("3")))
                    {
                        if("lan".contains(removeDiacriticalMarks(temp2Arr.get(i - 1))))
                        {
                            temp2Arr.set(i - 1, temp2Arr.get(i - 1)
                                    + " " + temp2Arr.get(i));
                            temp2Arr.remove(i);
                            lengthTemp2tArr = temp2Arr.size();
                            i--;

                        }
                    }
                    Log.d("temp2DArr", temp2Arr + "");
                    String compareArrTemp2 = removeDiacriticalMarks(temp2Arr.get(i));
                    String accentArrTemp2 = flattenToAscii(temp2Arr.get(i)).toString();
                    int nTemp2Arr = accentArrTemp2.length();
                    Log.d("replaceD", replaceD + "");
                    String replaceD2Arr = compareArrTemp2.replaceAll("đ", "d");

                    if ((temp.contains(temp2Arr.get(i)) || (replaceD.contains(replaceD2Arr)
                            && temp2Arr.get(i).contains(replaceD2Arr))
                            || (replaceD.contains(replaceD2Arr)
                            && (nTemp2Arr != 0)) ))
                    {
                        countContains++;
                    }

                    if(countContains == lengthTemp2tArr)
                    {
                        if(replaceD.contains(replaceD2Arr) && nTemp2Arr != 0)
                        {
                            int pos = replaceD.indexOf(replaceD2Arr);
                            String sb = temp.substring(pos, temp2Arr.get(i).length() + pos);
                            String accentArrTemp = flattenToAscii(sb).toString();
                            Log.d("sb", sb);
                            Log.d("accentTemp", accentArrTemp);
                            Log.d("accentTemp2", accentArrTemp2);
                            if(accentArrTemp.contains(accentArrTemp2) && !check)
                            {
                                check = true;
                                model.text = mois.get(z).text;
                                newList.add(model);
                                adapter.notifyDataSetChanged();
                                adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent= new Intent(ListTest.this,Test.class);
                                        intent.putExtra("keyt",dethi.get(finalZ));
                                        intent.putExtra("Uid2", uid);
                                        intent.putExtra("monhoc",monhoc);
                                        intent.putExtra("tende", model.text);
                                        ListTest.this.startActivity(intent);
                                    }
                                });
                            }
                            else if(!accentArrTemp.contains(accentTemp2) && !check)
                            {
                                accentArrTemp = accentArrTemp.replaceAll("̉đ", "đ");
                                int nTempArr = accentArrTemp.length();
                                for(int h = 0; h < nTemp2Arr; h++)
                                {
                                    for(int k = 0; k < nTempArr; k++)
                                    {
                                        if (accentArrTemp2.charAt(h) == accentArrTemp.charAt(k) && !check)
                                        {
                                            check = true;
                                            model.text = mois.get(z).text;
                                            newList.add(model);
                                            adapter.notifyDataSetChanged();
                                            adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View view, int position) {
                                                    Intent intent = new Intent(ListTest.this, Test.class);
                                                    intent.putExtra("keyt", dethi.get(finalZ));
                                                    intent.putExtra("Uid2", uid);
                                                    intent.putExtra("monhoc", monhoc);
                                                    intent.putExtra("tende", model.text);
                                                    ListTest.this.startActivity(intent);
                                                }
                                            });
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if ((temp.contains(temp2Arr.get(i)) || (replaceD.contains(replaceD2Arr)
                                && temp2Arr.get(i).contains(replaceD2Arr))) && !check)
                        {
                            check = true;
                            Log.d("temp2ArrItem", temp2Arr.get(i) + "");
                            Log.d("temp2ArrList", temp2Arr + "");
                            model.text = mois.get(z).text;
                            newList.add(model);
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(new MyFileAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent= new Intent(ListTest.this,Test.class);
                                    intent.putExtra("keyt", dethi.get(finalZ));
                                    intent.putExtra("Uid2", uid);
                                    intent.putExtra("monhoc",monhoc);
                                    intent.putExtra("tende", model.text);
                                    ListTest.this.startActivity(intent);
                                }
                            });
                        }
                    }
                }
            }
            adapter.setfilter(newList);
            rcvData.setAdapter(adapter);
            rcvData.invalidate();
        }
    }*/

    public void anhXa() {
        searchviewww = (MaterialSearchView) findViewById(R.id.materialsearchview);
        dethi = new ArrayList<>();
        rcvData = (RecyclerView) findViewById(R.id.recyclerViewImage);
        files = new ArrayList<>();
        adapter = new MyFileAdapter(ListTest.this, files);
        // rcvData.setHasFixedSize(true);
        //Linear
        rcvData.setLayoutManager(new LinearLayoutManager(ListTest.this));
        /*Grid
        rcvData.setLayoutManager(new GridLayoutManager(this,2));*/
        rcvData.setAdapter(adapter);
        dethi = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }
    private void search() {
        searchviewww.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<MyFile> newList = new ArrayList<MyFile>();
                if(newText.length() != 0)
                {
                    /*loadSearch(newText, newList);*/
                }
                return true;
            }
        });
    }
    private void Nav() {
        //set toolbar thay the cho actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.Search);
        searchviewww.setMenuItem(item);
        return true;
    }
}
