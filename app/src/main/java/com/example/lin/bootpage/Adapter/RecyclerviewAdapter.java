package com.example.lin.bootpage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lin.bootpage.Activity.WebActivity;
import com.example.lin.bootpage.BasicClass.History;
import com.example.lin.bootpage.BasicClass.Title;
import com.example.lin.bootpage.R;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private Context mcontext;
    private List<Title> titleList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        View TitleView;
        ImageView ImageView_title_recyclerview;
        TextView TextView_title_recyclerview;
        TextView TextView_time_recyclerview;
        public ViewHolder(View itemView) {
            super(itemView);
            TitleView=itemView;
            ImageView_title_recyclerview= itemView.findViewById(R.id.ImageView_title_recyclerview);
            TextView_title_recyclerview=itemView.findViewById(R.id.TextView_title_recyclerview);
            TextView_time_recyclerview=itemView.findViewById(R.id.TextView_time_recyclerview);
        }
    }

    public RecyclerviewAdapter(List<Title> title) {
        this.titleList=title;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_title,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.TitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Title title=titleList.get(position);
                //添加历史记录
                String name="NoName";
                String newsTitle=title.getTitle();
                String newsUrl=title.getUri();
                List<History> historyList= DataSupport.where("newsTitle like ?", newsTitle).find(History.class);
                if(historyList.size()>0) {
                    goToWebActivity(parent.getContext(),title.getTitle(),title.getUri());
                }else {
                    History history = new History();
                    history.setName(name);
                    history.setNewsTitle(newsTitle);
                    history.setNewsUrl(newsUrl);
                    history.save();
                    goToWebActivity(parent.getContext(),title.getTitle(),title.getUri());
                }
            }
        });
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Title title=titleList.get(position);
        Glide.with(mcontext).load(title.getImageUrl()).into(holder.ImageView_title_recyclerview);
        holder.TextView_title_recyclerview.setText(title.getTitle());
        holder.TextView_time_recyclerview.setText(title.getTime());
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public void goToWebActivity(Context context,String title,String uri){
        //跳转到指定的新闻网页
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

}
