package cn.edu.cn.counter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import cn.edu.cn.counter.domain.Rate;

public class MyAdapter extends ArrayAdapter  {

    private static final String TAG = "MyAdapter";


    public MyAdapter(Context context, int resource, ArrayList<Rate> list) {
        super(context,resource, list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //行控件
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.mylist_item,parent,false);
        }
        Rate rate = (Rate)getItem(position);
        TextView title = itemView.findViewById(R.id.itemTitle);
        TextView detail = itemView.findViewById(R.id.itemDetail);
        title.setText(rate.getNation());
        detail.setText(rate.getRate());
        return itemView;
    }


}