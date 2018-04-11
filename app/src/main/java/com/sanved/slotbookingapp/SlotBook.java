package com.sanved.slotbookingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Sanved on 10-04-2018.
 */

public class SlotBook extends AppCompatActivity implements View.OnClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    TextView date, slot;
    Button setdate, setslot, check;
    static int day, month, year;
    int slotnum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slot_book);

        date = findViewById(R.id.tvDate);
        slot = findViewById(R.id.tvSlot);
        slotnum = 1;

        setdate = findViewById(R.id.bDate);
        setslot = findViewById(R.id.bSlot);
        check = findViewById(R.id.bCheck);

        setdate.setOnClickListener(this);
        setslot.setOnClickListener(this);
        check.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bDate:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SlotBook.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;

            case R.id.bSlot:


                AlertDialog.Builder build = new AlertDialog.Builder(SlotBook.this);

                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.slot_select, null);

                final RadioGroup radioSexGroup = dialogView.findViewById(R.id.radioSex);

                build
                        .setTitle("Select Slot")
                        .setView(dialogView)
                        .setMessage("Select one of the three slots")
                        .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selectedId = radioSexGroup.getCheckedRadioButtonId();

                                switch (selectedId) {
                                    case R.id.r1:
                                        slotnum = 1;
                                        break;
                                    case R.id.r2:
                                        slotnum = 2;
                                        break;
                                    case R.id.r3:
                                        slotnum = 3;
                                        break;
                                }
                            }
                        })
                        .setCancelable(false);

                build.create().show();

                slot.setText("Slot - " + slotnum);

                break;

            case R.id.bCheck:



                break;
        }

    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth;
        month = monthOfYear;
        this.year = year;

        date.setText(dayOfMonth + "/" + monthOfYear+ "/" + year);

    }


}


