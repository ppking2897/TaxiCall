package com.example.biancaen.texicall.Driver.Driver_Main_Menu;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.biancaen.texicall.R;


public class Driver_Main_Menu_Dialog {
    private Context context;
    public Driver_Main_Menu_Dialog(Context context){
        this.context =context;
    }

    public void CreatePointDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context , R.style.Driver_MainMenu_Dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.layou_main_menu_point_dialog,null);

        LinearLayout knowButton =  (LinearLayout) view.findViewById(R.id.knowButton);
        ImageView closeDialog = (ImageView) view.findViewById(R.id.closeDialog);


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
