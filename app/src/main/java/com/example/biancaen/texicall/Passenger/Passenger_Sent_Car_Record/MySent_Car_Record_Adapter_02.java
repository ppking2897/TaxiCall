package com.example.biancaen.texicall.Passenger.Passenger_Sent_Car_Record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

import java.util.ArrayList;


class MySent_Car_Record_Adapter_02 extends RecyclerView.Adapter<MySent_Car_Record_Adapter_02.MyViewHolder02>{
    private Context context;
    private ArrayList<String> date;
    private ArrayList<String> getOn;
    private ArrayList<String> getOff;
    private ArrayList<String> time;

    public MySent_Car_Record_Adapter_02(
            Context context , ArrayList<String> date , ArrayList<String> getOn ,
            ArrayList<String> getOff , ArrayList<String> time){

        this.context = context;
        this.date = date;
        this.getOn = getOn;
        this.getOff = getOff;
        this.time = time;
    }
    @Override
    public MyViewHolder02 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_sent_car_02_ , parent ,false);
        MyViewHolder02 holder02 = new MyViewHolder02(view);
        return holder02;
    }

    @Override
    public void onBindViewHolder(MyViewHolder02 holder, int position) {
        holder.dateText.setText(date.get(position));
        holder.getOnText.setText(getOn.get(position));
        holder.getOffText.setText(getOff.get(position));
        holder.timeText.setText(time.get(position));
    }

    @Override
    public int getItemCount() {
        //date.size();
        return date.size();
    }
    class MyViewHolder02 extends RecyclerView.ViewHolder{
        TextView dateText;
        TextView getOnText;
        TextView getOffText;
        TextView timeText;
        MyViewHolder02(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.sent_car_02_date);
            getOnText = (TextView) itemView.findViewById(R.id.sent_car_02_get_on);
            getOffText = (TextView) itemView.findViewById(R.id.sent_car_02_get_off);
            timeText = (TextView) itemView.findViewById(R.id.sent_car_02_time);
        }
    }
}