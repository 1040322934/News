package com.example.lin.bootpage.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lin.bootpage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsFragment extends Fragment {
    @BindView(R.id.ViewPager_fragment_news)
    ViewPager ViewPager_fragment_news;
    @BindView(R.id.TextView_recommend_Fragment)
    TextView TextView_recommend_Fragment;
    @BindView(R.id.TextView_entertainment_Fragment)
    TextView TextView_entertainment_Fragment;
    @BindView(R.id.TextView_sport_Fragment)
    TextView TextView_sport_Fragment;
    private List<Fragment> fragmentList;
    private EntertainmentFragment entertainmentFragment;
    private RecommendFragment recommendFragment;
    private SportFragment sportFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavigation();
        TextView_recommend_Fragment.setSelected(true);
    }

    public void initNavigation() {
        recommendFragment = new RecommendFragment();
        entertainmentFragment = new EntertainmentFragment();
        sportFragment = new SportFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(recommendFragment);
        fragmentList.add(entertainmentFragment);
        fragmentList.add(sportFragment);
        ViewPager_fragment_news.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        ViewPager_fragment_news.setCurrentItem(0);
        ViewPager_fragment_news.addOnPageChangeListener(new DefineOnPageChangeListener());
    }

    //设置导航栏的点击事件
    @OnClick({R.id.TextView_recommend_Fragment, R.id.TextView_entertainment_Fragment,  R.id.TextView_sport_Fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.TextView_recommend_Fragment:
                ViewPager_fragment_news.setCurrentItem(0);
                break;
            case R.id.TextView_entertainment_Fragment:
                ViewPager_fragment_news.setCurrentItem(1);
                break;
            case R.id.TextView_sport_Fragment:
                ViewPager_fragment_news.setCurrentItem(2);
                break;
        }
    }



    //ViewPager滑动监听
    public class DefineOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                //要闻
                case 0:
                    TextView_recommend_Fragment.setSelected(true);
                    TextView_entertainment_Fragment.setSelected(false);
                    TextView_sport_Fragment.setSelected(false);
                    break;
                case 1:
                    TextView_recommend_Fragment.setSelected(false);
                    TextView_entertainment_Fragment.setSelected(true);
                    TextView_sport_Fragment.setSelected(false);
                    break;
                case 2:
                    TextView_recommend_Fragment.setSelected(false);
                    TextView_entertainment_Fragment.setSelected(false);
                    TextView_sport_Fragment.setSelected(true);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
