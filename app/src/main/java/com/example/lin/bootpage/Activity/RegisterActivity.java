package com.example.lin.bootpage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lin.bootpage.BasicClass.Account;
import com.example.lin.bootpage.R;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.EditText_userName_register)
    EditText EditText_userName_register;
    @BindView(R.id.EditText_password_register)
    EditText EditText_password_register;
    @BindView(R.id.EditText_againPassword_register)
    EditText EditText_againPassword_register;
    @BindView(R.id.Button_register_register)
    Button Button_register_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.Button_register_register)
    public void onViewClicked() {
        String name=EditText_userName_register.getText().toString();
        String password=EditText_password_register.getText().toString();
        String again_password=EditText_againPassword_register.getText().toString();
        Account account=new Account();
        //对用户信息进行查表，判断是否存在相同的用户
        if(password.equals(again_password)&&!password.equals("")&&!name.equals("")) {
            List<Account> accounts= DataSupport.where("name like ? and password like ?",name,password).find(Account.class);
            if(accounts.size()==0) {
                account.setName(name);
                account.setPassword(password);
                account.save();
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"已存在该用户",Toast.LENGTH_LONG).show();
            }
            //判断用户两次输入密码是否一致
        }else if(password.equals("")|name.equals("")) {
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_LONG).show();
        }
    }

}
