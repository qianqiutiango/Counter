package cn.edu.cn.counter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;

public class Config extends AppCompatActivity implements Runnable{
    private static final String TAG="LogTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Thread t = new Thread(this);
        t.start();
        Intent get = getIntent();
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        double dollarRate = sp.getFloat("dollar_rate", 6.825f);
        double euroRate = sp.getFloat("euro_rate",7.944f);
        double wonRate = sp.getFloat("won_rate",0.0058f);
        EditText dollar = findViewById(R.id.dollarRate);
        EditText euro = findViewById(R.id.euroRate);
        EditText won = findViewById(R.id.wonRate);
        dollar.setText(String.format("%.4f",dollarRate));
        euro.setText(String.format("%.4f",euroRate));
        won.setText(String.format("%.4f",wonRate));

    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        Log.d(TAG," -- onSaveInstanceState");
//        outState.putString("name","yoosir");
//        outState.putInt("age",24);
//        outState.putBoolean("handsome",true);
//        super.onSaveInstanceState(outState);
//    }
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.d(TAG," -- onRestoreInstanceState");
//        if(savedInstanceState != null) {
//            String name = savedInstanceState.getString("name");
//            int age = savedInstanceState.getInt("age");
//            boolean isHandsome = savedInstanceState.getBoolean("handsome");
//            Log.d(TAG, " -- onRestoreInstanceState get: name = " + name + ",age = " + age + ",handsome = " + isHandsome);
//        }
//    }
    public void save(View btn){

        EditText inputDollarRate = findViewById(R.id.dollarRate);
        EditText inputEuroRate = findViewById(R.id.euroRate);
        EditText inputWonRate = findViewById(R.id.wonRate);

        double dollarRate = Double.parseDouble(inputDollarRate.getText().toString());
        double euroRate = Double.parseDouble(inputEuroRate.getText().toString());
        double wonRate = Double.parseDouble(inputWonRate.getText().toString());

        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate",(float)dollarRate);
        editor.putFloat("euro_rate",(float)euroRate);
        editor.putFloat("won_rate",(float) wonRate);
        editor.apply();
//        // 回传参数
        Intent intent = new Intent();
//        intent.putExtra("dollarRate",dollarRate);
//        intent.putExtra("euroRate",euroRate);
//        intent.putExtra("wonRate",wonRate);
        setResult(RESULT_OK,intent);
        finish();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                EditText inputDollarRate = findViewById(R.id.dollarRate);
                double temp1 = (double) msg.obj;
                inputDollarRate.setText(String.format("%.4f",temp1));
            }
            if(msg.what == 2){
                EditText inputEuroRate = findViewById(R.id.euroRate);
                double temp1 = (double) msg.obj;
                inputEuroRate.setText(String.format("%.4f",temp1));
            }
            if(msg.what == 3){
                EditText inputWonRate = findViewById(R.id.wonRate);
                double temp1 = (double) msg.obj;
                inputWonRate.setText(String.format("%.4f",temp1));
            }
            super.handleMessage(msg);
        }
    };



    @Override
    public void run() {
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        double dollarRate = sp.getFloat("dollar_rate", 6.825f);
        double euroRate = sp.getFloat("euro_rate",7.944f);
        double wonRate = sp.getFloat("won_rate",0.0058f);
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
                if(str1.equals("美元")){
                    dollarRate  = Float.parseFloat(val)/100f;
                }
                if(str1.equals("欧元")){
                    euroRate = Float.parseFloat(val)/100f;
                }
                if(str1.equals("韩元")){
                    wonRate = Float.parseFloat(val)/100f;
                }
                //获取数据并返回……
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 传递消息
        Message dollarMsg = handler.obtainMessage(1);
        Message euroMsg = handler.obtainMessage(2);
        Message wonMsg = handler.obtainMessage(3);
//        dollarMsg.obj = String.valueOf(dollarRate);
//        euroMsg.obj = String.valueOf(euroRate);
//        wonMsg.obj = String.valueOf(wonRate);
        dollarMsg.obj = dollarRate;
        euroMsg.obj = euroRate;
        wonMsg.obj = wonRate;
        handler.sendMessage(dollarMsg);
        handler.sendMessage(euroMsg);
        handler.sendMessage(wonMsg);
    }

    

}
