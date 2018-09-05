package com.example.msi.onthidaihoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.msi.onthidaihoc.CustomDialog.ChangepassDiaglog;
import com.example.msi.onthidaihoc.CustomDialog.LogoutDialog;
import com.example.msi.onthidaihoc.CustomDialog.PhotoDialog;
import com.example.msi.onthidaihoc.Fragment.FragmentAccount;
import com.example.msi.onthidaihoc.Fragment.FragmentMain;
import com.example.msi.onthidaihoc.Fragment.FragmentRate;
import com.example.msi.onthidaihoc.Fragment.FragmentReview;
import com.example.msi.onthidaihoc.Fragment.FragmentSupport;
import com.example.msi.onthidaihoc.R;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.msi.onthidaihoc.CustomDialog.Constant.IMAGE_REQUEST_CODE;
import static com.example.msi.onthidaihoc.CustomDialog.Controller.downloadAvatar;
import static com.example.msi.onthidaihoc.CustomDialog.Controller.uploadAvatar;


public class ChooseActivity extends AppCompatActivity{

    String uid,test;
    Bitmap bitmap;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigation;
    String name, email;
    private AlertDialog.Builder defaultDialog;
    private PhotoDialog customDialog;
    private ChangepassDiaglog changepassDiaglog;
    private LogoutDialog logoutDialog;
    private Context context;
    private Uri imageUri;

    //Header-Nav
    private static CircleImageView iv_picture;
    private TextView tv_name;
    private TextView tv_email;

    public static ChooseActivity chooseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        chooseActivity = this;

        uid = getIntent().getExtras().getString("iduser");
        ghi();
        anhxa();
        nav();
/*        loadnameuser(uid);*/

    }
    public void ghi(){
        SharedPreferences ghi=getSharedPreferences("uid",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ghi.edit();
        editor.putString("uid",uid);
        editor.commit();
    }
    private void anhxa() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        navigation = (NavigationView) findViewById(R.id.navview);
        View header = navigation.getHeaderView(0);
        iv_picture = (CircleImageView) header.findViewById(R.id.iv_picture);
        tv_name = (TextView) header.findViewById(R.id.tv_name);
        tv_email = (TextView) header.findViewById(R.id.tv_email);

    }

/*    public void loadnameuser(String uid) {
        rootDatabase.child("account").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("name")) {
                    name = dataSnapshot.child("name").getValue().toString();
                    info();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void info() {
        if (user != null) {
            // email address, and profile photo Url
            email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            nav();
        }
    }*/


    // Khỏi tạo và mở dialog tùy chỉnh
    private void openCustomDialog() {
        customDialog = new PhotoDialog(ChooseActivity.this);
        customDialog.setTitle("Select action");
        customDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Nếu nhận <ActivityResult> từ activity CHỌN ẢNH (không phân biệt từ thư viện hay chụp ảnh mới)
        // và kiểm tra có chọn ảnh hay không để xử lý hàm if này
        if (requestCode == IMAGE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            imageUri = data.getData();
            // Lấy được tấm ảnh rồi thì mở activity cắt ảnh
            CropImage.activity(imageUri)// thảy cái uri vào intent crop image
                    .setAspectRatio(1, 1)// set tỉ lệ cắt (ở đây cắt avatar nên để tỉ lệ 1:1 cho nó thành hình vuông)
                    .setMaxCropResultSize(500,500) // kích thước cắt tối đa: 500x500 pixel
                    .setMinCropWindowSize(50, 50) // kích thước cắt tối thiểu: 50x50 pixel
                    .setBackgroundColor(R.color.colorWhite) // tự hiểu
                    .start(this);
        }

        // Nếu nhận <ActivityResult> từ activity CẮT ẢNH
        // và nhận được 1 tấm ảnh đã cắt thì xử lý hàm if này:
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                // Lấy đường dẫn
                imageUri = result.getUri();
                Log.d("imageUri",""+imageUri);
                // Đóng dialog mở lên lúc nãy
                customDialog.dismiss();
                try {
//                    imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                }catch (Exception e){
                    Log.d("Loi uri",""+e);
                }
                // upload Avatar lên
                uploadAvatar(context,uid,bitmap);// Xem bên class
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // nếu xảy ra lỗi thì cũng đóng dialog luôn:
                customDialog.dismiss();
            }
        }
    }

    public void nav(){
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_profile);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        if(email!=null){
            tv_email.setText(email);
        }
        if(name!=null){
            tv_name.setText(name);
        }

        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog();
            }
        });
        //load avatar
        downloadAvatar(context,uid);



/*        User user = new User();
        user = downloadAvatar(context,uid).getValue(User.class);
        Picasso.with(ChooseActivity.this).load(downloadAvatar(context,uid)).into(iv_picture);*/



        navigation=(NavigationView)findViewById(R.id.navview);
        navigation.setCheckedItem(R.id.nav_first_fragment);
        xulychonmenu(navigation.getMenu().getItem(0));
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                xulychonmenu(menuItem);
                return false;
            }
        });
    }

    public static void setImage(Bitmap bitmap){
        iv_picture.setImageBitmap(bitmap);
    }

    void xulychonmenu(MenuItem menuItem)
    {
        int id=menuItem.getItemId();
        Fragment fragment=null;
        Class classfragment=null;
        if(id == R.id.nav_first_fragment)
            classfragment=FragmentMain.class;
        if(id == R.id.nav_second_fragment)
            classfragment=FragmentAccount.class;
        if(id == R.id.nav_third_fragment)
            classfragment=FragmentSupport.class;
        if(id == R.id.nav_four_fragment)
            classfragment=FragmentRate.class;
        if(id == R.id.nav_five_fragment)
        {
            openChangeDialogChangePass();
            drawer.closeDrawers();
        }
        if(id == R.id.nav_six_fragment)
        {
            openLogoutDialog();
            drawer.closeDrawers();
        }
        if(id==R.id.nav_seven_fragment)
            classfragment=FragmentReview.class;
        try {
            fragment=(Fragment)classfragment.newInstance();
            FragmentManager fmanager= getSupportFragmentManager();
            fmanager.beginTransaction()
                    .replace(R.id.flContent,fragment)
                    .commit();
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            drawer.closeDrawer(GravityCompat.START);
        }catch(Exception e) {
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case R.id.mnLogout:
                mAuth.signOut();
                startActivity(new Intent(ChooseActivity.this, LaunchActivity.class));
                break;*/
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void openChangeDialogChangePass() {
        changepassDiaglog = new ChangepassDiaglog(this);
        changepassDiaglog.show();
    }
    private void openLogoutDialog() {
        logoutDialog = new LogoutDialog(this);
        logoutDialog.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
