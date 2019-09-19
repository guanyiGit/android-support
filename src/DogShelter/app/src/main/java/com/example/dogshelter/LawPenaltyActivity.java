package com.example.dogshelter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.datepicker.CustomDatePicker;
import com.example.datepicker.DateFormatUtils;

import java.util.ArrayList;

public class LawPenaltyActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView turnback;//返回
    private TextView mTvSelectedDate, mTvSelectedTime;
    private CustomDatePicker mDatePicker, mTimerPicker;
    private Spinner illegal;//违法行为
    private TextView deduction;//扣分
    private int num;//分数
    private Button yes;//是否收容是/否
    private Button no;
    private static final String TAG = "LawPenaltyActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_penalty);
        turnback=(ImageView)findViewById(R.id.turnback);
        deduction=(TextView) findViewById(R.id.deduction);//扣分
        yes=(Button)findViewById(R.id.yes);//是否收容是/否
        no=(Button)findViewById(R.id.no);
        yes.setOnClickListener(this);
        findViewById(R.id.no).setOnClickListener(this);

        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        findViewById(R.id.ll_date).setOnClickListener(this);
//        mTvSelectedDate = findViewById(R.id.tv_selected_date);
//        initDatePicker();

        findViewById(R.id.ll_time).setOnClickListener(this);
        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        initTimerPicker();


        illegal=(Spinner)findViewById(R.id.illegal);
        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("溜犬不及时清理犬只粪便");
        spinners.add("测试一");
        spinners.add("测试二");
//设置ArrayAdapter内置的item样式-这里是单行显示样式
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        //这里设置的是Spinner的样式 ， 输入 simple_之后会提示有4人，如果专属spinner的话应该是俩种，在特殊情况可自己定义样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //设置Adapter了
        illegal.setAdapter(adapter);
        illegal.setDropDownVerticalOffset(80);
        //修改背景 (带圆角的背景)
//        illegal.setPopupBackgroundResource(R.mipmap.foot);
        //监听Spinner的操作
        illegal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //选取时候的操作
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mTv.setText(adapter.getItem(position));
            }
            //没被选取时的操作
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                mTv.setText("No anything");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_date:
//                // 日期格式为yyyy-MM-dd
//                mDatePicker.show(mTvSelectedDate.getText().toString());
//                break;

            case R.id.ll_time:
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mTvSelectedTime.getText().toString());
                break;

            case R.id.yes:
               yes.setBackgroundResource(R.drawable.button_selector_exchange);
                no.setBackgroundResource(R.drawable.button_selector);
                break;
            case R.id.no:
                yes.setBackgroundResource(R.drawable.button_selector);
                no.setBackgroundResource(R.drawable.button_selector_exchange);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();

        mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }



//    减
    public void iv_1(View view){
        num=Integer.parseInt(deduction.getText().toString());
        if(num>0){
            num--;
        }
        Log.i(TAG, "测试数字减号"+num);
        deduction.setText(String.valueOf(num));
    }
//    加
    public void iv_2(View view){
        num=Integer.parseInt(deduction.getText().toString());
        if(num<10){
            num++;
        }
        Log.i(TAG, "测试数字加号"+num);
        deduction.setText(String.valueOf(num));
    }






}
