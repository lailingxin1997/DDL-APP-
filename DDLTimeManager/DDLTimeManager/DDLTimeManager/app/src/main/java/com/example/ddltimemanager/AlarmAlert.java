package com.example.ddltimemanager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AlarmAlert extends AppCompatActivity
{
    //是一个放音频的工具
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //这里是判定权限，如果音频有访问权限，就可以播放
        if(ContextCompat.checkSelfPermission(AlarmAlert.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AlarmAlert.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            //有权限访问的话 初始化播放器并开始播放
            this.initMediaPlayer();
            mediaPlayer.start();
        }

        //一个提示你提交任务的对话框
        new AlertDialog.Builder(AlarmAlert.this)
                .setTitle("任务时间到")          //对话框标题
                .setMessage("赶快提交吧")        //对话框内容
                .setPositiveButton("Ogay?",      //对话框的确定按钮
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                AlarmAlert.this.finish();   //按下了就把当前活动中止，那么放歌也就停了
                            }
                        })
                .show();        //对话框要显示出来才有用
    }

    //初始化播放器的操作
    private void initMediaPlayer() {
        try {
            //从手机SD卡根目录下读取mp3格式的音频文件
            File file = new File(Environment.getExternalStorageDirectory(), "热烈的决斗者.mp3");
            //设定好播放器的材料来源
            mediaPlayer.setDataSource(file.getPath());
            //做好播放的准备
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }//由于是文件操作，可能会抛出异常
    }

    @Override   //还是关于申请权限的方法
    public void onRequestPermissionsResult(int requestcode, String[] permissions, int[] grantResults) {
        switch (requestcode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                } break;
            default:
        }
    }

    @Override   //如果退出了当前活动，要把
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            //强制停止播放
            mediaPlayer.stop();
            //释放资源
            mediaPlayer.release();
        }
    }
}