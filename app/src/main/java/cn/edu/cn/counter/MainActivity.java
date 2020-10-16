package cn.edu.cn.counter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="LogTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchangerate);
        Intent intent = new Intent(this, gainData.class);
//         进行闹铃注册
        AlarmManager aManager=(AlarmManager)getSystemService(Service.ALARM_SERVICE);

        PendingIntent pi= PendingIntent.getActivity(this, 0, intent, 0);

        aManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pi);
//        aManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000,AlarmManager.INTERVAL_DAY, pi);

    }
    public void RMB2USD(View view){
        EditText et = findViewById(R.id.inputRMB);
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        String flag = et.getText().toString();
        if(flag.equals("")){
            Toast.makeText(this, "请输入RMB", Toast.LENGTH_SHORT).show();
        }
        else {
            EditText inputRMB = findViewById(R.id.inputRMB);
            TextView result = findViewById(R.id.textView3);
            Double temp = Double.parseDouble(inputRMB.getText().toString()) / sp.getFloat("dollar_rate",6.825f);
            result.setText(String.format("%.4f", temp) + "美元");
        }

    }
    public void RMB2EUR(View view){
        EditText et = findViewById(R.id.inputRMB);
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        String flag = et.getText().toString();
        if(flag.equals("")){
            Toast.makeText(this, "请输入RMB", Toast.LENGTH_SHORT).show();
        }
        else {
            EditText inputRMB = findViewById(R.id.inputRMB);
            TextView result = findViewById(R.id.textView3);
            Double temp = Double.parseDouble(inputRMB.getText().toString()) /sp.getFloat("euro_rate",7.944f);
            result.setText(String.format("%.4f", temp) + "欧元");

        }
    }
    public void RMB2WON(View view){
        EditText et = findViewById(R.id.inputRMB);
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        String flag = et.getText().toString();
        if(flag.equals("")){
            Toast.makeText(this, "请输入RMB", Toast.LENGTH_SHORT).show();
            // 弹窗方法
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("警告");
//            builder.setMessage("请输入RMB");
//            builder.setPositiveButton("确定",null);
//            builder.show();
        }
        else {
            EditText inputRMB = findViewById(R.id.inputRMB);
            TextView result = findViewById(R.id.textView3);
            Double temp = Double.parseDouble(inputRMB.getText().toString()) /sp.getFloat("won_rate",0.0058f);
            result.setText(String.format("%.4f", temp) + "韩元");
        }
    }
    public void config(View btn){
        //open activity
        Intent second = new Intent(this,Config.class);
        startActivity(second);
    }
    public void showRateList(View btn){
        //open activity
        Intent second = new Intent(this,TestList.class);
        startActivity(second);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String result = ((TextView) findViewById(R.id.textView3)).getText().toString();
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putString("result", result);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String result = savedInstanceState.getString("result");
        Log.i(TAG, "onRestoreInstanceState: ");
        ((TextView)findViewById(R.id.textView3)).setText(result);
    }
    // 使用配置文件读取数据，就不需要回传参数了
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        double backDollar = data.getDoubleExtra("dollarRate",dollarRate);
//        double backEuro = data.getDoubleExtra("euroRate",euroRate);
//        double backWon = data.getDoubleExtra("wonRate",wonRate);
//        dollarRate = backDollar;
//        euroRate = backEuro;
//        wonRate = backWon;
//
//    }
    //重写onCreateOptionMenu(Menu menu)方法，当菜单第一次被加载时调用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //填充选项菜单（读取XML文件、解析、加载到Menu组件上）
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    //重写OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件（根据id来区分是哪个item）
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.firstItem:
                Toast.makeText(this, "第一个选项栏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.secondItem:
                Toast.makeText(this, "第二个选项栏", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}


