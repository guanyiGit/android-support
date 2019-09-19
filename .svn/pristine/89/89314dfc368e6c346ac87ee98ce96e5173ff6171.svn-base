package com.example.dogshelter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;
import com.example.tooltype.MD5Util;
import com.example.tooltype.ProperTies;
import com.example.tooltype.UpdateManager;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private static final String TAG = "MainActivity";
    private String account_text;//账号
    private String password_text;//密码
    private Properties proper;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);//去掉标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //去掉导航栏
        setContentView(R.layout.activity_main);

//        判断网络是否异常
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

//        严苛模式（StrictMode）避免主线程被阻塞
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());


        proper = ProperTies.getProperties(getApplication());
        String address = proper.getProperty("Update");

        UpdateManager manager = new UpdateManager(MainActivity.this);
        // 检查软件更新
        manager.checkUpdate(address);

        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        Drawable drawable = getResources().getDrawable(R.mipmap.account);
        drawable.setBounds(0, 0, 40, 40);
        account.setCompoundDrawables(drawable, null, null, null);
        Drawable drawable2 = getResources().getDrawable(R.mipmap.password);
        drawable2.setBounds(0, 0, 40, 40);
        password.setCompoundDrawables(drawable2, null, null, null);


        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_text = account.getText().toString().trim();//账号去除首尾空格
                password_text = password.getText().toString().trim();//密码
//                if (true) {//临时修改
                if (isConnectInternet()) {
                    if (TextUtils.isEmpty(account_text) || TextUtils.isEmpty(password_text)) {
                        Toast.makeText(MainActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        password_text = new MD5Util().md5Password(password_text);
                        Log.i(TAG, "测试更新回应: " + password_text);
                        String address2 = proper.getProperty("Login");
                        String data = "username=" + account_text + "&password=" + password_text;
                        HttpUtil.sendHttpRequest(address2, data, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response, String token) {
                                Log.i(TAG, "测试更新回应: " + response);
                                pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                editor = pref.edit();
                                if (!TextUtils.isEmpty(token)) {
                                    editor.putString("token", token.substring(token.indexOf('[') + 1, token.indexOf(']')));
                                } else {
                                    editor.clear();
                                }
                                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFinish(String response) {
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.i(TAG, "测试更新onError: " + e.toString());
                                Looper.prepare();
                                if(e.toString().contains("java.lang.NullPointerException")){
                                    Toast.makeText(MainActivity.this, "账号或密码错误请重试", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "服务器异常请稍后重试", Toast.LENGTH_SHORT).show();
                                }
                                //java.lang.NullPointerException 空指针异常，服务器返回结果为空
                                //java.io.FileNotFoundException,访问服务器异常
                                Looper.loop();
                            }
                        });

                    }
                } else {
                    Toast.makeText(MainActivity.this, "当前网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
//                Toast.makeText(context, "网络正常!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "当前网络不可用!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //判断网络是否正常连接
    private boolean isConnectInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return networkInfo.isAvailable();
        }
        return false;
    }


}
