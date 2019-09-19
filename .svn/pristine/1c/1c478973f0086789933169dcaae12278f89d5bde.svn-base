package com.example.dogshelter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tooltype.StringUtils;
import com.rscja.deviceapi.RFIDWithUHF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private ImageView button1;//犬只查询
    private ImageView button2;//执法巡查
    private ImageView button3;//流浪犬收容
    private ImageView button4;//我的消息
    private ImageView doset;//设置
    private static final String TAG = "HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//去掉标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉导航栏
        setContentView(R.layout.activity_home_page);
        button1=(ImageView)findViewById(R.id.button1);
        button2=(ImageView)findViewById(R.id.button2);
        button3=(ImageView)findViewById(R.id.button3);
        button4=(ImageView)findViewById(R.id.button4);
        doset=(ImageView)findViewById(R.id.doset);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,DogInquiry.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,UHTReadTag.class);
                startActivity(intent);
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,HomeladDogShelter.class);
                startActivity(intent);
            }
        });



        doset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,SetUpActivity.class);
                startActivity(intent);
            }
        });


    }




















}
