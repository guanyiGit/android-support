package com.example.selfadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dogshelter.R;

import java.util.List;

public class SimpleListviewAdatper extends ArrayAdapter<String> {
    private int resuorceId;
    public SimpleListviewAdatper(Context context, int textViewResourceId, List<String> list){
        super(context,textViewResourceId,list);
        resuorceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String e_dgnumber=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view = LayoutInflater.from(getContext()).inflate(resuorceId, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.dog_number=(TextView)view.findViewById(R.id.dog_number);
            view.setTag(viewHolder);
        }else{
         view=convertView;
         viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.dog_number.setText(e_dgnumber);
        return view;
    }

    class ViewHolder{
        TextView dog_number;
    }
}
