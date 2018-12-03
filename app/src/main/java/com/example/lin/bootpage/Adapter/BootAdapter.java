package com.example.lin.bootpage.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.lin.bootpage.R;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class BootAdapter extends PagerAdapter{
    private Context mcontext;
    private List mdata;
    private ImageView ImageView_bootImg_basic;

    public BootAdapter(Context context,List data){
        this.mcontext=context;
        this.mdata=data;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    //绑定ViewPager的布局
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=View.inflate(mcontext,R.layout.viewpager_basic,null);
        ImageView_bootImg_basic=view.findViewById(R.id.ImageView_bootImg_basic);
        Glide.with(mcontext).load(mdata.get(position)).into(ImageView_bootImg_basic);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
