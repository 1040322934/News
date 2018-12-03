package com.example.lin.bootpage.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.example.lin.bootpage.Adapter.RecyclerviewAdapter;
import com.example.lin.bootpage.BasicClass.News;
import com.example.lin.bootpage.BasicClass.Title;
import com.example.lin.bootpage.R;
import com.example.lin.bootpage.Util.HttpUtil;
import com.example.lin.bootpage.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecommendFragment extends Fragment {
    @BindView(R.id.RecyclerView_recommend)
    RecyclerView RecyclerView_recommend;
    @BindView(R.id.LinearLayout_ProgressBar_interesting)
    LinearLayout LinearLayout_ProgressBar_interesting;
    @BindView(R.id.EasyRefreshLayout_recommend)
    EasyRefreshLayout EasyRefreshLayout_recommend;
    private List<Title> titleList;
    private RecyclerviewAdapter recyclerviewAdapter;
    //定义网页请求序号
    int num = 0;
    int num2 = num + 20;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_recommend, container, false);
        ButterKnife.bind(this, view);
        titleList = new ArrayList<>();
        initView();
        return view;
    }

    public void initView() {
        requestNews();
        recyclerviewAdapter = new RecyclerviewAdapter(titleList);
        RecyclerView_recommend.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView_recommend.setAdapter(recyclerviewAdapter);
        //刷新事件
        EasyRefreshLayout_recommend.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                num=num+20;
                requestNews();
                EasyRefreshLayout_recommend.loadMoreComplete();
            }

            @Override
            public void onRefreshing() {
                num=0;
                titleList.clear();
                requestNews();
                EasyRefreshLayout_recommend.refreshComplete();
            }
        });
    }


    //请求新闻信息
    public void requestNews() {
        String newsUrl = getString(R.string.recommendUrl) + num + "-" + num2 + ".html";
        HttpUtil.sendOkHttpRequest(newsUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取新闻信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<News> newsList = new ArrayList<>();
                final String responseText = response.body().string();
                newsList = Utility.handleNewsResponse(responseText, "T1467284926140");
                for (News news : newsList) {
                    Title title = new Title(news.title, news.time, news.imgsrc, news.url);
                    titleList.add(title);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout_ProgressBar_interesting.setVisibility(View.INVISIBLE);
                        recyclerviewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
