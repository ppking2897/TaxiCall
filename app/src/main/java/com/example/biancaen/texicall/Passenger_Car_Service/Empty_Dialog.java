package com.example.biancaen.texicall.Passenger_Car_Service;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.biancaen.texicall.R;

public class Empty_Dialog {
    private Context context;
    private int dialogEmptyCont , dialogEmptyTripPay;
    Empty_Dialog(Context context , int dialogEmptyCont , int dialogEmptyTripPay ){
        this.context = context;
        this.dialogEmptyCont = dialogEmptyCont;
        this.dialogEmptyTripPay = dialogEmptyTripPay;
    }
    public void CreateEmptyDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.CustomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty_dialog,null);

        LinearLayout knowButton =  (LinearLayout) view.findViewById(R.id.knowButton);
        ImageButton closeDialog = (ImageButton) view.findViewById(R.id.closeDialog);

        TextView textViewDialogEmptyCont = (TextView) view.findViewById(R.id.dialogEmptyCont);
        TextView textViewDialogEmptyTripPay = (TextView) view.findViewById(R.id.dialogEmptyTripPay);

        textViewDialogEmptyCont.setText(""+dialogEmptyCont);
        textViewDialogEmptyTripPay.setText(""+dialogEmptyTripPay);

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        knowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
