package com.example.ddltimemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CheckMission extends AppCompatActivity implements View.OnClickListener{

    private static int itemNumber = 0;      //项目个数

    private SharedPreferences pref;         //数据库 从WorkDDL中获取数据的
    private SharedPreferences.Editor editor;
    
    private Button clearAll;    //用于清空所有任务项的按钮
    String[] data;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_mission_layout);

        clearAll = (Button) findViewById(R.id.deleteAll);
        clearAll.setOnClickListener(this);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(CheckMission.this);
        itemNumber = pref.getInt("ItemNumber", 0);  //获取任务项数
        String[] data = new String[itemNumber];
        for(int i = 0; i < itemNumber; i++)
        {
            data[i] = pref.getString("TIME" + (i+1), "");
        }

        adapter = new ArrayAdapter<String>(CheckMission.this,android.R.layout.simple_list_item_1, data);
        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        listView.setAdapter(null);
    }
}
