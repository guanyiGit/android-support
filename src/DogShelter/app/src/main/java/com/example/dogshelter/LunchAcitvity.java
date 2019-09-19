package com.example.dogshelter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LunchAcitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//去掉标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉导航栏
        setContentView(R.layout.activity_lunch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LunchAcitvity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

//        Activity跳转动画
//        goMainActivity();
    }


    public void goMainActivity(){
        startActivity(new Intent(LunchAcitvity.this,MainActivity.class));
        overridePendingTransition(R.anim.base_slide_right_in,R.anim.base_slide_right_out);
        finish();
    }


}
