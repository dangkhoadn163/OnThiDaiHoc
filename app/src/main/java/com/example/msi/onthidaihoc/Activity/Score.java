package com.example.msi.onthidaihoc.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.FolderMoi.MoiAdapter;
import com.example.msi.onthidaihoc.Fragment.DetailResult_Fragment;
import com.example.msi.onthidaihoc.Fragment.Score_Fragment;
import com.example.msi.onthidaihoc.MyFile.MyFile;
import com.example.msi.onthidaihoc.MyFile.MyFileAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFileViewHolder;
import com.example.msi.onthidaihoc.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Score extends AppCompatActivity {
    private static final String TAG = "Test";
    DrawerLayout drawer;
    ArrayList<MyFile> files;
    MyFileAdapter adapter;
    ArrayList<String> dethi;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int idmonhoc,idmonhoc2,id,id2;
    String userid,uid;
    String useranswer;
    String quizanswers;
    String monhoc,monhoc2;
    String scoreuser;
    private int countquiz = 0;
    Toolbar toolbar;
    String Dapan_dethi,Dapan_user,Score,dethimon,ketquathi;
    NavigationView navigation;
    RadioButton[][] rdbtn = new RadioButton[50][4];
    private TextView tvscore;
    TextView[] txvArr = new TextView[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvscore= (TextView)findViewById(R.id.tv_score);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        dethi = new ArrayList<>();
        files = new ArrayList<>();
        adapter = new MyFileAdapter(Score.this, files);
        dethi = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        id = getIntent().getExtras().getInt("id");
        if (id != 0) {
            id = getIntent().getExtras().getInt("id");
        }
        else {
            id2 = getIntent().getExtras().getInt("id_dethi");
            id =id2;
        }

        userid = getIntent().getExtras().getString("iduser");
        if(userid!=null){
            userid = getIntent().getExtras().getString("iduser");
        }
        else {
            uid = getIntent().getExtras().getString("id_user");
            userid=uid;
        }

        idmonhoc = getIntent().getExtras().getInt("idmonhoc");
        if(idmonhoc!=0){
            idmonhoc = getIntent().getExtras().getInt("idmonhoc");
        }
        else {
            idmonhoc2 = getIntent().getExtras().getInt("id_monhoc");
            idmonhoc=idmonhoc2;
        }
        anhxa();
        setidmonmySQL();
        inintquiz(monhoc);
        getketquathi();
        // loadnameuser(userid);
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
    public void getmon(){
        setidmonmySQL();
        String type = "getdethi";
        BackgroundWoker backgroundWoker = new BackgroundWoker(Score.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                dethimon=output;
                loaddapandethi();
            }
        });
        backgroundWoker.execute(type,id+"",idmonhoc+"");
    }
    public void loaddapandethi(){
        for (int i = 0; i < dethimon.length(); i++) {
            if (dethimon != null) {
                try {
                    JSONArray jsonArray = new JSONArray(dethimon);
                    JSONObject index = jsonArray.getJSONObject(i);
                    Dapan_dethi = index.getString("Dapan");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadquizanswer();
            }
            else{
                Toast.makeText(Score.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getketquathi(){
        setidmonmySQL();
        String type = "getketquathi";
        BackgroundWoker backgroundWoker = new BackgroundWoker(Score.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                ketquathi=output;
                Log.d("check","lấy kết quả thi: "+output);
                loaddapanuser();
                getmon();
            }
        });
        backgroundWoker.execute(type,id+"",userid,idmonhoc+"");
    }
    public void loaddapanuser(){
        for (int i = 0; i < ketquathi.length(); i++) {
            if (ketquathi != null) {
                try {
                    JSONArray jsonArray = new JSONArray(ketquathi);
                    JSONObject index = jsonArray.getJSONObject(i);
                    Score = index.getString("Score");
                    Dapan_user = index.getString("Dapan");
                    Log.d("score","điểm, đáp án: "+Score+Dapan_user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loaduseranswer();
            }
            else{
                Toast.makeText(Score.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Score_Fragment(), "Kết quả");
        adapter.addFrag(new DetailResult_Fragment(), "Chi tiết kết quả");
        viewPager.setAdapter(adapter);
    }
    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        for (int i = 0; i < 50; i++)
        {
            String txvstr = "txvcau" + (i + 1);
            int restxvID = getResources().getIdentifier(txvstr, "id", getPackageName());
            txvArr[i] = ((TextView) findViewById(restxvID));
            for (int j = 0; j < 4; j++)
            {
                String quizid = "cau" + (i + 1) + (char) (j + 97);
                int resID = getResources().getIdentifier(quizid, "id", getPackageName());
                rdbtn[i][j] = ((RadioButton) findViewById(resID));
                Log.d("arr[]", rdbtn[i][j].getResources().getResourceEntryName(rdbtn[i][j].getId()));
            }
        }
    }

    public void loaduseranswer() {
        useranswer=Dapan_user;
    }

    public void loadquizanswer() {
        quizanswers=Dapan_dethi;
        loadscore();
    }
    public void loadscore() {
        scoreuser=Score;
        scoreformat();
        Nav();
    }

    public void loadnameuser(String userid) {
    }
    private void Nav() {
        //set toolbar thay the cho actionbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_tracnghiem);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        navigation = (NavigationView) findViewById(R.id.nvcView);
        Button btnchitiet = (Button) findViewById(R.id.btnChitiet);
        /*btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddetailresult(id);
            }
        });*/
        TextView tvscore= (TextView) findViewById(R.id.txv_score);
        tvscore.setText(countrightanswer() + "/" + countquiz);
        youranswers();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnSetting:
                Intent intent= new Intent(Score.this,ChooseActivity.class);
//                intent.putExtra("monhoc",monhoc);
//              key put phải trùng vs key bên ActivityChoose
                intent.putExtra("iduser", userid);
                Score.this.startActivity(intent);
                break;
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void scoreformat()
    {
        if((int)(Float.valueOf(scoreuser) * 10) == (Float.valueOf(scoreuser) * 10))
        {
            tvscore.setText(String.format("%.1f", Float.valueOf(scoreuser)) + "/10");
        }
        else
        {
            tvscore.setText(String.format("%.2f", Float.valueOf(scoreuser)) + "/10");
        }
    }
    private void inintquiz(String monhoc)
    {
        if(monhoc.equals("anhvan"))
        {
            countquiz = 50;
        }
        else if(monhoc.equals("vatly") || monhoc.equals("hoahoc") || monhoc.equals("dialy")
                || monhoc.equals("lichsu") || monhoc.equals("sinhhoc"))
        {
            countquiz = 40;
        }
        for(int i = countquiz; i < 50; i++)
        {
            txvArr[i].setEnabled(false);
            for(int j = 0; j < 4; j++)
            {
                rdbtn[i][j].setEnabled(false);
            }
        }
    }
    private int countrightanswer()
    {
        return (int)(Float.valueOf(scoreuser) / (10.0 / countquiz));
    }
    public void youranswers()
    {
        Log.d("answer", quizanswers + "");
        Log.d("useranswer", useranswer + "");
        int index = 0;
        int length = useranswer.length();
        int lengthquiz = quizanswers.length();
        if(length > lengthquiz)
        {
            useranswer = useranswer.substring(0, lengthquiz);
            length = useranswer.length();
        }
        char c;
        char cafter;
        char cquizans;
        char cquizansafter;
        for (int i = 0; i < length; i++)
        {
            String indexString = "";
            //String indexStringquiz = "";
            c = useranswer.charAt(i);
            cquizans = quizanswers.charAt(i);
            if (c >= '0' && c <= '9' && cquizans >= '0' && cquizans <= '9')
            {
                indexString += c + "";
                cafter = useranswer.charAt(i + 1);
                cquizansafter = quizanswers.charAt(i + 1);
                if (cafter >= '0' && cafter <= '9' && cquizansafter >= '0' && cquizansafter <= '9')
                {
                    indexString += cafter + "";
                    i++;
                }
                index = (Integer.valueOf(indexString)) - 1;
            }
            else if (c >= 'a' && c <= 'd' && cquizans >= 'A' && cquizans <= 'D')
            {
                rdbtn[index][(int)c - 97].setChecked(true);
                if (!rdbtn[index][(int)cquizans - 65].isChecked())
                {
                    rdbtn[index][(int)cquizans - 65].setButtonTintList(ColorStateList.valueOf(Color.RED));
                    rdbtn[index][(int)cquizans - 65].setChecked(true);
                }
                for (int j = 0; j < 4; j++)
                {
                    if ((int)c - 97 == j || (int)cquizans - 65 == j)
                        continue;
                    rdbtn[index][j].setEnabled(false);
                }
            }
            else if(c == 'e')
            {
                for (int j = 0; j < 4; j++)
                {
                    rdbtn[index][j].setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Thoát ứng dụng?")
                .setMessage("Bạn có chắc chắn muốn thoát ứng dụng?")
                .setNegativeButton("Không", null)
                .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.finishAffinity(Score.this);
                    }
                }).create().show();
    }
}