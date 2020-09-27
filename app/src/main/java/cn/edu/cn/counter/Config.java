package cn.edu.cn.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent get = getIntent();
        double dollarRate = get.getDoubleExtra("dollarRate",MainActivity.dollarRate);
        double euroRate = get.getDoubleExtra("euroRate",MainActivity.euroRate);
        double wonRate = get.getDoubleExtra("wonRate",MainActivity.wonRate);
        EditText dollar = findViewById(R.id.dollarRate);
        EditText euro = findViewById(R.id.euroRate);
        EditText won = findViewById(R.id.wonRate);
        dollar.setText(String.valueOf(dollarRate));
        euro.setText(String.valueOf(euroRate));
        won.setText(String.valueOf(wonRate));

    }
    public void save(View btn){

        EditText inputDollarRate = findViewById(R.id.dollarRate);
        EditText inputEuroRate = findViewById(R.id.euroRate);
        EditText inputWonRate = findViewById(R.id.wonRate);

        double dollarRate = Double.parseDouble(inputDollarRate.getText().toString());
        double euroRate = Double.parseDouble(inputEuroRate.getText().toString());
        double wonRate = Double.parseDouble(inputWonRate.getText().toString());
        // 回传参数
        Intent intent = new Intent();
        intent.putExtra("dollarRate",dollarRate);
        intent.putExtra("euroRate",euroRate);
        intent.putExtra("wonRate",wonRate);
        setResult(RESULT_OK,intent);
        finish();
    }
}