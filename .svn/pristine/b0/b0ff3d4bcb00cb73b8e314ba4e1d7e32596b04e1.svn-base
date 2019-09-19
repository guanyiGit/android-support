package com.example.selfadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.dogshelter.DetailActivity;
import com.example.dogshelter.R;
import com.example.model.DogType;
import java.util.List;
import java.util.Map;

public class EnforcementAdatper extends RecyclerView.Adapter<EnforcementAdatper.ViewHolder> {
    private Context mcontext;
    private List<DogType> dogTypeList;
    private Map<String,String> map;
    private static final String TAG = "EnforcementAdatper";

    static class ViewHolder extends RecyclerView.ViewHolder{
        View DogTypeView;
        ImageView dog_type;//图片
        TextView name;//名字
        TextView integral_text;
        TextView integral;//积分
        TextView type;//种类
        TextView age;//年龄
        TextView id;//设备id
        Button status;//状态
        Button checkdetail;//查看详情

        public ViewHolder(View view){
            super(view);
            DogTypeView=view;
            dog_type=(ImageView)view.findViewById(R.id.dog_type);
            name=(TextView)view.findViewById(R.id.name);
            integral_text=(TextView)view.findViewById(R.id.integral_text);
            integral=(TextView)view.findViewById(R.id.integral);
            type=(TextView)view.findViewById(R.id.type);
            age=(TextView)view.findViewById(R.id.age);
            id=(TextView)view.findViewById(R.id.id);
            status=(Button)view.findViewById(R.id.status);
            checkdetail=(Button)view.findViewById(R.id.checkdetail);
        }
    }

    public EnforcementAdatper(List<DogType> dogTypeList,Context context,Map<String,String> map){
        this.dogTypeList=dogTypeList;
        mcontext=context;
        this.map=map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_type_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.DogTypeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Boolean flag=true;//判断是否有对应犬只信息
                int position=holder.getAdapterPosition();
                DogType dogType=dogTypeList.get(position);
                for(String key:map.keySet()) {
                    if(key.equals(dogType.getId())){
                        flag=false;
                        Intent intent = new Intent(mcontext, DetailActivity.class);
                        intent.putExtra("dogInfo",map.get(key));
                        Log.i(TAG, "测试传输数据:"+map.get(key));
                        mcontext.startActivity(intent);
                        break;
                    }
                }
                if(flag){
                    Toast.makeText(v.getContext(),"此犬只未绑定",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.checkdetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Boolean flag=true;//判断是否有对应犬只信息
                int position=holder.getAdapterPosition();
                DogType dogType=dogTypeList.get(position);
                for(String key:map.keySet()) {
                    if(key.equals(dogType.getId())){
                        flag=false;
                        Intent intent = new Intent(mcontext, DetailActivity.class);
                        intent.putExtra("dogInfo",map.get(key));
                        Log.i(TAG, "测试传输数据:"+map.get(key));
                        mcontext.startActivity(intent);
                        break;
                    }
                }
                if(flag){
                    Toast.makeText(v.getContext(),"此犬只未绑定",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        DogType dogType=dogTypeList.get(position);
//        holder.dog_type.setImageResource(dogType.getImageId());
        if(TextUtils.isEmpty(dogType.getName())){
            holder.dog_type.setImageResource(R.mipmap.foot);
            holder.name.setText("未绑定犬只");
            holder.integral_text.setText("");
            holder.integral.setText(dogType.getIntegral());
            holder.type.setText(dogType.getType());
            holder.age.setText(dogType.getAge());
            holder.id.setText(dogType.getId());
            holder.status.setText("未绑定");
            holder.checkdetail.setVisibility(View.INVISIBLE);
        }else {
            if(TextUtils.isEmpty(dogType.getImageId())){
                holder.dog_type.setImageResource(R.mipmap.foot);
            }else {
                Glide.with(mcontext).load(dogType.getImageId()).into(holder.dog_type);
            }
            holder.name.setText(dogType.getName());
            holder.integral.setText(dogType.getIntegral());
            holder.type.setText(dogType.getType());
            holder.age.setText(dogType.getAge()+"岁");
            holder.id.setText("设备ID："+dogType.getId());
        }
    }

    @Override
    public int getItemCount(){
        return dogTypeList.size();
    }


//    @GlideModule
//    public final class MyAppGlideModule extends AppGlideModule{}

}
