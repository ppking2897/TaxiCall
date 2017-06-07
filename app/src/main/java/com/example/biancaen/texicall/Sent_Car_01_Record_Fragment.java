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

public class Sent_Car_01_Record_Fragment extends Fragment {
    private ArrayList<String> address_Record = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_car_01 , container , false);

        //-----Todo 地址紀錄預備位置-----
        address_Record.add("新北市板橋區中正路二段542巷18弄64之7號");
        address_Record.add("新北市三重區吳興街329號1樓");
        address_Record.add("台北市信義區忠孝東路二段398巷26號");
        address_Record.add("台北市信義區忠孝東路二段398巷26號");



        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.sent_car_01_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdapter myAdapter = new MyAdapter(getContext(),address_Record);

        recyclerView.setAdapter(myAdapter);

        return view;
    }

}


class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private ArrayList<String> address_Record;
    public MyAdapter (Context context , ArrayList<String> address_Record){
        this.context = context;
        this.address_Record = address_Record;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_sent_car_01_,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(address_Record.get(position));
    }

    @Override
    public int getItemCount() {
        return address_Record.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.sent_car_textView_01);
    }
}
