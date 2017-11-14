package com.example.ddltimemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CheckupReview extends AppCompatActivity {

    private String[] data = {"任务1","任务2","任务3","任务4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkup_review_layout);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckupReview.this,android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
}
