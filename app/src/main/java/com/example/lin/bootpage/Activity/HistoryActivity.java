package com.example.lin.bootpage.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lin.bootpage.Adapter.RecyclerviewAdapter_History;
import com.example.lin.bootpage.BasicClass.History;
import com.example.lin.bootpage.R;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.RecyclerView_history)
    RecyclerView RecyclerView_history;
    @BindView(R.id.toolbar_history)
    Toolbar toolbar_history;
    private RecyclerviewAdapter_History adapter_his_col;
    private List<History>  historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        //融合状态栏
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.coloDark));
        }
        initView();
        initHistory();
    }

    public void initView(){
        setSupportActionBar(toolbar_history);
        //改变Toolbar的默认后退按钮的样式和用处，变为滑动菜单栏的按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //将按钮布局添加到Toolbar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_history,menu);
        return true;
    }

    //为Toolbar的按键添加点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_delete_history:
                DataSupport.deleteAll(History.class,"name=?","NoName");
                historyList.clear();
                adapter_his_col.notifyDataSetChanged();
            default:
                break;
        }
        return true;
    }


    public void initHistory() {
        historyList = DataSupport.where("name like ?", "NoName").find(History.class);
        adapter_his_col= new RecyclerviewAdapter_History(historyList);
        RecyclerView_history.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView_history.setAdapter(adapter_his_col);
        RecyclerView_history.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
