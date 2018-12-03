package com.example.lin.bootpage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lin.bootpage.Activity.WebActivity;
import com.example.lin.bootpage.BasicClass.History;
import com.example.lin.bootpage.R;

import java.util.List;

public class RecyclerviewAdapter_History extends RecyclerView.Adapter<RecyclerviewAdapter_History.ViewHolder> {
    private Context mcontext;
    private List<History> historyList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TextView_news_his;
        public ViewHolder(View itemView) {
            super(itemView);
            TextView_news_his=itemView.findViewById(R.id.TextView_news_his);
        }
    }

    public RecyclerviewAdapter_History(List<History> historyList) {
        this.historyList=historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_history,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.TextView_news_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                History history=historyList.get(position);
                //跳转到指定的新闻网页
                Intent intent=new Intent(mcontext, WebActivity.class);
                intent.putExtra("uri",history.getNewsUrl());
                mcontext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history=historyList.get(position);
        holder.TextView_news_his.setText(history.getNewsTitle());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
