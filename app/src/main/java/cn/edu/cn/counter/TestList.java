package cn.edu.cn.counter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestList extends ListActivity implements Runnable {
    private static final String TAG="LogTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_list);
        List listItems = new ArrayList<HashMap<String,String>>();
        SharedPreferences sp = getSharedPreferences("rateFromNet", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.getDefaultSharedPreferences(this);
        Map<String,String> map = new HashMap<String,String>();
        map = (Map<String, String>) sp.getAll();
        for(Map.Entry<String, String> entry : map.entrySet()){
            HashMap<String,String> mapTemp = new HashMap<>();
            String mapKey = entry.getKey();
            String mapValue = String.valueOf(entry.getValue());
            mapTemp.put("ItemTitle",mapKey);
            mapTemp.put("ItemDetail",mapValue);
            listItems.add(mapTemp);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.mylist_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
        setListAdapter(listItemAdapter);

        // 使用控件完成列表创建的方式
//        ListView listView = (ListView) findViewById(R.id.mylist_item);
//        String data[] = {"one","two","three","four"};
//        ListAdapter adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,data);
//        listView.setAdapter(adapter);


    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                List<String> rateList = (List<String>) msg.obj;
                ListAdapter adapter = new ArrayAdapter<String>(
                        TestList.this,
                        android.R.layout.simple_list_item_1,
                        rateList);
                setListAdapter(adapter);
            }

            super.handleMessage(msg);
        }
    };
    @Override
    public void run() {
        List<String> result = new ArrayList<String>();
        //获取网络数据
        try {
            String url = "https://www.usd-cny.com/bankofchina.htm";
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(0);
            //获取TD中的数据
            Elements tds = table.getElementsByTag("td" );
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, str1 + "==>" + val);
                result.add(str1 + "==>" + val);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 传递消息
        Message returnList = handler.obtainMessage(1);
        returnList.obj = result;
        handler.sendMessage(returnList);

    }
}