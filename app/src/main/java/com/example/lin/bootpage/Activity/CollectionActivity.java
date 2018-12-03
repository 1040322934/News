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
import android.view.MenuItem;
import android.view.View;

import com.example.lin.bootpage.Adapter.RecyclerViewAdapter_Collection;
import com.example.lin.bootpage.Adapter.RecyclerviewAdapter_History;
import com.example.lin.bootpage.BasicClass.Collection;
import com.example.lin.bootpage.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_collection)
    Toolbar toolbar_collection;
    @BindView(R.id.RecyclerView_collection)
    RecyclerView RecyclerView_collection;
    private RecyclerViewAdapter_Collection adapter_collection;
    private List<Collection> collectionList;
    private String name="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        //注册EventBus
        EventBus.getDefault().register(this);
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
        setSupportActionBar(toolbar_collection);
        //改变Toolbar的默认后退按钮的样式和用处，变为滑动菜单栏的按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //为Toolbar的按键添加点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_collect:

            default:
                break;
        }
        return true;
    }

    //设置接受的EventBus操作
    @Subscribe(sticky = true)
    public void onEvent(com.example.lin.bootpage.BasicClass.UserName userName) {
        name = userName.getName();
    }

    public void initHistory() {
        collectionList = DataSupport.where("name like ?", name).find(Collection.class);
        adapter_collection= new RecyclerViewAdapter_Collection(collectionList);
        RecyclerView_collection.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView_collection.setAdapter(adapter_collection);
        RecyclerView_collection.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
