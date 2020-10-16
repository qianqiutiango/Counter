package cn.edu.cn.counter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import cn.edu.cn.counter.domain.Rate;

public class TestAdapter extends ListActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "MyAdapter";

    Handler handler ;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView = getListView();
        super.onCreate(savedInstanceState);
        ArrayList<Rate> listRate= new ArrayList<>();
        SharedPreferences sp =
                getSharedPreferences("rateFromNet", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        Map<String,String> map;
        map = (Map<String, String>) sp.getAll();
        for(Map.Entry<String, String> entry : map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = String.valueOf(entry.getValue());
            Rate rate = new Rate();
            rate.setNation(mapKey);
            rate.setRate(mapValue);
            listRate.add(rate);
        }
        MyAdapter myAdapter = new MyAdapter(this,R.layout.mylist_item, listRate);
        listView.setOnItemClickListener(this);
        setListAdapter(myAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = getListView();
        Object itemAtPosition = listView.getItemAtPosition(position);
        Rate rate = (Rate) itemAtPosition;
        String titleStr = rate.getNation();
        String rateStr = rate.getRate();
        Log.i(TAG,"onItemClick:titleStr="+titleStr);
        Log.i(TAG,"onItemClick:rateStr="+rateStr);
        Intent intent = new Intent(this,Detail.class);
        intent.putExtra("titleStr",titleStr);
        intent.putExtra("rateStr",rateStr);
        startActivity(intent);
//        TextView title = (TextView)view.findViewById(R.id.itemTitle);
//        TextView detail = (TextView) view.findViewById(R.id.itemDetail);


    }



}