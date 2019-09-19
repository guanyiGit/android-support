package com.example.dogshelter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.DataHolder;
import com.example.model.DogDetailsInfo;
import com.example.selfadapter.SimpleListviewAdatper;
import com.example.tooltype.ActivityCollerctor;
import com.example.tooltype.BaseActivity;
import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;
import com.example.tooltype.ProperTies;
import com.example.tooltype.StringUtils;
import com.rscja.deviceapi.RFIDWithUHF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class UHTReadTag extends BaseActivity {
    private ListView LvTags;
    private ImageView turnback;//返回
    public List<String> list = new ArrayList<String>();// 存储扫描的list
    private SimpleListviewAdatper adapter;
    private TextView scan_count;//已识别数量
    private Properties proper;
    private static final String TAG = "UHTReadTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uhtread_tag);
        turnback = (ImageView) findViewById(R.id.turnback);
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollerctor.finishAll();
//                finish();
            }
        });


        adapter = new SimpleListviewAdatper(
                UHTReadTag.this, R.layout.activity_listview, list);
        LvTags = (ListView) findViewById(R.id.LvTags);
        scan_count = (TextView) findViewById(R.id.scan_count);

        //初始化声音
        initSound();
        //连接设备
        initUHF();

        tagList = new ArrayList<HashMap<String, String>>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.obj + "";
                String[] strs = result.split("@");
                addEPCToList(strs[0], strs[1]);
                playSound(1);
            }
        };


        //region 提交按钮逻辑
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                proper= ProperTies.getProperties(getApplication());
//                String address = proper.getProperty("DogMessage");
//                ArrayList<String> data=new ArrayList<String>();
//                data.add("362531821656226");
//
//                //region OKHttp访问
//                                HttpUtil.sendRequestWithOKHttp(address, data, new HttpCallbackListener() {
//                    @Override
//                    public void onFinish(String response,String token){
//
//                    }
//                    @Override
//                    public void onFinish(String response) {
//                        Log.i(TAG, "测试犬只信息"+response);
//                        String dogInfo=parseJsonObject(response);//犬只信息
//                        Intent intent=new Intent(UHTReadTag.this,LawEnforcement.class);
//                        intent.putExtra("dogInfo",dogInfo);
//                        startActivity(intent);
//                        Log.i(TAG, "测试犬只信息ha:"+dogInfo);
//                    }
//                    @Override
//                    public void onError(Exception e) {
//                        Log.i(TAG, "测试犬只信息"+e.toString());
//                        Looper.prepare();
//                        Toast.makeText(UHTReadTag.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//                //endregion
//
//                //region http访问
////                HttpUtil.sendHttpRequest(address, data.toString(), new HttpCallbackListener() {
////                    @Override
////                    public void onFinish(String response,String token){
////                        Log.i(TAG, "测试犬只信息"+response);
////                    }
////                    @Override
////                    public void onFinish(String response) {
////                        Log.i(TAG, "测试犬只信息"+response);
////                    }
////                    @Override
////                    public void onError(Exception e) {
////                        Log.i(TAG, "测试犬只信息"+e);
////                        Looper.prepare();
////                        Toast.makeText(UHTReadTag.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
////                        Looper.loop();
////                    }
////                });
//                //endregion
//
////                HttpUtil.sendRequestOKHttp(address,data);
//            }
//        });
        //endregion
    }


    private String parseJsonObject(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONArray result = json.getJSONArray("result");
//            JSONObject  doginfo=result.getJSONObject(0);
            return result.toString();
        } catch (JSONException e) {
            Toast.makeText(UHTReadTag.this, "服务器异常请重新尝试", Toast.LENGTH_SHORT).show();
            finish();
            return e.toString();
        }
    }


    //region 初始化设备和声音
    public RFIDWithUHF mReader;
    public static Boolean NewFlag=false;
    public void initUHF() {
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {

            toastMessage(ex.getMessage());

            return;
        }

//        if (mReader != null&&mReader.init()) {
//            readTag();
//        }
        if(mReader != null&&NewFlag==true){
           readTag();
        }else if(mReader != null){
            new InitTask().execute();
        }
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toastMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }


    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                ActivityCollerctor.finishAll();
                Toast.makeText(UHTReadTag.this, "初始化失败请重新激活",
                        Toast.LENGTH_SHORT).show();
            } else {
                readTag();//开始超高频读取
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mypDialog = new ProgressDialog(UHTReadTag.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在初始化设备，请稍等...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }

    @Override
    protected void onDestroy() {

        if (mReader != null) {
            mReader.free();
            NewFlag=false;
        }
        super.onDestroy();
    }


    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;

    private void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(this, R.raw.serror, 1));
        am = (AudioManager) this.getSystemService(AUDIO_SERVICE);// 实例化AudioManager对象
    }

    public void playSound(int id) {
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //endregion

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 280) {
            Flag = false;
            readTag();

            proper = ProperTies.getProperties(getApplication());
            String address = proper.getProperty("DogMessage");
            list.add("861964040628880");
            list.add("4545");
            list.add("4577");
            list.add("4555");
            list.add("4456");
            Log.i(TAG, "测试犬只信息啊: " + address);
            //region OKHttp访问
            HttpUtil.sendRequestWithOKHttp(address, list, new HttpCallbackListener() {
                @Override
                public void onFinish(String response, String token) {

                }

                @Override
                public void onFinish(String response) {
                    Log.i(TAG, "测试犬只信息" + response);
                    if (response.contains("503 Service Unavailable")) {
                        Looper.prepare();
                        Toast.makeText(UHTReadTag.this, "服务器异常", Toast.LENGTH_SHORT).show();
                        finish();
                        Looper.loop();
                    } else if(response.contains("HTTP Status 400 – Bad Request")){
                        Looper.prepare();
                        Toast.makeText(UHTReadTag.this, "服务器异常,请稍后重试！", Toast.LENGTH_SHORT).show();
                        finish();
                        Looper.loop();
                    }else {
                        NewFlag=true;
                        String result = parseJsonObject(response);//犬只信息
                        DogDetailsInfo dogDetailsInfo=new DogDetailsInfo();
                        dogDetailsInfo.setResult(result);
                        Log.i(TAG, "测试犬只信息aaaa: " + result);
                        DataHolder.getInstance().setmDogDetailsInfo(dogDetailsInfo);
                        Intent intent = new Intent(UHTReadTag.this, LawEnforcement.class);
//                        intent.putExtra("result", result);
                        intent.putExtra("list", (Serializable) list);
                        startActivity(intent);
//                      finish();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "测试犬只信息" + e.toString());
                    Looper.prepare();
                    Toast.makeText(UHTReadTag.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private HashMap<String, String> map;
    private ArrayList<HashMap<String, String>> tagList;

    private void addEPCToList(String epc, String rssi) {
        if (!TextUtils.isEmpty(epc)) {
            int index = checkIsExist(epc);
            map = new HashMap<String, String>();
            map.put("tagUii", epc);
//            map.put("tagCount", String.valueOf(1));
//            map.put("tagRssi", rssi);
            if (index == -1) {
                tagList.add(map);
                for (HashMap<String, String> m : tagList) {
                    for (String k : m.keySet()) {
                        if (list.contains(m.get(k).substring(4).replaceAll("^(0+)", ""))) {
                            continue;
                        } else {
                            list.add(m.get(k).substring(4).replaceAll("^(0+)", ""));
                            //不能保证犬牌id全为数字
//                      list.add(String.valueOf(Integer.parseInt(m.get(k).substring(4))));
                        }
                    }
                }
//                Log.i(TAG, "测试数据"+list.toString());
                LvTags.setAdapter(adapter);
                scan_count.setText("" + adapter.getCount());
            } else {
//                int tagcount = Integer.parseInt(
//                        tagList.get(index).get("tagCount"), 10) + 1;
//                map.put("tagCount", String.valueOf(tagcount));
                tagList.set(index, map);
            }
            adapter.notifyDataSetChanged();
        }
    }


    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (StringUtils.isEmpty(strEPC)) {
            return existFlag;
        }
        String tempStr = "";
        for (int i = 0; i < tagList.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp = tagList.get(i);
            tempStr = temp.get("tagUii");
            if (strEPC.equals(tempStr)) {
                existFlag = i;
                break;
            }
        }
        return existFlag;
    }


    private Boolean Flag = true;

    private void readTag() {
        if (Flag) {
            if (UHTReadTag.this.mReader.startInventoryTag(0, 0)) {
                new TagThread().start();
            } else {
                UHTReadTag.this.mReader.stopInventory();
                Toast.makeText(UHTReadTag.this, "Open Failure", Toast.LENGTH_SHORT).show();
            }
        } else {// 停止识别
            stopInventory();
        }
    }

    private void stopInventory() {
        if (!Flag) {
            if (UHTReadTag.this.mReader.stopInventory()) {

            } else {
                Toast.makeText(UHTReadTag.this, "Stop Failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Handler handler;

    class TagThread extends Thread {
        public void run() {
            String strTid;
            String strResult;
            String[] res = null;
            while (Flag) {
                res = UHTReadTag.this.mReader.readTagFromBuffer();
                if (res != null) {
                    strTid = res[0];
                    if (strTid.length() != 0 && !strTid.equals("0000000" +
                            "000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }
//                    Log.i("测试", "EPC:"+res[1]+"|"+strResult);
                    Message msg = handler.obtainMessage();
                    msg.obj = strResult + "EPC:" + UHTReadTag.this.mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                    handler.sendMessage(msg);
                }
            }
        }
    }


}
