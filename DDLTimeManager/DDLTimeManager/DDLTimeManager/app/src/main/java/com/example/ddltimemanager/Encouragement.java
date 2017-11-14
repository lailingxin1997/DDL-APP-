package com.example.ddltimemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Encouragement extends AppCompatActivity {
    private String[] data = {"即使现在，对手也不停地翻动书页","自己选的路，跪着也要把它走完","生活不是等待风暴过去，而是学会在雨中翩翩起舞",
            "不怕，不悔","博观而约取，厚积而薄发"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encouragement_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Encouragement.this,android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
}
