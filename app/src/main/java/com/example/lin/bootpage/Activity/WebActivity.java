package com.example.lin.bootpage.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lin.bootpage.BasicClass.Collection;
import com.example.lin.bootpage.BasicClass.History;
import com.example.lin.bootpage.BasicClass.UserName;
import com.example.lin.bootpage.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_collect_web)
    Toolbar toolbar_collect_web;
    //private SwipeRefreshLayout swipeRefresh;
    private WebView webView;
    private String UserName = "null";
    private String title;
    private String url;
    private List<Collection> collectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
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
        loadUrl();
    }

    public void initView() {
        setSupportActionBar(toolbar_collect_web);
        //改变Toolbar的默认后退按钮的样式和用处，变为滑动菜单栏的按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //加载网页逻辑
    public void loadUrl(){
        Intent intent = getIntent();
        url = intent.getStringExtra("uri");
        title=intent.getStringExtra("title");
        webView = findViewById(R.id.webview_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
//        swipeRefresh = findViewById(R.id.swipe_web);
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//        //设置当webview滑动到顶部才会触发刷新事件
//        swipeRefresh.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
//                return webView.getScrollY() > 0;
//            }
//        });
//        //为下滑刷新设置监听事件
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                webView.loadUrl(webView.getUrl());
//                swipeRefresh.setRefreshing(false);
//            }
//        });
    }

    //将按钮布局添加到Toolbar上
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_collection,menu);
        return true;
    }

    //为Toolbar的按键添加点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_collect:
                if(UserName.equals("null")){
                    Toast.makeText(this,"你还没有登录",Toast.LENGTH_SHORT).show();
                }else {
                    collectionList=DataSupport.where("name like ? and newsTitle like ?",UserName,title).find(Collection.class);
                    if(collectionList.size()>0){
                        Toast.makeText(this,"该新闻已经收藏过啦~",Toast.LENGTH_SHORT).show();
                    }else {
                        Collection collection=new Collection();
                        collection.setName(UserName);
                        collection.setNewsTitle(title);
                        collection.setNewsUrl(url);
                        collection.save();
                        Toast.makeText(this,"收藏成功~",Toast.LENGTH_SHORT).show();
                    }
                }
            default:
                break;
        }
        return true;
    }

    public void StartActivity(Context context) {
        Intent intent = new Intent(context, WebActivity.class);
        context.startActivity(intent);
    }

    //设置接受的EventBus操作
    @Subscribe(sticky = true)
    public void onEvent(com.example.lin.bootpage.BasicClass.UserName userName) {
        UserName = userName.getName();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
