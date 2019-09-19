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
import com.example.dogshelter.DetailActivity;
import com.example.dogshelter.R;
import com.example.model.DogType;
import com.example.tooltype.HttpCallbackListener;
import com.example.tooltype.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class DogInquiryAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mcontext;
    private List<DogType> dogTypeList;
    private String address;
    private static final String TAG = "DogInquiryAdatper";

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public  final int LOADING = 1;
    // 加载完成
    public  final int LOADING_COMPLETE = 2;
    // 加载到底
    public  final int LOADING_END = 3;



    @Override
    public int getItemViewType(int position){
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public DogInquiryAdatper(List<DogType> dogTypeList,Context context,String address){
        this.dogTypeList=dogTypeList;
        mcontext=context;
        this.address=address;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType==TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dog_inquiry_item, parent, false);
            final  RecyclerViewHolder holder=new  RecyclerViewHolder(view);
            holder.DogTypeView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    DogType dogType=dogTypeList.get(position);
                    HttpUtil.sendRequestOKHttpAddress(address+"?cardNum=&dogId="+dogType.getId(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response, String token) {
                        }
                        @Override
                        public void onFinish(String response) {
                            Log.i(TAG, "测试犬只信息" + response);
//                            Log.i(TAG, "测试犬只信息a啊: "+parseJsonObject2(response));
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
                                Log.i(TAG, "测试犬只信息" + result);
                                Intent intent = new Intent(mcontext, DetailActivity.class);
                                intent.putExtra("dogInfo", result);
                                mcontext.startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.i(TAG, "测试犬只信息" + e.toString());
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
                    DogType dogType=dogTypeList.get(position);
                    HttpUtil.sendRequestOKHttpAddress(address+"?cardNum=&dogId="+dogType.getId(), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response, String token) {
                        }
                        @Override
                        public void onFinish(String response) {
                            if(response.contains("503 Service Unavailable")){
                                Looper.prepare();
                                Toast.makeText(mcontext, "服务器异常", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
//                            else if(parseJsonObject2(response).equals("000000")){
//                                Looper.prepare();
//                                Toast.makeText(mcontext, "此犬暂无详情可查", Toast.LENGTH_SHORT).show();
//                                Looper.loop();
//                            }
                            else {
                                String result = parseJsonObject(response);//犬只信息
                                Log.i(TAG, "测试犬只信息" + result);
                                Intent intent = new Intent(mcontext, DetailActivity.class);
                                intent.putExtra("dogInfo", result);
                                mcontext.startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.i(TAG, "测试犬只信息" + e.toString());
                            Looper.prepare();
                            Toast.makeText(mcontext, "网络异常请重试", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                }
            });


//            return new RecyclerViewHolder(view);
                return holder;
        }else if(viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }

//        final RecyclerView.ViewHolder holder=new RecyclerView.ViewHolder(view);
//        holder.DogTypeView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                int position=holder.getAdapterPosition();
//                DogType dogType=dogTypeList.get(position);
//                list.clear();
//                list.add(dogType.getId());
//                HttpUtil.sendRequestWithOKHttp(address, list, new HttpCallbackListener() {
//                    @Override
//                    public void onFinish(String response, String token) {
//                    }
//                    @Override
//                    public void onFinish(String response) {
//                        Log.i(TAG, "测试犬只信息" + response);
//                        Log.i(TAG, "测试犬只信息a啊: "+parseJsonObject2(response));
//                        if(response.contains("503 Service Unavailable")){
//                            Looper.prepare();
//                            Toast.makeText(mcontext, "服务器异常", Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        }else if(parseJsonObject2(response).equals("000000")){
//                            Looper.prepare();
//                            Toast.makeText(mcontext, "此犬暂无详情可查", Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        } else {
//                            String result = parseJsonObject(response);//犬只信息
//                            Intent intent = new Intent(mcontext, DogInquiryDetail.class);
//                            intent.putExtra("dogInfo", result);
//                            mcontext.startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Log.i(TAG, "测试犬只信息" + e.toString());
//                        Looper.prepare();
//                        Toast.makeText(mcontext, "网络异常请重试", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//            }
//        });


        return null;
//        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
//        holder.dog_type.setImageResource(R.mipmap.foot);
        if (holder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            Glide.with(mcontext).load(dogTypeList.get(position).getImageId()).into(recyclerViewHolder.dog_type);
            recyclerViewHolder.name.setText(dogTypeList.get(position).getName());
            recyclerViewHolder.integral.setText(dogTypeList.get(position).getIntegral());
            recyclerViewHolder.type.setText(dogTypeList.get(position).getType());
            recyclerViewHolder.age.setText(dogTypeList.get(position).getAge()+"岁");
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
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




    private String parseJsonObject(String data){
        try {
            JSONObject json = new JSONObject(data);
            JSONObject result=json.getJSONObject("result");
//            JSONObject dogInfo=result.getJSONObject("dogInfo");
            return  result.toString();
        }catch (JSONException e){
            return e.toString();
        }
    }

    private String parseJsonObject2(String data){
        try {
            JSONObject json = new JSONObject(data);
            return  json.getString("code");
        }catch (JSONException e){
            return e.toString();
        }
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        View DogTypeView;
        ImageView dog_type;//图片
        TextView name;//名字
        TextView integral_text;
        TextView integral;//积分
        TextView type;//种类
        TextView age;//年龄
        Button checkdetail;//查看详情

        RecyclerViewHolder(View itemView) {
            super(itemView);
            DogTypeView=itemView;
            dog_type=(ImageView)itemView.findViewById(R.id.dog_type);
            name=(TextView)itemView.findViewById(R.id.name);
            integral_text=(TextView)itemView.findViewById(R.id.integral_text);
            integral=(TextView)itemView.findViewById(R.id.integral);
            type=(TextView)itemView.findViewById(R.id.type);
            age=(TextView)itemView.findViewById(R.id.age);
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












}
