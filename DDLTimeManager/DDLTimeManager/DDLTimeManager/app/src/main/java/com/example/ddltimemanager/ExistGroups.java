package com.example.ddltimemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExistGroups extends AppCompatActivity {

    private String[] data = {"1.软件工程六班", "2.程序员学习俱乐部", "3.高数讨论小组"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exist_groups_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExistGroups.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView)findViewById(R.id.list_2_item);
        listView.setAdapter(adapter);
    }
}
