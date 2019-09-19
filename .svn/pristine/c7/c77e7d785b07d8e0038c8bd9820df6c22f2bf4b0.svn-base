package com.example.dogshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DogInquiryDetail extends AppCompatActivity {
    private ImageView turnback;//返回
    private GridView gridView;//图片
    private GridViewAdapter mGridViewAddImgAdapter; //展示图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private String dogInfo;//犬只信息
    private TextView dogOwnerName;//犬主姓名
    private TextView sex;//性别
    private TextView cardType;//证件类型
    private TextView cardNum;//证件号码
    private TextView dogOwnerPhone;//联系电话
    private TextView districtName;//所属区域
    private TextView blackId;//所属街道
    private TextView address;//详细地址
    private TextView godName;//犬名
    private TextView breed;//品种
    private TextView dogGender;//犬性别
    private TextView dogAge;//犬龄
    private TextView color;//毛色
    private TextView birthTime;//出生日期
    private TextView fatherId;//父本
    private TextView motherId;//母本
    private TextView immuneCardNum;//免疫证号
    private TextView orgName;//办理机构
    private TextView lssueTime;//办理时间
    private TextView deviceNumber;//设备编号
    private TextView orgName2;//签发机构
    private TextView lssueTime2;//签发日期
    private TextView endTime;//有效期限
    private TextView injectionTime;//免疫时间
    private TextView vaccineName;//免疫项目
    private TextView vaccineProducer;//免疫厂家及批号
    private TextView name;//接种兽医
    private TextView orgName3;//接种单位
    private TextView violationDate;//违法时间
    private TextView violationPlace ;//违法地点
    private TextView violationType ;//违法行为
    private TextView fine;//本次扣分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_inquiry_detail);
        Intent intent=getIntent();
        dogInfo=intent.getStringExtra("dogInfo");
        turnback=(ImageView)findViewById(R.id.turnback);//返回
        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridView = (GridView) findViewById(R.id.gridView);
        initGridView();
        dogOwnerName=(TextView)findViewById(R.id.dogOwnerName);
        sex=(TextView)findViewById(R.id.sex);
        cardType=(TextView)findViewById(R.id.cardType);
        cardNum=(TextView)findViewById(R.id.cardNum);
        dogOwnerPhone=(TextView)findViewById(R.id.dogOwnerPhone);
        districtName=(TextView)findViewById(R.id.districtName);
        blackId=(TextView)findViewById(R.id.blackId);
        address=(TextView)findViewById(R.id.address);
        godName=(TextView)findViewById(R.id.godName);
        breed=(TextView)findViewById(R.id.breed);
        dogGender=(TextView)findViewById(R.id.dogGender);
        dogAge=(TextView)findViewById(R.id.dogAge);
        birthTime=(TextView)findViewById(R.id.birthTime);
        color=(TextView)findViewById(R.id.color);
        fatherId=(TextView)findViewById(R.id.fatherId);
        motherId=(TextView)findViewById(R.id.motherId);
        immuneCardNum=(TextView)findViewById(R.id.immuneCardNum);
        orgName=(TextView)findViewById(R.id.orgName);
        lssueTime=(TextView)findViewById(R.id.lssueTime);
        deviceNumber=(TextView)findViewById(R.id.deviceNumber);
        orgName2=(TextView)findViewById(R.id.orgName2);
        lssueTime2=(TextView)findViewById(R.id.lssueTime2);
        endTime=(TextView)findViewById(R.id.endTime);
        injectionTime  =(TextView)findViewById(R.id.injectionTime  );
        vaccineName=(TextView)findViewById(R.id.vaccineName);
        vaccineProducer=(TextView)findViewById(R.id.vaccineProducer);
        name=(TextView)findViewById(R.id.name);
        orgName3=(TextView)findViewById(R.id.orgName3);
        violationDate=(TextView)findViewById(R.id.violationDate);
        violationPlace=(TextView)findViewById(R.id.violationPlace );
        violationType =(TextView)findViewById(R.id.violationType );
        fine=(TextView)findViewById(R.id.fine);

        try {
            JSONObject json = new JSONObject(dogInfo);
            JSONObject dogInfo=json.getJSONObject("dogInfo");//犬只信息
            JSONObject dogOwners=json.getJSONObject("dogOwners");//犬主信息
            JSONObject immuneCard=json.getJSONObject("immuneCard");//证件信息
            JSONObject dogCard=json.getJSONObject("dogCard");//犬证信息
            JSONArray urlLsit=json.getJSONArray("urlLsit");//图片
            JSONObject urlLsit_json=new JSONObject(urlLsit.getString(0));
            JSONArray devices=json.getJSONArray("devices");//设备信息
            JSONObject devices_json=new JSONObject(devices.getString(0));
            JSONArray vaccine=json.getJSONArray("vaccineList");//免疫信息
            JSONObject vaccine_json=null;
            if(vaccine.length()>0) {
                vaccine_json = new JSONObject(vaccine.getString(vaccine.length()-1));
            }
            JSONArray inspection=json.getJSONArray("inspectionsInfoList");//违法信息
            JSONObject inspection_json=null;
            if(inspection.length()>0) {
                inspection_json = new JSONObject(inspection.getString(inspection.length()-1));
            }

            mPicList.add(urlLsit_json.getString("imageUrl"));
            mPicList.add(urlLsit_json.getString("thumbnailUrl"));
            //犬主信息
            dogOwnerName.setText(dogOwners.getString("dogOwnerName"));
            if(dogOwners.getString("sex")!="null") {
                sex.setText(Integer.valueOf(dogOwners.getString("sex")) == 0 ? "男" : "女");
            }
            if(dogOwners.getString("cardType")!="null") {
                cardType.setText(Integer.valueOf(dogOwners.getString("cardType")) == 0 ? "身份证" : "其他");
            }
            cardNum.setText(dogOwners.getString("cardNum"));
            dogOwnerPhone.setText(dogOwners.getString("dogOwnerPhone"));
            districtName.setText(dogOwners.getString("districtName"));
            blackId.setText(dogOwners.getString("blackId"));
            address.setText(dogOwners.getString("address"));
//            犬只信息
            godName.setText(dogInfo.getString("dogName"));
            breed.setText(dogInfo.getString("breed"));
            if(dogInfo.getString("dogGender")!="null") {
                dogGender.setText(Integer.valueOf(dogInfo.getString("dogGender"))==0?"雄":"雌");
            }
            dogAge.setText(dogInfo.getString("dogAge"));
            birthTime.setText(dogInfo.getString("birthTime"));
            color.setText(dogInfo.getString("color"));
            fatherId.setText(dogInfo.getString("fatherId"));
            motherId.setText(dogInfo.getString("motherId"));
//            证件信息
            immuneCardNum.setText(immuneCard.getString("immuneCardNum"));
            orgName.setText(immuneCard.getString("orgName"));
            lssueTime.setText(immuneCard.getString("startTime"));
            deviceNumber.setText(devices_json.getString("deviceNumber"));
            orgName2.setText(dogCard.getString("orgName"));
            lssueTime2.setText(dogCard.getString("lssueTime"));
            endTime.setText(dogCard.getString("endTime"));
//            免疫信息
            if(vaccine_json!=null) {
                injectionTime.setText(vaccine_json.getString("injectionTime"));
                vaccineName.setText(vaccine_json.getString("vaccineName"));
                vaccineProducer.setText(vaccine_json.getString("vaccineProducer") + "  " + vaccine_json.getString("+vaccineNum"));
                name.setText(vaccine_json.getString("name"));
                orgName3.setText(vaccine_json.getString("orgName"));
            }
//            违法信息
            if(inspection_json!=null) {
                violationDate.setText(inspection_json.getString("violationDate"));
                violationPlace.setText(inspection_json.getString("violationPlace"));
                violationType.setText(inspection_json.getString("violationType "));
                fine.setText(inspection_json.getString("fine"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



//    初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(this, mPicList);
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                if (position == parent.getChildCount() - 1) {
//                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
//                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
//                        //最多添加5张图片
//                        viewPluImg(position);
//                    }
//                } else {
                viewPluImg(position);
//                }
            }
        });
    }




    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivity(intent);
//        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
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
