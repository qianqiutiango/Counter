package cn.edu.cn.counter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class gainData extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t = new Thread(this);
        t.start();

    }

    @SuppressLint("WrongConstant")
    @Override
    public void run() {
//        Toast.makeText(this,"已更新汇率",Toast.LENGTH_SHORT).show();
        List listItems = new ArrayList<HashMap<String,String>>();
        Log.i("MyTag","获取汇率");
        //处理数据实验
        String url = "https://www.usd-cny.com/bankofchina.htm";

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "run: " + doc.title());
        Elements tables = doc.getElementsByTag("table");
        Element table6 = tables.get(0);
        //获取TD中的数据
        Elements tds = table6.getElementsByTag("td");
        //将获取数据存入文件中
        SharedPreferences sp = getSharedPreferences("rateFromNet", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        for(int i=0;i<tds.size();i+=6){
            Element td1 = tds.get(i);
            Element td2 = tds.get(i+5);
            String str1 = td1.text();
            String val = td2.text();
            HashMap<String,String> map = new HashMap<>();
            map.put("ItemTitle",str1);
            map.put("ItemDetail",val);
            listItems.add(map);
            //数据存储
            ed.putFloat(str1, Float.valueOf(val));
            //获取数据并返回……
        }
        ed.commit();
        Log.i("TAG","currentTime:"+System.currentTimeMillis());

    }
}