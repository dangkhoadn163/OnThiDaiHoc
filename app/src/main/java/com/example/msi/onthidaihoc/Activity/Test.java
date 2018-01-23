package com.example.msi.onthidaihoc.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;
import com.example.msi.onthidaihoc.CustomDialog.DialogStart;
import com.example.msi.onthidaihoc.FolderMoi.Moi;
import com.example.msi.onthidaihoc.FolderMoi.MoiAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFile;
import com.example.msi.onthidaihoc.MyFile.MyFileAdapter;
import com.example.msi.onthidaihoc.MyFile.MyFileViewHolder;
import com.example.msi.onthidaihoc.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Test extends AppCompatActivity {
    private static final String TAG = "Test";
    DrawerLayout drawer;
    LinearLayout lnl;
    private DialogStart dialognew;
    ProgressDialog dialogstart;
    Toolbar toolbar;
    NavigationView navigation;
    private RadioGroup[] rdg = new RadioGroup[50];
    String answers;
    Integer id;
    String monhoc="";
    int clock;
    String scored;
    String duongdandethi,Link;
    private String mstr;
    private String sstr;
    private int countMinute;
    private int countSecond;
    private byte countquiz;
    private int time;
    private String saveanswers = "";
    private boolean checkClickDialog = false;
    ArrayList<String> mois;
    MoiAdapter adapter;
    private TextView tvMinute, tvSecond;
    private Handler handler;
    private Button btnSave;
    private Runnable runnable;
    private CountDownTimer countDownTimer;
    private ImageButton imgClock, imgPen;
    private RecyclerView rcvDataMoi;
    private BigDecimal score = new BigDecimal("0.0");
    private BigDecimal scoreperanswer = new BigDecimal("0.0");
    ScrollView scrollView;
    ProgressBar progressBar;
    CountDownTimer timercheck,timerstart;
    AlertDialog.Builder dialogBack;
    AlertDialog dialogfinish;
    private RecyclerView rcvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        monhoc = getIntent().getExtras().getString("monhoc");
        id = getIntent().getExtras().getInt("id_dethi");
        clock=1;
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        dialognew = new DialogStart(Test.this);

        anhxa();
        initquiztimescore(monhoc);

        CDTimer();
        openCustomDialog();
        radiogroup();
//        loadnameuser(userid);

        getlink();
//        loadanswer(keyt);
        autoCheck();
        Nav();
        ClickClock();
        countup();
    }
    public void getlink(){
        String type = "load";
        BackgroundWoker backgroundWoker = new BackgroundWoker(Test.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(Test.this, ""+output, Toast.LENGTH_SHORT).show();
                duongdandethi=output;
                load();
            }
        });
        backgroundWoker.execute(type,id+"");
    }
    public void load() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                    Toast.makeText(Test.this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    public void loadsaveanswers(){
        String idquantri="1";
        String answersuser = saveanswers;
        String type = "loadsaveanswers";
        BackgroundWoker backgroundWoker = new BackgroundWoker(Test.this, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Toast.makeText(Test.this, ""+output, Toast.LENGTH_SHORT).show();
            }
        });
        backgroundWoker.execute(type, idquantri);
    }
    void autoCheck()
    {
        for(int i = 0; i < countquiz; i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(4) + 97;
            String quizid = "cau" + (i + 1) + (char)r;
            int resID = getResources().getIdentifier(quizid, "id", getPackageName());
            rdg[i].check(resID);
        }
    }
    private void anhxa() {
        imgClock = (ImageButton) findViewById(R.id.imageClock);
        imgPen = (ImageButton) findViewById(R.id.imagePencil);
        tvMinute = (TextView) findViewById(R.id.txtMinute);
        tvSecond = (TextView) findViewById(R.id.txtSecond);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        rdg1=(RadioGroup)findViewById(R.id.rdgcau1);
        for (int i = 0; i < 50; i++) {
            String quizid = "rdgcau" + (i + 1);
            int resID = getResources().getIdentifier(quizid, "id", getPackageName());
            rdg[i] = ((RadioGroup) findViewById(resID));
//            Toast.makeText(Test.this, quizid,Toast.LENGTH_SHORT).show();
        }
        mois = new ArrayList<>();
        adapter = new MoiAdapter(Test.this,mois);
        rcvDataMoi = (RecyclerView) findViewById(R.id.recyclerViewTest);
        rcvDataMoi.setHasFixedSize(true);
        rcvDataMoi.setLayoutManager(new LinearLayoutManager(this));
        rcvDataMoi.setAdapter(adapter);

    }
    private void ClickClock(){
        imgClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clock= clock*-1;
                if (clock==1){
                    tvMinute.setVisibility(View.INVISIBLE);
                    tvSecond.setVisibility(View.INVISIBLE);
                }
                else if(clock==-1) {
                    tvMinute.setVisibility(View.VISIBLE);
                    tvSecond.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void radiogroup() {
        for (int j = 0; j < 50; j++) {
            final int finalJ = j;
            rdg[j].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override

                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    //rdg[finalJ].getResources().getResourceEntryName(rdg[finalJ].getCheckedRadioButtonId());
//                            Toast.makeText(Test.this, rdg[finalJ]
//                                            .getResources()
//                                            .getResourceEntryName(rdg[finalJ]
//                                            .getCheckedRadioButtonId()) + "", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void dialog(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Nộp bài thi");
        builder.setMessage("Bạn có chắc chắn muốn nộp bài thi không?");
        builder.setNegativeButton("Không", null);
        builder.setPositiveButton("Nộp bài", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Test.this, "Điểm của bạn là: " + score, Toast.LENGTH_SHORT).show();
//                        Log.d("Score", score + "");
                loadsaveanswers();
                Intent intent= new Intent(Test.this,Score.class);
                intent.putExtra("id",id);
                Test.this.startActivity(intent);
            }
        });
        dialogfinish = builder.create();
        dialogfinish.show();
    }

    private void Nav() {
        //set toolbar thay the cho actionbar
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_tracnghiem);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        navigation = (NavigationView) findViewById(R.id.nvView);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idRdb = "";
                for (int j = 0; j < countquiz; j++) {
                    if ((rdg[j].getCheckedRadioButtonId()) == -1)
                    {
                        Toast.makeText(Test.this, "Bạn chưa đánh câu " + (j + 1),
                                Toast.LENGTH_SHORT).show();
                        scrollView.smoothScrollTo(0, rdg[j].getTop());
                        return;
                    }
                }
                if(countMinute <= 4){
                    Toast.makeText(Test.this, "Còn 5 phút hết giờ làm bài!!! \n" +
                            "Bạn sẽ không được nộp bài kể từ thời gian này !", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog();
                Log.d("ID", idRdb );
                int lengthresult = answers.length();
                String temp = "";
                int index = 0;
                for(int j = 0; j < lengthresult; j++)
                {
                    char c = answers.charAt(j);
                    temp = temp.concat(c + "");
                    if(c >= 'A' && c <= 'D')
                    {
                        Log.d("Temp", temp);
                        boolean checkresult = rdg[index]
                                .getResources()
                                .getResourceEntryName(rdg[index]
                                        .getCheckedRadioButtonId()).toLowerCase().contains(temp.toLowerCase());
                        Log.d("Result", temp + ":" + checkresult + "");
                        saveanswers = saveanswers.concat(rdg[index].getResources()
                                .getResourceEntryName(rdg[index].getCheckedRadioButtonId()) + "");
                        if(checkresult)
                        {
                            score = score.add(scoreperanswer);
                            Log.d("Scorestep", score + "");
                        }
                        temp = "";
                        index++;
                    }
                }
                saveanswers = saveanswers.replace("cau", "");
                scored= score + "";
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//noinspectionSimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
    private void timeup(){
        Toast.makeText(Test.this, "Đã hết giờ làm bài", Toast.LENGTH_SHORT).show();
        int lengthresult = answers.length();
        String temp = "";
        int index = 0;
        for(int j = 0; j < lengthresult; j++)
        {
            char c = answers.charAt(j);
            temp = temp.concat(c + "");
            if(c >= 'A' && c <= 'D')
            {
                Log.d("Temp", temp);
                boolean checkresult = false;
                //Log.d("Result", temp + ":" + checkresult + "");
                if(rdg[index].getCheckedRadioButtonId() == -1)
                {
                    saveanswers = saveanswers.concat("cau" + (index + 1) + "e");
                    Log.d("timeup1", saveanswers);
                    checkresult = false;
                }
                else
                {
                    saveanswers = saveanswers.concat(rdg[index].getResources()
                            .getResourceEntryName(rdg[index].getCheckedRadioButtonId()) + "");
                    checkresult = rdg[index]
                            .getResources()
                            .getResourceEntryName(rdg[index]
                                    .getCheckedRadioButtonId()).toLowerCase().contains(temp.toLowerCase());
                }

                if(checkresult)
                {
                    score = score.add(scoreperanswer);
                    Log.d("Scorestep", score + "");
                }
                temp = "";
                index++;
            }
        }
        saveanswers = saveanswers.replace("cau", "");
        Log.d("timeup", saveanswers);
        scored= score+"";
        Log.d("score0", scored);
        loadsaveanswers();
    }
    private void initquiztimescore(String monhoc)
    {
        if(monhoc.equals("anhvan"))
        {
            time = 3600000;
            countquiz = 50;
            scoreperanswer = new BigDecimal("0.2");
        }
        else if(monhoc.equals("toanhoc"))
        {
            time = 5400000;
            countquiz = 50;
            scoreperanswer = new BigDecimal("0.2");
        }
        else if(monhoc.equals("vatly") || monhoc.equals("hoahoc") || monhoc.equals("gdcd")
                || monhoc.equals("dialy") || monhoc.equals("lichsu") || monhoc.equals("sinhhoc"))
        {
            time = 3000000;
            countquiz = 40;
            scoreperanswer = new BigDecimal("0.25");
        }
        for(int i = countquiz; i < 50; i++)
        {
            for(int j = 0; j <= 4; j++)
            {
                rdg[i].getChildAt(j).setEnabled(false);
            }
        }
        mstr = getString(R.string.minute, (time / 1000) / 60);
        sstr = getString(R.string.second, (time / 1000) % 60);
        tvMinute.setText("" + mstr);
        tvSecond.setText(":" + sstr);
    }
    private void CDTimer() {
        timerstart = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set dethi to edittext
                long temp = millisUntilFinished / 1000;
                countMinute = (int)temp / 60;
                countSecond = (int)temp % 60;
                mstr = getString(R.string.minute, countMinute);
                sstr = getString(R.string.second, countSecond);
                tvMinute.setText("" + mstr);
                tvSecond.setText(":" + sstr);
                if(countMinute == 5 && countSecond == 0){
                    Toast.makeText(Test.this, "Còn 5 phút hết giờ làm bài !", Toast.LENGTH_SHORT).show();
                }
                if(dialogfinish != null)
                {
                    if(countMinute < 3 && dialogfinish.isShowing())
                    {
                        dialogfinish.dismiss();
                    }
                }
            }
            public void onFinish() {
                mstr = (getString(R.string.minute, 0));
                sstr = (getString(R.string.second, 0));
                tvMinute.setText("" + mstr);
                tvSecond.setText(":" + sstr);
                timeup();
                Toast.makeText(Test.this, "Điểm của bạn là: " + score, Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Test.this,Score.class);
                intent.putExtra("id", id);
                intent.putExtra("monhoc", monhoc);
                Test.this.startActivity(intent);
            }
        };
    }
    private void countup() {
        timercheck = new CountDownTimer(time,  200) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set dethi to edittext
                if (adapter.getter()) {
                    dialognew.pbstart.setVisibility(View.GONE);
                    dialognew.txvTitle.setText("Bạn đã sẵn sàng thi chưa?");
                    dialognew.txvThoat.setVisibility(View.VISIBLE);
                    dialognew.txvThi.setVisibility(View.VISIBLE);
                    timercheck.cancel();

//                    if(!dialognew.isShowing() && !checkClickDialog)
//                    {
//                        timercheck.cancel();
//                        timerstart.start();
//                    }
//                    if(dialognew.getCheckBack() && !dialognew.isShowing() && !checkClickDialog)
//                    {
//                        timercheck.cancel();
//                        timerstart.start();
//                    }
                    Log.d("cd", millisUntilFinished + "");
                }
                else
                {
                    if(!dialognew.getCheckBack() && dialognew.pbstart.isShown())
                    {
                        Toast.makeText(Test.this, "         Đang tải dữ liệu.\n " +
                                "Vui lòng chờ trong giây lát!", Toast.LENGTH_SHORT).show();
                        dialognew.setCheckBack(true);
                        dialognew.txvTitle.setText("Vui lòng chờ trong giây lát!!!");
                    }
                }
            }
            public void onFinish() {

            }
        }.start() ;
    }
    // Khỏi tạo và mở dialog tùy chỉnh
    private void openCustomDialog() {
        dialognew.txvThoat.setVisibility(View.INVISIBLE);
        dialognew.txvThi.setVisibility(View.INVISIBLE);
        dialognew.txvThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialognew.dismiss();
                timerstart.start();
                timercheck.cancel();
            }
        });
        dialognew.txvThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Test.this,ListTest.class);
                intent.putExtra("monhoc","anhvan");
                intent.putExtra("id", id);
                Test.this.startActivity(intent);
                /*ActivityCompat.finishAffinity(Test.this);*/
            }
        });
        dialognew.show();
        dialognew.dialogListener(timercheck, timerstart);
    }

    @Override
    public void onBackPressed()
    {
        dialogBack = new AlertDialog.Builder(this);
        dialogBack.setTitle("Thoát ứng dụng?") ;
        dialogBack.setMessage("Bạn phải nộp bài hay đợi hết thời gian mới được thoát nhé !");
        dialogBack.setNegativeButton(":)", null);
        dialogBack.create().show();
    }
}
