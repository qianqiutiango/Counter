package cn.edu.cn.counter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Detail extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Intent get = getIntent();
        final String currency = get.getStringExtra("titleStr");
        String temp = get.getStringExtra("rateStr");
        final Double rate = Double.parseDouble(temp);
        //币种
        TextView currTv = (TextView) findViewById(R.id.currence);
        EditText input = (EditText) findViewById(R.id.inputNew);
        currTv.setText(currency);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView show = findViewById(R.id.showResult);
                if(s.toString().equals("")) {
                    Toast.makeText(Detail.this,"请输入RMB",Toast.LENGTH_SHORT).show();
                    show.setText("");
                }
                else if(!isNumeric(s.toString())){
                    Toast.makeText(Detail.this,"请输入数字",Toast.LENGTH_SHORT).show();
                }
                else {
                    Double temp = (Double.parseDouble(s.toString())/rate)*100;
                    show.setText(String.format("%.4f", temp) + currency);
                }


            }
        };
        input.addTextChangedListener(textWatcher);

    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}