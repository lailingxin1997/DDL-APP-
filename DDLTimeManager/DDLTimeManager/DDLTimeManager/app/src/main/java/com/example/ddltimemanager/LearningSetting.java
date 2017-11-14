package com.example.ddltimemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Time;

public class LearningSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_setting_layout);

        Button button_time= (Button)findViewById(R.id.button_time);
        button_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent (LearningSetting.this,TimeSetting.class);
                startActivity(intent);
            }
        });
        Button edit_text_1_1_7= (Button)findViewById(R.id.edit_text_1_1_7);
        edit_text_1_1_7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent (LearningSetting.this,LabelSetting.class);
                startActivity(intent);
            }
        });

    }



}
