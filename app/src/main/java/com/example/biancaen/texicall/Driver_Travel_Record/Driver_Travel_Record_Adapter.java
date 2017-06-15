package com.example.biancaen.texicall.Driver_Travel_Record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

import java.util.ArrayList;


class Driver_Travel_Record_Adapter extends RecyclerView.Adapter<Driver_Travel_Record_Adapter.MyDriverHolder> {

    private Context context;
    private ArrayList<String> date;
    private ArrayList<String> getOn;
    private ArrayList<String> getOff;
    private ArrayList<String> time;
    private ArrayList<String> rate;

    Driver_Travel_Record_Adapter(
            Context context, ArrayList<String> date, ArrayList<String> getOn,
            ArrayList<String> getOff, ArrayList<String> time, ArrayList<String> rate){

        this.context = context;
        this.date = date;
        this.getOn = getOn;
        this.getOff = getOff;
        this.time = time;
        this.rate = rate;

    }

    @Override
    public MyDriverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_driver_travel_record , parent , false);
        return new MyDriverHolder(view);
    }

    @Override
    public void onBindViewHolder(MyDriverHolder holder, int position) {
        holder.dateText.setText(date.get(position));
        holder.getOnText.setText(getOn.get(position));
        holder.getOffText.setText(getOff.get(position));
        holder.timeText.setText(time.get(position));
        holder.rateText.setText(rate.get(position));
    }

    @Override
    public int getItemCount() {
        return rate.size();
    }

    class MyDriverHolder extends RecyclerView.ViewHolder{
        TextView dateText;
        TextView getOnText;
        TextView getOffText;
        TextView timeText;
        TextView rateText;
        MyDriverHolder(View itemView) {
            super(itemView);
            dateText = (TextView) itemView.findViewById(R.id.driver_record_date);
            getOnText = (TextView) itemView.findViewById(R.id.driver_record_get_on);
            getOffText = (TextView) itemView.findViewById(R.id.driver_record_get_off);
            timeText = (TextView) itemView.findViewById(R.id.driver_record_time);
            rateText = (TextView) itemView.findViewById(R.id.driver_record_rate);

        }
    }
}
