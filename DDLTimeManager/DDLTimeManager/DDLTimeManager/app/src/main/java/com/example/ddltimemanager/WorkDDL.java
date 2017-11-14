package com.example.ddltimemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkDDL extends AppCompatActivity implements View.OnClickListener{

    //任务信息输入框
    private EditText inputMessage;

    //时间设置按钮
    private Button setTime;

    //决定任务的按钮
    private Button inputMission;

    //格式化时间字符串
    String time;

    //记录任务们的容器
    ArrayList<String> missions = new ArrayList<>();

    //以当前时间创建的日历
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_ddl_main);

        //实例化控件们 并实现按钮监听
        inputMessage = (EditText) findViewById(R.id.input_message_1);
        inputMission = (Button) findViewById(R.id.input_mission);
        inputMission.setOnClickListener(this);
        setTime = (Button) findViewById(R.id.setTime);  //设置时间按钮
        setTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setTime: {
                //先把时间设置为当前时间
                c.setTimeInMillis(System.currentTimeMillis());

                //当前时间对应的小时和分钟
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                //最关键的时间选择器   参数分别为（当前活动）（设置时间的监听器）（选择器最初指向的小时，分钟）
                new TimePickerDialog(WorkDDL.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            //这里传入的hourOfDay和minute就是监听到当前时间选择器的时与分
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);             //设置时间的方法 设置的是准备提醒的时间 这个时间以Calendar形式保存

                                time = format(hourOfDay, minute);     //设置时间形式的字符串
                                setTime.setText(time);               //让这个字符串显示在设置时间的按钮中
                                time = inputMessage.getText().toString() + ":   " + time;   //这样就获得表示任务的字符串了
                            }
                        }, mHour, mMinute, true).show();
            }
            break;

            case R.id.input_mission:        //点到输入任务按钮了
            {
                missions.add(time);     //由于上一步中的time字符串变成了任务字符串，所以这里表示添加一个任务
                String[] times = new String[missions.size()];   //创建很长的任务数组
                for (int i = 0; i < times.length; i++) {
                    times[i] = "任务" + (i + 1) + "   " + missions.get(i).toString();      //把ArrayList字符数组转换成普通字符数组
                }

                //SharedPreferences保存数据，并提交
                SharedPreferences timesShare = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = timesShare.edit();

                for (int i = 0; i < times.length; i++) {
                    editor.putString("TIME" + (i + 1), times[i]);
                }//把任务数据都存进这个小数据库
                editor.putInt("ItemNumber", times.length);      //保存任务项数
                editor.commit();

                //接下来是定时发送广播的设置 以便在指定的时间能够接收到想要的提醒
                Intent intent = new Intent(WorkDDL.this, CallAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        WorkDDL.this, 0, intent, 0);       //从MainActivity 到CallAlarm的广播发送器
                AlarmManager am;
                am = (AlarmManager) getSystemService(ALARM_SERVICE);    //获取系统服务

                //要实现 时间在前不能提醒  先获取当前时间
                Calendar now = Calendar.getInstance();
                int nowHour = now.get(Calendar.HOUR_OF_DAY);
                int nowMinut = now.get(Calendar.MINUTE);

                //如果设定闹钟的时间在实际时间之后才能响应
                if((c.get(Calendar.HOUR_OF_DAY) > nowHour) || (c.get(Calendar.HOUR_OF_DAY) == nowHour && c.get(Calendar.MINUTE) > nowMinut))
                am.set(AlarmManager.RTC_WAKEUP,         //哪怕是关机状态也可以运行的
                        c.getTimeInMillis(),            //此处设定的时间是用TimePicker决定的
                        sender                          //这个参数就等于要执行的动作
                );

                //为了添加成功任务后弹出一条信息
                new AlertDialog.Builder(this).setTitle("添加成功").setMessage("已添加 " + time + " 任务")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        }
    }

    //格式化时间字符串 **:**
    public String format(int time, int minute)
    {
        if(("" + time).length() == 1 )
            if(("" + minute).length() == 1)
                return "0" + time + ":" + "0" + minute;
            else
                return "0" + time + ":" + minute;
        else
             if(("" + minute).length() == 1)
                  return "" + time + ":" + "0" + minute;
             else
                 return "" + time + ":" + minute;
    }
}
