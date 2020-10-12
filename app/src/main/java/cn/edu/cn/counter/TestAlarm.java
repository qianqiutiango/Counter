package cn.edu.cn.counter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TestAlarm extends AppCompatActivity {

    final static long ONE_DAY_MSECOND = 24*60*60*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_alarm);
        Intent intent = new Intent(this, TestList.class);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,);
        AlarmManager aManager=(AlarmManager)getSystemService(Service.ALARM_SERVICE);


        // 启动一个名为getRate的Activity 来获取汇率
        // 设置定时任务，这里使用绝对时间，即使休眠也提醒，程序启动后过一天会启动新的Activity，在这里配置一年的任务

        PendingIntent pi= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),ONE_DAY_MSECOND, pi);


    }



}