package com.example.ddltimemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 刘力宾 on 2017/5/19.
 */

public class Login extends BaseActivity implements View.OnClickListener{

    //小数据库 用于记住账号密码时用
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    //用于选择是否记住账号密码的复选框
    private CheckBox rememberPass;

    //账号密码输入
    private EditText accountEdit;
    private EditText passwordEdit;

    //登陆按钮
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);        //注意了 这里先模拟的是登陆界面的布局

        //四个组件就位
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);

        login.setOnClickListener(this);     //按钮监听

        pref = PreferenceManager.getDefaultSharedPreferences(this);     //获取数据库

        boolean isRemember = pref.getBoolean("remember_password", false);   //获取是不是记得密码 默认不记得
        if(isRemember)      //如果记得密码
        {
            String account = pref.getString("account", ""); //获取账户
            String password = pref.getString("password", "");   //获取密码
            accountEdit.setText(account);
            passwordEdit.setText(password);     //把账号密码打印到输入框里
            rememberPass.setChecked(true);      //复选框此时是被选中的
        }
    }

    @Override
    public void onClick(View v) {
        //截取当前的账号密码
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        //账号admin 密码123456 与你输入的进行匹配
        if(account.equals("admin") && password.equals("123456"))
        {
            editor = pref.edit();   //获取数据库
            if(rememberPass.isChecked())    //如果记住账号密码的复选框被选中
            {
                editor.putBoolean("remember_password", true);
                editor.putString("account", account);
                editor.putString("password", password);         //把复选框是否选中 账号 密码都存入数据库里
            }else {
                editor.clear();     //没选中的话就把数据库给清空
            }
            editor.apply();     //提交数据
            Intent intent = new Intent(Login.this, MainActivity.class);     //从登陆界面到主界面
            startActivity(intent);
            finish();
        }else {
                //账号密码错了，要弹出个对话框提示
            new AlertDialog.Builder(this).setTitle("错误").setMessage("夭寿啦，你账号或密码错啦")
            .setPositiveButton("重新输入", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .setNegativeButton("忘记密码", null)
            .show();
        }
    }
}
