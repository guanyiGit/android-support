package com.example.selfadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dogshelter.HomeladDogShelterDetail;
import com.example.dogshelter.R;
import com.example.model.HomeShelterDog;
import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;


public class HomelandDogShelterAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private List<HomeShelterDog> dogTypeList;
    private String address;
    private static final String TAG = "HomelandDogShelterAdatp";


    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    public HomelandDogShelterAdatper(List<HomeShelterDog> dogTypeList, Context context, String address) {
        this.dogTypeList = dogTypeList;
        mcontext = context;
        this.address = address;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_homelad_dog_shelter_item, parent, false);
            final RecyclerViewHolder holder=new RecyclerViewHolder(view);
            holder.DogTypeView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    HomeShelterDog dogType=dogTypeList.get(position);
                    HttpUtil.sendRequestOKHttpAddress(address+"?collectionId="+dogType.getId(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response, String token) {
                        }
                        @Override
                        public void onFinish(String response) {
                            Log.i(TAG, "测试入和出: "+address);
                            if(response.contains("503 Service Unavailable")){
                                Looper.prepare();
                                Toast.makeText(mcontext, "服务器异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else if(response.contains("HTTP Status 400 – Bad Request")){
                                Looper.prepare();
                                Toast.makeText(mcontext, "服务器异常,请稍后重试！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
//                            else if(parseJsonObject2(response).equals("000000")){
//                                Looper.prepare();
//                                Toast.makeText(mcontext, "此犬暂无详情可查", Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }
                            else {
                                String result = parseJsonObject(response);//犬只信息
                                Log.i(TAG, "onFinish: "+result);
                                Intent intent = new Intent(mcontext, HomeladDogShelterDetail.class);
                                intent.putExtra("dogInfo", result);
                                mcontext.startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Looper.prepare();
                            Toast.makeText(mcontext, "网络异常请重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                }
            });

            holder.checkdetail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    HomeShelterDog dogType=dogTypeList.get(position);
                    HttpUtil.sendRequestOKHttpAddress(address+"?collectionId="+dogType.getId(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response, String token) {
                        }
                        @Override
                        public void onFinish(String response) {
                            Log.i(TAG, "测试入和出: "+address);
                            if(response.contains("503 Service Unavailable")){
                                Looper.prepare();
                                Toast.makeText(mcontext, "服务器异常，请稍后重试！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }else if(response.contains("HTTP Status 400 – Bad Request")){
                                Looper.prepare();
                                Toast.makeText(mcontext, "服务器异常,请稍后重试！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
//                            else if(parseJsonObject2(response).equals("000000")){
//                                Looper.prepare();
//                                Toast.makeText(mcontext, "此犬暂无详情可查", Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }
                            else {
                                String result = parseJsonObject(response);//犬只信息
                                Log.i(TAG, "onFinish: "+result);
                                Intent intent = new Intent(mcontext, HomeladDogShelterDetail.class);
                                intent.putExtra("dogInfo", result);
                                mcontext.startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Looper.prepare();
                            Toast.makeText(mcontext, "网络异常请重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                }
            });


            return holder;
        }else if(viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new HomelandDogShelterAdatper.FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
//        holder.dog_type.setImageResource(R.mipmap.foot);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        if (holder instanceof HomelandDogShelterAdatper.RecyclerViewHolder) {
            HomelandDogShelterAdatper.RecyclerViewHolder recyclerViewHolder = (HomelandDogShelterAdatper.RecyclerViewHolder) holder;
            Glide.with(mcontext).load(dogTypeList.get(position).getImageId()).into(recyclerViewHolder.dog_type);
            recyclerViewHolder.dogBreed.setText(dogTypeList.get(position).getDogBreed());
            recyclerViewHolder.dogGender.setText(dogTypeList.get(position).getDogGender());
            recyclerViewHolder.dogColor.setText(dogTypeList.get(position).getDogColor());
            recyclerViewHolder.dogAge.setText(Double.parseDouble(dogTypeList.get(position).getDogAge())+"岁");
//            recyclerViewHolder.collectionTime.setText("收容时间:"+formatter.format(dogTypeList.get(position).getSheltertime()));
        } else if (holder instanceof HomelandDogShelterAdatper.FootViewHolder) {
            HomelandDogShelterAdatper.FootViewHolder footViewHolder = (HomelandDogShelterAdatper.FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public int getItemCount(){
        return dogTypeList.size()+1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }



    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View DogTypeView;
        ImageView dog_type;//图片
        TextView dogBreed;//犬种类
        TextView dogGender;//性别
        TextView dogColor;//颜色
        TextView dogAge;//年龄
        TextView collectionTime;//收容时间
        Button checkdetail;//查看详情

        RecyclerViewHolder(View itemView) {
            super(itemView);
            DogTypeView=itemView;
            dog_type=(ImageView)itemView.findViewById(R.id.dog_type);
            dogBreed=(TextView)itemView.findViewById(R.id.dogBreed);
            dogGender=(TextView)itemView.findViewById(R.id.dogGender);
            dogColor=(TextView)itemView.findViewById(R.id.dogColor);
            dogAge=(TextView)itemView.findViewById(R.id.dogAge);
            collectionTime=(TextView)itemView.findViewById(R.id.collectionTime);
            checkdetail=(Button)itemView.findViewById(R.id.checkdetail);
        }
    }



    private class FootViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public  void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    private String parseJsonObject(String data){
        try {
            JSONObject json = new JSONObject(data);
            JSONObject result=json.getJSONObject("data");
            return  result.toString();
        }catch (JSONException e){
            return e.toString();
        }
    }

}
