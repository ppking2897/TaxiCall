package com.example.biancaen.texicall;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Sent_Car_02_Record_Fragment extends Fragment {
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> getOn = new ArrayList<>();
    private ArrayList<String> getOff = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_02 , container , false);

        //Todo 預計顯示乘車資訊存放位置
        date.add("2017-04-20");
        date.add("2017-04-20");
        date.add("2017-04-20");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOn.add("上車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        getOff.add("下車地點/");
        time.add("乘車時間/");
        time.add("乘車時間/");
        time.add("乘車時間/");


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sent_car_02_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdapter_02 myAdapter_02 = new MyAdapter_02(getContext() , date , getOn , getOff , time );

        recyclerView.setAdapter(myAdapter_02);

        return view;
    }
}

class MyAdapter_02 extends RecyclerView.Adapter<MyViewHolder02>{
    private Context context;
    private ArrayList<String> date;
    private ArrayList<String> getOn;
    private ArrayList<String> getOff;
    private ArrayList<String> time;

    public MyAdapter_02(Context context , ArrayList<String> date , ArrayList<String> getOn , ArrayList<String> getOff , ArrayList<String> time){
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
}

class MyViewHolder02 extends RecyclerView.ViewHolder{
    TextView dateText;
    TextView getOnText;
    TextView getOffText;
    TextView timeText;
    public MyViewHolder02(View itemView) {
        super(itemView);
        dateText = (TextView) itemView.findViewById(R.id.sent_car_02_date);
        getOnText = (TextView) itemView.findViewById(R.id.sent_car_02_get_on);
        getOffText = (TextView) itemView.findViewById(R.id.sent_car_02_get_off);
        timeText = (TextView) itemView.findViewById(R.id.sent_car_02_time);
    }
}

