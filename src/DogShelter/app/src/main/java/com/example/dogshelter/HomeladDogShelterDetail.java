package com.example.dogshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loadPhoto.GridViewAdapter;
import com.example.loadPhoto.MainConstant;
import com.example.loadPhoto.PlusImageActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeladDogShelterDetail extends AppCompatActivity {
    private ImageView turnback;//返回
    private GridView gridView;//图片
    private GridViewAdapter mGridViewAddImgAdapter; //展示图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private TextView dogCode;//犬只编号
    private TextView dogName;//犬名
    private TextView dogBreed;//品种
    private TextView dogGender;//性别
    private TextView dogAge;//犬龄
    private TextView dogColor;//毛色
    private TextView weight;//体重
    private TextView healthStatus;//健康状态
    private TextView doghouseNum;//犬舍编号
    private TextView collDogReason;//收容犬类别
    private TextView dogCharacter;//特点
    private TextView address;//位置
    private TextView collectionTime;//收容时间
    private TextView remarks;//备注
    private String dogInfo;//流浪犬详情

    private static final String TAG = "HomeladDogShelterDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelad_dog_shelter_detail);
        turnback=(ImageView)findViewById(R.id.turnback);//返回
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        dogInfo = intent.getStringExtra("dogInfo");
        gridView = (GridView) findViewById(R.id.gridView);
        initGridView();
        dogCode=(TextView)findViewById(R.id.dogCode);
        dogName=(TextView)findViewById(R.id.dogName);
        dogBreed=(TextView)findViewById(R.id.dogBreed);
        dogGender=(TextView)findViewById(R.id.dogGender);
        dogAge=(TextView)findViewById(R.id.dogAge);
        dogColor=(TextView)findViewById(R.id.dogColor);
        weight=(TextView)findViewById(R.id.weight);
        healthStatus=(TextView)findViewById(R.id.healthStatus);
        doghouseNum=(TextView)findViewById(R.id.doghouseNum);
        collDogReason=(TextView)findViewById(R.id.collDogReason);
        dogCharacter=(TextView)findViewById(R.id.dogCharacter);
        address=(TextView)findViewById(R.id.address);
        collectionTime=(TextView)findViewById(R.id.collectionTime);
        remarks=(TextView)findViewById(R.id.remarks);

        try {
            JSONObject json = new JSONObject(dogInfo);
            dogCode.setText(json.getString("dogCode"));
            dogName.setText(json.getString("dogName"));
            dogBreed.setText(json.getString("dogBreed"));
            dogGender.setText(json.getString("dogGender"));
            dogAge.setText(json.getString("dogAge"));
            dogColor.setText(json.getString("dogColor"));
            weight.setText(json.getString("weight"));
            healthStatus.setText(json.getString("healthStatus"));
            doghouseNum.setText(json.getString("doghouseNum"));
            collDogReason.setText(json.getString("collDogReason"));
            dogCharacter.setText(json.getString("dogCharacter"));
            address.setText(json.getString("address"));
            collectionTime.setText(json.getString("collectionTime"));
            remarks.setText(json.getString("remarks"));
            if(json.getString("zUrl")!="null") {
                mPicList.add(json.getString("zUrl"));
            }
            if(json.getString("cUrl")!="null") {
                mPicList.add(json.getString("cUrl"));
            }
            if(json.getString("wUrl")!="null") {
                mPicList.add(json.getString("wUrl"));
            }
            if(json.getString("wUrltwo")!="null") {
                mPicList.add(json.getString("wUrltwo"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                viewPluImg(position);
            }
        });
    }


    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }


    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

}
