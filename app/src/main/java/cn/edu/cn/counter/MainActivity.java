package cn.edu.cn.counter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 美元汇率
    static double dollarRate = 6.825;
    // 欧元汇率
    static double euroRate =  7.944;
    // 韩元汇率
    static double wonRate = 0.0058;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchangerate);
    }
    public void RMB2USD(View view){
        EditText et = findViewById(R.id.inputRMB);

        String flag = et.getText().toString();
        if(flag.equals("")){
            Toast.makeText(this, "请输入RMB", Toast.LENGTH_SHORT).show();
        }
        else {
            EditText inputRMB = findViewById(R.id.inputRMB);
            TextView result = findViewById(R.id.textView3);
            Double temp = Double.parseDouble(inputRMB.getText().toString()) / dollarRate;
            result.setText(String.format("%.4f", temp) + "美元");
        }

    }
    public void RMB2EUR(View view){
        EditText et = findViewById(R.id.inputRMB);

        String flag = et.getText().toString();
        if(flag.equals("")){
            Toast.makeText(this, "请输入RMB", Toast.LENGTH_SHORT).show();
        }
        else {
            EditText inputRMB = findViewById(R.id.inputRMB);
            TextView result = findViewById(R.id.textView3);
            Double temp = Double.parseDouble(inputRMB.getText().toString()) / euroRate;
            result.setText(String.format("%.4f", temp) + "欧元");

        }
    }
    public void RMB2WON(View view){
        EditText et = findViewById(R.id.inputRMB);
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
            Double temp = Double.parseDouble(inputRMB.getText().toString()) /wonRate;
            result.setText(String.format("%.4f", temp) + "韩元");
        }
    }
    public void config(View btn){
        //open activity
        Intent second = new Intent(this,Config.class);
        second.putExtra("dollarRate",dollarRate);
        second.putExtra("euroRate",euroRate);
        second.putExtra("wonRate",wonRate);
        startActivityForResult(second,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        double backDollar = data.getDoubleExtra("dollarRate",dollarRate);
        double backEuro = data.getDoubleExtra("euroRate",euroRate);
        double backWon = data.getDoubleExtra("wonRate",wonRate);
        dollarRate = backDollar;
        euroRate = backEuro;
        wonRate = backWon;

    }
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


