package cn.edu.cn.counter;

import android.annotation.SuppressLint;
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

import cn.edu.cn.counter.db.RateManager;
import cn.edu.cn.counter.domain.RateItem;

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
        RateManager rateManager = new RateManager(this);
        //表中有数据
        if(rateManager.HaveData("tb_rates")){
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();

                //更新数据
                rateManager.updateByName(str1,val);
                Log.i("币种：",str1+" 更新成功");
            }
        }
        else{
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                //币种
                String str1 = td1.text();
                //汇率
                String val = td2.text();
                RateItem rateItem = new RateItem();
                rateItem.setCurName(str1);
                rateItem.setCurRate(val);
                //插入数据
                rateManager.add(rateItem);
                Log.i("币种：",str1+" 插入成功");
            }
        }



        Log.i("TAG","currentTime:"+System.currentTimeMillis());

    }
}