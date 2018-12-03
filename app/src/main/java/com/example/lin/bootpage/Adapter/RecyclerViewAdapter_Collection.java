package com.example.lin.bootpage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.bootpage.Activity.WebActivity;
import com.example.lin.bootpage.BasicClass.Collection;
import com.example.lin.bootpage.BasicClass.History;
import com.example.lin.bootpage.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RecyclerViewAdapter_Collection extends RecyclerView.Adapter<RecyclerViewAdapter_Collection.ViewHolder> {
    private Context mcontext;
    private List<Collection> collectionList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TextView_news_collection;
        public ViewHolder(View itemView) {
            super(itemView);
            TextView_news_collection=itemView.findViewById(R.id.TextView_news_collection);
        }
    }

    public RecyclerViewAdapter_Collection(List<Collection> collectionList) {
        this.collectionList=collectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext==null){
            mcontext=parent.getContext();
        }
        View view= LayoutInflater.from(mcontext).inflate(R.layout.recyclerview_collection,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        //点击事件
        viewHolder.TextView_news_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Collection collection=collectionList.get(position);
                //跳转到指定的新闻网页
                Intent intent=new Intent(mcontext, WebActivity.class);
                intent.putExtra("uri",collection.getNewsUrl());
                mcontext.startActivity(intent);
            }
        });
        //长按事件
        viewHolder.TextView_news_collection.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Collection collection=collectionList.get(position);
                String name=collection.getName();
                String title=collection.getNewsTitle();
                DataSupport.deleteAll(Collection.class,"name like ? and newsTitle like ?",name,title);
                collectionList.remove(collection);
                notifyDataSetChanged();
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collection collection=collectionList.get(position);
        holder.TextView_news_collection.setText(collection.getNewsTitle());
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }
}
