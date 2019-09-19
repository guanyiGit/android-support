package com.example.tooltype;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dogshelter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UpdateManager {
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 保存解析的XML信息 */
    HashMap<String, String> mHashMap;
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    Map<String, String> map;

    private Context mContext;
    /* 更新进度条 */
    private ProgressBar mProgress;

    private Dialog mDownloadDialog;

    private static String TAG = "UpdateManager";

    static HashMap<String, String> hashMap = new HashMap<String, String>();


    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);

                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();

                    break;

                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context) {

        this.mContext = context;

    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(String address) {

        if (isUpdate(address)) {
            // 显示提示对话框
            showNoticeDialog();

        } else {

            Toast.makeText(mContext, "当前已是最新版本", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 检查软件是否有更新版本
     *
     * @return
     */
    private boolean isUpdate(final String address) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        HttpURLConnection urlConnection = null;
        try {
            Log.e(TAG, "开始请求网络数据...");
            URL url = new URL(address);
            // 利用HttpURLConnection对象从网络中请求网络数据
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET"); // 设置请求方式GET，POST
            // 设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(6000); //设置读取超时
            if (urlConnection.getResponseCode() != 200) {
                // 对响应码进行判断,200为成功
                throw new RuntimeException("请求url失败");
            }
            // 从Internet获取网页,发送请求,将网页以流的形式读回来
            InputStream inputStream = urlConnection.getInputStream();
            // //对输入流进行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            // 解析xml
//            hashMap = parseXMLWithPull(stringBuilder.toString());
            Log.i(TAG, "测试啊啊啊"+stringBuilder.toString());
            hashMap = parseJsonObject(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            Log.e(TAG, "关闭请求连接");
            if (urlConnection != null)
                urlConnection.disconnect();
        }
//            }
//        }).start();



        if (null != hashMap) {
            // 获取当前软件版本
            int versionCode = getVersionCode(mContext);
            int serviceCode = Integer.valueOf(hashMap.get("version"));
            // 版本判断
            if (serviceCode >= versionCode) {
                return true;
            }
        }


        return false;

    }

    public HashMap<String, String> parseXMLWithPull(String xmlString) {
        try {
            Log.d(TAG, ">>> 开始解析XML...");
            // 获取XmlPullParser对象
            // 第一种获取XmlPullParser对象的方式
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();
            // 第二种获取XmlPullParser对象的方式
            // XmlPullParser parser=Xml.newPullParser();
            parser.setInput(new StringReader(xmlString));
            int eventType = parser.getEventType();
            String version = "";
            String name = "";
            String url = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();

                switch (eventType) {
                    // 开始读取XML文档 ，对应people节点
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 开始解析某个结点，对应name ，age，sex标签
                    case XmlPullParser.START_TAG:

                        if ("version".equals(nodeName))
                            version = parser.nextText();

                        if ("name".equals(nodeName))
                            name = parser.nextText();

                        if ("url".equals(nodeName))
                            url = parser.nextText();

                        break;
                    // 完成解析某个结点，对应person节点
                    case XmlPullParser.END_TAG:
                        if ("update".equals(nodeName)) {
//                            hashMap.put("version", version);
//                            hashMap.put("name", name);
//                            hashMap.put("url", url);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }


    private HashMap<String, String> parseJsonObject(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONObject result = json.getJSONObject("obj");
            String version = result.getString("versionInfo");
            String name = result.getString("plName");
            String url = result.getString("pkUrl");
            hashMap.put("version", version);
            hashMap.put("name", "app-release.apk");
            hashMap.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;
    }


    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle("软件更新");

        builder.setMessage("检测到新版本，立即更新吗");
        // 更新
        builder.setPositiveButton("更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 显示下载对话框
                        showDownloadDialog();
                    }
                });
        // 稍后更新
        builder.setNegativeButton("稍后更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("正在更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);

        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 设置取消状态
                        cancelUpdate = true;
                    }
                });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author luliuying
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory()
                            + "/";
                    mSavePath = sdpath + "download";
                    // System.out.println("mSavePath:" + mSavePath);
                    URL url = new URL(hashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();

                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // System.out.println("大小" + length);
                    // 创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, hashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.

                    fos.close();
                    is.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();

        }

    }

    ;


    /**
     * 安装APK文件
     */
    private void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkfile = new File(mSavePath, hashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }
        // Android 8.0安装调用
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
//            intent.setDataAndType(Uri.fromFile(apkfile),
//                    "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        } else {
        // 通过Intent安装APK文件
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
//        }

    }


}




