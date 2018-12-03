package com.example.lin.bootpage.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.bootpage.BasicClass.Account;
import com.example.lin.bootpage.MainActivity;
import com.example.lin.bootpage.R;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.EditText_name_main)
    EditText EditText_name_main;
    @BindView(R.id.EditText_password_main)
    EditText EditText_password_main;
    @BindView(R.id.CheckBox_remember_main)
    CheckBox CheckBox_remember_main;
    @BindView(R.id.Button_login_main)
    Button Button_login_main;
    @BindView(R.id.TextView_register_main)
    TextView TextView_register_main;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        remember();
    }

    //点击事件集合
    @OnClick({R.id.Button_login_main, R.id.TextView_register_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Button_login_main:
                String name_login=EditText_name_main.getText().toString();
                String password_login=EditText_password_main.getText().toString();
                //对输入的用户名和密码进行查表
                List<Account> accounts= DataSupport.where("name like ? and password like ?",name_login,password_login).find(Account.class);
                if(accounts.size()==0){
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }else {
                    //存储用户名和密码
                    editor=pref.edit();
                    if(CheckBox_remember_main.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("userName",name_login);
                        editor.putString("userPassword",password_login);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    //跳转到主题页面
                    Intent intent=new Intent(this,MainActivity.class);
                    intent.putExtra("name",name_login);
                    intent.putExtra("num",1);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.TextView_register_main:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    //记住密码功能
    public void remember(){
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if (isRemember){
            //如果记住了密码，就将帐号和密码都设置到文本框中
            userName=pref.getString("userName","");
            userPassword=pref.getString("userPassword","");
            EditText_name_main.setText(userName);
            EditText_password_main.setText(userPassword);
            CheckBox_remember_main.setChecked(true);
        }
    }

}
