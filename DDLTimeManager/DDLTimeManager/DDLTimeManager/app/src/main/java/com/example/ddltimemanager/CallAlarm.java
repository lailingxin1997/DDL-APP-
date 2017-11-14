package com.example.ddltimemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 刘力宾 on 2017/5/20.
 */

public class CallAlarm extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent i = new Intent(context, AlarmAlert.class);   //该接收器决定开往哪个新活动

        //这几句我也没看懂 但是没有不行
        Bundle bundleRet = new Bundle();
        bundleRet.putString("STR_CALLER", "");
        i.putExtras(bundleRet);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);       //跳转提醒活动中
    }
}
