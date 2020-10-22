package cn.edu.cn.counter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cn.counter.db.RateManager;
import cn.edu.cn.counter.domain.RateItem;

public class TestAdapter extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "MyAdapter";

    Handler handler ;
    MyAdapter myAdapter ;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView = getListView();
        super.onCreate(savedInstanceState);
        RateManager rateManager = new RateManager(this);
        List result;
        result = rateManager.getAll();
        MyAdapter myAdapter = new MyAdapter(this,
                R.layout.mylist_item,
                (ArrayList<RateItem>) result);
        setListAdapter(myAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        setListAdapter(myAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = getListView();

        Object itemAtPosition = listView.getItemAtPosition(position);
        RateItem rate = (RateItem) itemAtPosition;
        String titleStr = rate.getCurName();
        String rateStr = rate.getCurRate();
        Log.i(TAG,"onItemClick:titleStr="+titleStr);
        Log.i(TAG,"onItemClick:rateStr="+rateStr);
        Intent intent = new Intent(this,Detail.class);
        intent.putExtra("titleStr",titleStr);
        intent.putExtra("rateStr",rateStr);
        startActivity(intent);



    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final ListView listView = getListView();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"onItemLongClick:对话框事件处理");
                myAdapter.remove(listView.getItemAtPosition(position));
            }
        }).setNegativeButton("否",null);
        builder.create().show();


        return true;
    }
}