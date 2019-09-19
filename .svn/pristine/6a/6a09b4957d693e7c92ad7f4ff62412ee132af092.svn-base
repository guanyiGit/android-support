package com.example.tooltype;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static void sendHttpRequest(final String address, final String date, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
//                    connection.setRequestProperty("Charset", "utf-8");//设置编码
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
//                    connection.setRequestProperty("Content-Type","application/json");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    connection.setRequestProperty("Content-Length",String.valueOf(date.getBytes().length));
//                    connection.setRequestProperty("deviceIds","[362531821656226]");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                   out.writeBytes(date);
//                    out.writeUTF("utf-8");
                    out.write(date.getBytes("utf-8")); //写入文本数据
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }


                    //获取消息头
                    Map<String, List<String>> map = connection.getHeaderFields();
                    String token = map.get("token").toString();
//                    for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//                        Log.i(TAG, " 哈哈"+ entry.getKey() +
//                                " ,Value : " + entry.getValue());
//                    }


                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response.toString(), token);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
//                    connection.setRequestProperty("Charset", "utf-8");//设置编码
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(6000);
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    public static void sendRequestWithOKHttp(final String address, final List<String> data, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONArray jsonArray=new JSONArray(data);
//                    String newData=jsonArray.toString();
                    String newData=data.toString();
                    Log.i(TAG, "测试犬只信息"+newData);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), newData);
                    Request request = new Request.Builder()
                            .url(address).post(body).build();
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("deviceIds", data.toString()).build();
                    Response response = client.newCall(request).execute();
                    listener.onFinish(response.body().string());
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }



    public static void sendRequestOKHttpData(final String address, final String data, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
                    Request request = new Request.Builder()
                            .url(address).post(body).build();
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("deviceIds", data.toString()).build();
                    Response response = client.newCall(request).execute();
                    listener.onFinish(response.body().string());
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }



    public static void sendRequestOKHttpAddress(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), data);
                    Request request = new Request.Builder()
                            .url(address).build();
//                    RequestBody requestBody = new FormBody.Builder()
//                            .add("deviceIds", data.toString()).build();
                    Response response = client.newCall(request).execute();
                    listener.onFinish(response.body().string());
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }






    public static void sendRequestOKHttp(final String address,final ArrayList<String> data){
//        try {
            // 创建json对象
//            JSONObject jsonObject = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(data);
//            jsonObject.put("deviceIds", jsonArray);
//            String newData=jsonObject.toString();

            JSONArray jsonArray=new JSONArray(data);
            String newData=jsonArray.toString();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), newData);
            Request request = new Request.Builder()
                    .url(address)
                    .post(body)
                    .build();
// 向服务器异步请求数据
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Log.i(TAG, "测试犬只信息失败");
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    ResponseBody body = response.body();
                    Log.i(TAG, "测试犬只信息成功"+body.string());


                }
            });

//        } catch (JSONException e) {
//            e.printStackTrace();
//    }
    }






}
