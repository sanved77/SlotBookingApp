package com.sanved.slotbookingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by Sanved on 10-04-2018.
 */

public class GameSelect extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    CardView tennis, pool, badminton;
    TextView date,slot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_select);

        tennis = findViewById(R.id.cvTennis);
        pool = findViewById(R.id.cvPool);
        badminton = findViewById(R.id.cvBadminton);

        tennis.setOnClickListener(this);
        pool.setOnClickListener(this);
        badminton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.cvTennis:

                break;

            case R.id.cvPool:

                break;

            case R.id.cvBadminton:

                break;
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
