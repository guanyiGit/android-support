package com.example.dogshelter;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.model.DogType;
import com.example.model.HomeShelterDog;
import com.example.selfadapter.DogInquiryAdatper;
import com.example.selfadapter.HomelandDogShelterAdatper;
import com.example.tooltype.EndlessRecyclerOnScrollListener;
import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;
import com.example.tooltype.ProperTies;

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

public class HomeladDogShelter extends AppCompatActivity {
    private ImageView turnback;//返回
    //    private SearchView searchView;//搜索控件
    private ImageView glass;//搜索
    private EditText glass_line;//搜索text
    private InputMethodManager methodManager;//输入法管理器
    private SwipeRefreshLayout swipRefreshLayout;//下拉刷新
    private String pushWord = "";//输入查询字
    private RecyclerView recyclerView;//循环列表
    private LinearLayoutManager layoutManager;
    private HomelandDogShelterAdatper adatper;
    private List<HomeShelterDog> dogTypeList = new ArrayList<>();
    public List<String> dog_list = new ArrayList<String>();
    public int pn=1;//初始化页数
    public Boolean Reflash_Decide=false;//判断是否刷新
    public Boolean Bottom_End=false;//判断是否到底了
    public Map<String, String> map = new HashMap<String, String>();
    private Properties proper;

    private static final String TAG = "HomeladDogShelter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelad_dog_shelter);
        proper = ProperTies.getProperties(getApplication());
        glass = (ImageView) findViewById(R.id.glass);
        glass_line = (EditText) findViewById(R.id.search_line);
        final String address = proper.getProperty("DogHome");
        methodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glass_line.setFocusable(false);//设置输入框不可聚焦，即失去焦点和光标
                methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);// 隐藏输入法
                if (TextUtils.isEmpty(glass_line.getText().toString())) {
                    Toast.makeText(HomeladDogShelter.this, "请输入需查询的内容", Toast.LENGTH_SHORT).show();
                } else {
                    pushWord = glass_line.getText().toString();
                    String data = getData(address,pushWord);
                    OKHttp(data);
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
            }
        });

        glass_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    pn=1;
                    Reflash_Decide=false;
                    Bottom_End=false;
                    pushWord="";
                    String data = getData(address,pushWord);
                    OKHttp(data);
                }
            }
        });

        String data = getData(address, pushWord);
        Log.i(TAG, "测试犬只信息哈" + address);
        OKHttp(data);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
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
                Log.i(TAG, "测试数量：");
                if (Bottom_End) {
                    adatper.setLoadState(adatper.LOADING_END);
                } else {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Reflash_Decide=true;
                                    String data = getData(address,pushWord);
                                    OKHttp(data);
                                    adatper.setLoadState(adatper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 2000);
                }
            }
        });


        swipRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pn=1;
                Reflash_Decide=false;
                Bottom_End=false;
                String data = getData(address,pushWord);
                OKHttp(data);
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



        turnback = (ImageView) findViewById(R.id.turnback);//返回
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public  String getData(String address, String value) {
        if(Reflash_Decide==true) {
           pn++;
        }
            String data = address + "?pn="+pn+"&pageSize=6&status=0&string=" + value;
        return data;
    }


    public void OKHttp(final String address) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response, String token) {
            }

            @Override
            public void onFinish(String response) {
                String address = proper.getProperty("DogHomeDetail");
                Log.i(TAG, "测试犬只信息哈" + response);
                initDogList(response, address);
            }

            @Override
            public void onError(Exception e) {
                Looper.prepare();
                Toast.makeText(HomeladDogShelter.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

    }


    public void initDogList(final String response, final String address) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray dogInfos = data.getJSONArray("lists");
                    if(!Reflash_Decide) {
                        dogTypeList.clear();
                    }
                    if (dogInfos.length() == 0) {
                        Toast.makeText(HomeladDogShelter.this, "查询无数据", Toast.LENGTH_SHORT).show();
                    } else {
                        if(dogInfos.length()<6){
                            Bottom_End=true;
                        }
                        for (int i = 0; i < dogInfos.length(); i++) {
                            JSONObject newJson = dogInfos.getJSONObject(i);
//                        map.put(newJson.getString("dogId"),newJson.toString());//添加犬只信息
                            dogTypeList.add(new HomeShelterDog(newJson.getString("dogBreed"), newJson.getString("dogGender"),
                                    newJson.getString("dogColor"), newJson.getString("dogAge"), newJson.getString("collectionId"),
                                    newJson.getString("thumbnailUrl"), newJson.getString("collectionTime")));
                        }
                        adatper = new HomelandDogShelterAdatper(dogTypeList, HomeladDogShelter.this, address);
                        recyclerView.setAdapter(adatper);
                        if (dogTypeList.size() > 6) {
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

}
