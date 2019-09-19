package com.example.dogshelter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.model.DataHolder;
import com.example.model.DogDetailsInfo;
import com.example.model.DogType;
import com.example.selfadapter.EnforcementAdatper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LawEnforcement extends AppCompatActivity {
    private List<DogType> dogTypeList = new ArrayList<>();
    public List<String> dog_list = new ArrayList<String>();// 存储已有设备id的犬牌号
    private AlertDialog alertDialog = null;
    Map<String, String> map = new HashMap<String, String>();//存储对应犬只的信息
    private ImageView turnback;//返回
    private DogDetailsInfo dogDetailsInfo;
    private static final String TAG = "LawEnforcement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_enforcement);
        turnback = (ImageView) findViewById(R.id.turnback);
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LawEnforcement.this, UHTReadTag.class);
                startActivity(intent);
                finish();
            }
        });

        //调色背景
//        WindowManager.LayoutParams lp=getWindow().getAttributes();
//        lp.alpha=0.3f;
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        //弹窗
//          View view=View.inflate(getApplicationContext(),R.layout.dialog_text,null);
//          alertDialog=new AlertDialog.Builder(this).setView(view).create();
//          alertDialog.show();
//          alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//              @Override
//              public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                  if(keyCode == 280){
//                      if(!alertDialog.isShowing()) {
//                         alertDialog.cancel();
//                      }
//                  }
//                  return false;
//              }
//          });


        Intent intent = getIntent();
//        String result = intent.getStringExtra("result");//获取犬只信息
        dogDetailsInfo= DataHolder.getInstance().getDogDetailsInfo();
        String result=dogDetailsInfo.getResult();
        List<String> list = (List<String>) intent.getSerializableExtra("list");
        initDogType(result, list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        EnforcementAdatper adatper = new EnforcementAdatper(dogTypeList, this, map);
        recyclerView.setAdapter(adatper);
    }

    //初始化Recycleview数据
    private void initDogType(String result, List<String> list) {
        try {
            JSONArray json = new JSONArray(result);
            Log.i(TAG, "测试数量" + json.length());

            for (int i = 0; i < json.length(); i++) {
                JSONObject dogInfo = json.getJSONObject(i).getJSONObject("dogInfo");
                JSONArray devices = json.getJSONObject(i).getJSONArray("devices");
                JSONObject devices_json = new JSONObject(devices.getString(0));
                JSONArray urlLsit = json.getJSONObject(i).getJSONArray("urlLsit");
                String urlLsit_json;
                if (urlLsit.length() > 0) {
                    urlLsit_json = new JSONObject(urlLsit.getString(0)).getString("imageUrl");
                } else {
                    urlLsit_json = "";
                }
                dog_list.add(devices_json.getString("deviceNumber"));
                map.put(dogInfo.getString("dogId"), json.getString(i).toString());
                dogTypeList.add(new DogType(dogInfo.getString("dogName"), dogInfo.getString("dogGender"), dogInfo.getString("breed"),
                        dogInfo.getString("dogAge"), dogInfo.getString("dogId"), urlLsit_json));
            }
            for (String dog_id : list) {
                if (!dog_list.contains(dog_id)) {
                    dogTypeList.add(new DogType("", "", "", "", dog_id, ""));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 280) {
            Intent intent = new Intent(LawEnforcement.this, UHTReadTag.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
