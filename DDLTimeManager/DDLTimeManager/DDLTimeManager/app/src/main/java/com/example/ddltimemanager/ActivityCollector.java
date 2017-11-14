package com.example.ddltimemanager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘力宾 on 2017/5/19.
 * Implemented by 邓怡然 on 2017/5/30.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();    //管理活动类

    //添加一个活动
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    //删除一个活动
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }

    //关掉所有活动
    public static void finishAll()
    {
        for(Activity activity : activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
