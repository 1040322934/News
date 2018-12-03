package com.example.lin.bootpage.Util;

import android.util.Log;

import com.example.lin.bootpage.BasicClass.News;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    //解析json报文
    public static List<News> handleNewsResponse(String response,String id){
        Gson gson=new Gson();
        List<News> newsList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray(id);
            for(int i=1;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                if(jsonObject1.has("mtime")&&jsonObject1.has("title")&&jsonObject1.has("imgsrc")&&jsonObject1.has("url")) {
                    String newsContent=jsonArray.getJSONObject(i).toString();
                    News news=gson.fromJson(newsContent,News.class);
                    if(!news.url.equals("")) {
                        newsList.add(news);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
