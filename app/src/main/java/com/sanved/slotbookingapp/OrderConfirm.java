package com.sanved.slotbookingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Sanved on 11-04-2018.
 */

public class OrderConfirm extends AppCompatActivity {

    TextView tv;

    int day,month,year,slotnum,game;
    String strName;

    public String[] gametype = {"dummy","Tennis", "Badminton", "Snooker"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                day = Integer.parseInt(bundle.getString("day"));
                month = Integer.parseInt(bundle.getString("month"));
                year = Integer.parseInt(bundle.getString("year"));
                slotnum = Integer.parseInt(bundle.getString("slot"));
                game = Integer.parseInt(bundle.getString("game"));
                strName = bundle.getString("user");

            }
        }else{
            day = (int) savedInstanceState.getSerializable("day");
            month = (int) savedInstanceState.getSerializable("month");
            year = (int) savedInstanceState.getSerializable("year");
            slotnum = (int) savedInstanceState.getSerializable("slot");
            game = (int) savedInstanceState.getSerializable("game");
            strName = (String) savedInstanceState.getSerializable("user");
        }

        tv = findViewById(R.id.tvSum);

        String summ = "Game - " + gametype[game] + "\n"
                + "Date - " + day + "/" + month + "/" + year + "\n"
                + "Slot - " + slotnum;

        tv.setText(summ);


    }
}
