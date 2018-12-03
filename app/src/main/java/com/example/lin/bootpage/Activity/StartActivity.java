package com.example.lin.bootpage.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lin.bootpage.Adapter.BootAdapter;
import com.example.lin.bootpage.MainActivity;
import com.example.lin.bootpage.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends Activity {
    private ViewPager ViewPager_BootPage_start;
    private List dataList;
    private TextView TextView_first_start;
    private TextView TextView_second_start;
    private TextView TextView_third_start;
    private TextView TextView_fourth_start;
    private Button Button_move_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if(!isFirst()){
            goToMain();
        }
        TextView_first_start=findViewById(R.id.TextView_first_start);
        TextView_second_start=findViewById(R.id.TextView_second_start);
        TextView_third_start=findViewById(R.id.TextView_third_start);
        TextView_fourth_start=findViewById(R.id.TextView_fourth_start);
        Button_move_start=findViewById(R.id.Button_move_start);
        dataList=new ArrayList<>();
        initData();
        TextView_first_start.setSelected(true);
        ViewPager_BootPage_start=findViewById(R.id.ViewPager_BootPage_start);
        ViewPager_BootPage_start.setAdapter(new BootAdapter(this,dataList));
        ViewPager_BootPage_start.setCurrentItem(0);
        ViewPager_BootPage_start.addOnPageChangeListener(new DefineOnPageChangeListener());
        Button_move_start.setOnClickListener(new itemOnClickListener());
    }

    //初始化需要加进ViewPager的数据
    public void initData(){
        dataList.add(R.drawable.boot1);
        dataList.add(R.drawable.boot2);
        dataList.add(R.drawable.boot3);
        dataList.add(R.drawable.boot4);
    }

    //ViewPager的滑动监听
    public class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //滑动时根据滑动页面切换最下面的显示
        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    TextView_first_start.setSelected(true);
                    TextView_second_start.setSelected(false);
                    TextView_third_start.setSelected(false);
                    TextView_fourth_start.setSelected(false);
                    break;
                case 1:
                    TextView_first_start.setSelected(false);
                    TextView_second_start.setSelected(true);
                    TextView_third_start.setSelected(false);
                    TextView_fourth_start.setSelected(false);
                    break;
                case 2:
                    TextView_first_start.setSelected(false);
                    TextView_second_start.setSelected(false);
                    TextView_third_start.setSelected(true);
                    TextView_fourth_start.setSelected(false);
                    break;
                case 3:
                    TextView_first_start.setSelected(false);
                    TextView_second_start.setSelected(false);
                    TextView_third_start.setSelected(false);
                    TextView_fourth_start.setSelected(true);
                    break;
                default:
                    break;
            }
            //判断当前时候最后一个页面，是的话就显示进入主界面的button
            if (position==dataList.size()-1){
                Button_move_start.setVisibility(View.VISIBLE);
            }else {
                Button_move_start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //点击事件集合
    public class itemOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.Button_move_start:
                    goToMain();
            }
        }
    }
    //判断是否第一次启动
    public Boolean isFirst(){
        SharedPreferences pref =getSharedPreferences("data",0);
        Boolean state=pref.getBoolean("state",true);
        if (state){
            pref.edit().putBoolean("state",false).commit();
            return true;
        }else {
            return false;
        }
    }


    //直接进入主界面
    public void goToMain(){
        Context context=StartActivity.this;
        Intent  intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
        finish();
    }

}
