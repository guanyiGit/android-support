package com.example.dogshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SetUpActivity extends AppCompatActivity {
    private ImageView turnback;//返回
    private ImageView my_message;//我的信息
    private ImageView change_password;//修改密码
    private TextView logout;//退出登陆


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        my_message=(ImageView)findViewById(R.id.my_message);
        change_password=(ImageView)findViewById(R.id.change_password);
        logout=(TextView)findViewById(R.id.logout);
        turnback=(ImageView)findViewById(R.id.turnback);

        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        my_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetUpActivity.this,SingleMessageActivity.class);
                startActivity(intent);
            }
        });


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetUpActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });











    }
}
