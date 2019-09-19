package com.example.dogshelter;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.model.DogType;
import com.example.selfadapter.DogInquiryAdatper;
import com.example.tooltype.EndlessRecyclerOnScrollListener;
import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;
import com.example.tooltype.ProperTies;
import com.rscja.deviceapi.Barcode2D;
import com.zebra.adc.decoder.Barcode2DWithSoft;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class DogInquiry extends AppCompatActivity {
    private ImageView turnback;//返回
    //    private SearchView searchView;//搜索控件
    private ImageView glass;//搜索
    private EditText glass_line;//搜索text
    private InputMethodManager methodManager;//输入法管理器
    private ImageView scan;//扫描二维码
    private SwipeRefreshLayout swipRefreshLayout;//下拉刷新
    private String pushWord = "";//输入查询字
    private RecyclerView recyclerView;//循环列表
    private LinearLayoutManager layoutManager;
    private DogInquiryAdatper adatper;
    private List<DogType> dogTypeList = new ArrayList<>();
    public List<String> dog_list = new ArrayList<String>();
    public Map<String, String> map = new HashMap<String, String>();
    private static final String TAG = "DogInquiry";
    private Properties proper;
    private final String address = "http://192.168.0.69:80/biz/dogAndOwner/selectDogListe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_inquiry);
        proper = ProperTies.getProperties(getApplication());
//        final String address = proper.getProperty("DogSearch");
        scan = (ImageView) findViewById(R.id.scan);//扫描
        //region 搜索控件
//        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.searchView);
//        final EditText editText = (EditText) searchView.findViewById(R.id.search_src_text);
//        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setQueryHint("犬主身份证号/手机号/犬证号/犬只标示牌号");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                Log.i(TAG, "测试犬只信息得到字符串: " + s);
//                pushWord = s;
//                size = 6;//初始化
//                String data = getData(s);
//                OKHttp(address, data);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                Log.i(TAG, "测试犬只信息得到字符串2: " + s);
//                if (TextUtils.isEmpty(s)) {
//                    pushWord = "";
//                    size = 6;//初始化
//                    String data = getData("");
//                    OKHttp(address, data);
//                }
//                return true;
//            }
//        });
        //endregion
        glass = (ImageView) findViewById(R.id.glass);
        glass_line = (EditText) findViewById(R.id.search_line);

        methodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glass_line.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
//                if (methodManager.isActive()) {
                methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);// 隐藏输入法
//                }
                if (TextUtils.isEmpty(glass_line.getText().toString())) {
                    Toast.makeText(DogInquiry.this, "请输入需查询的内容", Toast.LENGTH_SHORT).show();
                } else {
                    pushWord = glass_line.getText().toString();
                    size = 6;//初始化
                    String data = getData(pushWord);
                    OKHttp(address, data);
                }
            }
        });

        glass_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glass_line.setFocusable(true);
                glass_line.setFocusableInTouchMode(true);//设置触摸聚焦
                glass_line.requestFocus();//请求焦点
                glass_line.findFocus();//获取焦点
                methodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);// 显示输入法
//                methodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


        glass_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.i(TAG, "测试数据改变1: " + s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "测试数据改变2: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    pushWord = "";
                    size = 6;//初始化
                    String data = getData(pushWord);
                    OKHttp(address, data);
                }
                Log.i(TAG, "测试数据改变3: " + s.toString());
            }
        });


        //初始化扫描
        initBarcode2D();
        //初始化声音
        initSound();
        String data = getData("");
        OKHttp(address, data);

        turnback = (ImageView) findViewById(R.id.turnback);//返回
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 6;
                finish();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readBroad();
            }
        });
        swipRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                size = 6;
                String data = getData("");
                OKHttp(address, data);

                // 延时1s关闭下拉刷新
                swipRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipRefreshLayout != null && swipRefreshLayout.isRefreshing()) {
                            swipRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(DogInquiry.this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });


        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adatper.setLoadState(adatper.LOADING);


                Log.i(TAG, "测试数量：" + dogTypeList.size() + "----" + size);

                if (dogTypeList.size() + 6 < size) {
                    adatper.setLoadState(adatper.LOADING_END);
                } else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String data = getData(pushWord);
                                    OKHttp(address, data);
                                    adatper.setLoadState(adatper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 2000);

                }
            }
        });


    }

    private int lastOffset;//偏移量
    private int lastPosition;//位置

//    记录RecycleView当前位置
    private void getPositionAndOffset() {
        layoutManager=(LinearLayoutManager)recyclerView .getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(recyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 280) {
            readBroad();
        }
        return super.onKeyDown(keyCode, event);
    }


    //region 初始化声音和扫描
    public Barcode2DWithSoft mBarcode;

    public void initBarcode2D() {
        try {
            mBarcode = Barcode2DWithSoft.getInstance();
        } catch (Exception ex) {
            toastMessage(ex.getMessage());
            return;
        }

        if (mBarcode != null) {
            new InitTask().execute();
        }
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mBarcode.open(DogInquiry.this);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                Toast.makeText(DogInquiry.this, "初始化失败请重新激活",
                        Toast.LENGTH_SHORT).show();
            } else {
//                readBroad();//开始
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(DogInquiry.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("正在初始化设备，请稍等...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }

    @Override
    protected void onDestroy() {

        if (mBarcode != null) {
            mBarcode.close();
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

    private Boolean Flag = true;

    private void readBroad() {
        if (Flag) {
            playSound(1);
            mBarcode.scan();
            mBarcode.setScanCallback(new Barcode2DWithSoft.ScanCallback() {
                @Override
                public void onScanComplete(int i, int i1, byte[] bytes) {
                    if (bytes == null) {
                        Toast.makeText(DogInquiry.this, "识别失败，请重新尝试",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        glass_line.setFocusable(false);
                        size = 6;
                        glass_line.setText(new String(bytes).substring(0, i1));
                        String data = getData(new String(bytes).substring(0, i1));
                        OKHttp(address, data);
                        Log.i(TAG, "测试扫描3: " + new String(bytes).substring(0, i1));
                    }
                }
            });
        } else {
            mBarcode.stopScan();
        }
    }


    //获取犬只列表
    private static int size = 6;

    public static String getData(String value) {
        Log.i(TAG, "测试犬只信息数字" + String.valueOf(size));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("input", value);
            jsonObject.put("num", 1);
            jsonObject.put("start", 1);
            jsonObject.put("size", size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        size += 6;
        return jsonObject.toString();
    }


    public void initDogList(final String response, final String address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray dogInfos = result.getJSONArray("dogInfos");
                    dogTypeList.clear();
                    if (dogInfos.length() == 0) {
                        Toast.makeText(DogInquiry.this, "查询无数据", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < dogInfos.length(); i++) {
                            JSONObject newJson = dogInfos.getJSONObject(i);
//                        map.put(newJson.getString("dogId"),newJson.toString());//添加犬只信息
                            dogTypeList.add(new DogType(newJson.getString("dogName"), newJson.getString("dogGender"),
                                    newJson.getString("breed"), newJson.getString("dogAge"), newJson.getString("dogId"),
                                    newJson.getString("url")));
                        }
                        adatper = new DogInquiryAdatper(dogTypeList, DogInquiry.this, address);
                        recyclerView.setAdapter(adatper);
                        if(dogTypeList.size()>6){
                            scrollToPosition();
                        }
                    }
                    adatper.notifyDataSetChanged();
                    swipRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void OKHttp(final String address, String data) {
        HttpUtil.sendRequestOKHttpData(address, data, new HttpCallbackListener() {
            @Override
            public void onFinish(String response, String token) {
            }

            @Override
            public void onFinish(String response) {
                String address = proper.getProperty("DogInfo");
                Log.i(TAG, "测试犬只信息aaaaaa" + response);
                initDogList(response, address);
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(DogInquiry.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

    }


}
