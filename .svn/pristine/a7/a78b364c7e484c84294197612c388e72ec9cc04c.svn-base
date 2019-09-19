package com.example.tooltype;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        ActivityCollerctor.addActivity(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollerctor.removeActivity(this);
    }

}
