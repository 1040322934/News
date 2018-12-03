package com.example.lin.bootpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.bootpage.Activity.CollectionActivity;
import com.example.lin.bootpage.Activity.HistoryActivity;
import com.example.lin.bootpage.Activity.LoginActivity;
import com.example.lin.bootpage.BasicClass.Collection;
import com.example.lin.bootpage.BasicClass.UserName;
import com.example.lin.bootpage.Fragment.NewsFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_view)
    NavigationView nav_View;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar_main;
    @BindView(R.id.DrawerLayout_main)
    DrawerLayout DrawerLayout_main;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TextView name_header;
    //定义第一次按退出按键的时间
    private long firstTime=0;
    private String name;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //融合状态栏
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.coloDark));
        }
        initView();
        getData();
        replaceFragment(new NewsFragment());
    }


    //初始化控件
    public void initView() {
        setSupportActionBar(toolbar_main);
        //改变Toolbar的默认后退按钮的样式和用处，变为滑动菜单栏的按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.other);
        }
        //设置抽屉头部的点击事件
        View headView = nav_View.inflateHeaderView(R.layout.nav_header);
        name_header = headView.findViewById(R.id.name_header);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // 为活动菜单的选项添加点击事件
        nav_View.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.history_nav:
                        Intent intent=new Intent(MainActivity.this, HistoryActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    case R.id.collection_nav:
                        Intent intent2=new Intent(MainActivity.this, CollectionActivity.class);
                        MainActivity.this.startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }


    //为Toolbar的按键添加点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DrawerLayout_main.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }


    //接收LoginActivity传递过来的登录信息
    public void getData() {
        Intent intent = getIntent();
        int num = intent.getIntExtra("num", 0);
        if (num == 1) {
            name = intent.getStringExtra("name");
            UserName userName=new UserName(name);
            EventBus.getDefault().postSticky(userName);
            name_header.setText(name);
        }
    }

    //该方法用于切换不同的Fragment
    public void replaceFragment(Fragment fragment) {
        //定义Fragment事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //进行Fragment替换
        ft.replace(R.id.FrameLayout_displayFragment_main, fragment);
        //提交事务
        ft.commit();
    }

    //重写退出按键的事件，改为按两次退出键才会退出程序
    @Override
    public void onBackPressed() {
        long secondTime=System.currentTimeMillis();
        //如果两次按键时间超过1000毫秒则不退出
        if(secondTime-firstTime>1000){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            firstTime=secondTime;
        }else {
            finish();
        }
    }

}
