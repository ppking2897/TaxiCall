package com.example.biancaen.texicall.Driver.Driver_Point_Record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

import java.util.ArrayList;


class Driver_Point_Record_Adapter extends RecyclerView.Adapter<Driver_Point_Record_Adapter.MyPointRecordAdapter> {
    private Context context;
    private ArrayList<String> date;
    private ArrayList<String> pointInfo;

    Driver_Point_Record_Adapter(Context context, ArrayList<String> date, ArrayList<String> pointInfo){
        this.context = context;
        this.date = date;
        this.pointInfo = pointInfo ;
    }

    @Override
    public MyPointRecordAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_point_record , parent , false);
        return new MyPointRecordAdapter(view);
    }

    @Override
    public void onBindViewHolder(MyPointRecordAdapter holder, int position) {
        holder.date.setText(date.get(position));
        holder.pointInfo.setText(pointInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return pointInfo.size();
    }

    class MyPointRecordAdapter extends RecyclerView.ViewHolder{
        TextView date;
        TextView pointInfo;
        MyPointRecordAdapter(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.pointDate);
            pointInfo = (TextView)itemView.findViewById(R.id.pointPointInfo);
        }
    }
}
