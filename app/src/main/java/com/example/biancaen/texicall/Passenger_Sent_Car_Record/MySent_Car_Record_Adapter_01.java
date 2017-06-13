package com.example.biancaen.texicall.Passenger_Sent_Car_Record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

import java.util.ArrayList;


class MySent_Car_Record_Adapter_01 extends RecyclerView.Adapter<MySent_Car_Record_Adapter_01.MyViewHolder01>{
    private Context context;
    private ArrayList<String> address_Record;
    MySent_Car_Record_Adapter_01(Context context, ArrayList<String> address_Record){
        this.context = context;
        this.address_Record = address_Record;
    }
    @Override
    public MyViewHolder01 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_sent_car_01_,parent,false);

        return new MyViewHolder01(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder01 holder, int position) {
        holder.textView.setText(address_Record.get(position));
    }

    @Override
    public int getItemCount() {
        return address_Record.size();
    }

    class MyViewHolder01 extends RecyclerView.ViewHolder{
        TextView textView;
        MyViewHolder01(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.sent_car_textView_01);
        }
    }
}

