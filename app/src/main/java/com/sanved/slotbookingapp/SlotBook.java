package com.sanved.slotbookingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sanved on 10-04-2018.
 */

public class SlotBook extends AppCompatActivity implements View.OnClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    TextView date, slot, result;
    Button setdate, setslot, check, bookSlot;

    static int day, month, year, game;
    String strName;
    int slotnum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slot_book);

        game = 1;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                game = Integer.parseInt(extras.getString("game"));
                strName = extras.getString("user");
            }
        }else{
            game = (int) savedInstanceState.getSerializable("game");
            strName = (String) savedInstanceState.getSerializable("user");
        }

        date = findViewById(R.id.tvDate);
        slot = findViewById(R.id.tvSlot);
        result = findViewById(R.id.tvResult);
        slotnum = 1;

        setdate = findViewById(R.id.bDate);
        setslot = findViewById(R.id.bSlot);
        check = findViewById(R.id.bCheck);
        bookSlot = findViewById(R.id.bBook);

        setdate.setOnClickListener(this);
        setslot.setOnClickListener(this);
        check.setOnClickListener(this);
        bookSlot.setOnClickListener(this);

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
                                        slot.setText("Slot - " + slotnum);
                                        break;
                                    case R.id.r2:
                                        slotnum = 2;
                                        slot.setText("Slot - " + slotnum);
                                        break;
                                    case R.id.r3:
                                        slotnum = 3;
                                        slot.setText("Slot - " + slotnum);
                                        break;
                                }
                            }
                        })
                        .setCancelable(false);

                build.create().show();

                break;

            case R.id.bCheck:

                String url = "http://tapkeer.com/slot/book.php";

                RequestQueue queue = Volley.newRequestQueue(SlotBook.this);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int test = 69;
                        Log.i("My success",""+response);

                        try {
                            JSONObject reader = new JSONObject(response);
                            test = reader.getInt("result");
                        }catch(Exception e){
                            Log.e("",e.toString());
                        }

                        if(test == 0){
                            result.setTextColor(ContextCompat.getColor(SlotBook.this, R.color.yellow));
                            result.setText("Slot already booked");
                        }else if(test == 1){
                            result.setTextColor(ContextCompat.getColor(SlotBook.this, R.color.white));
                            result.setText("Slot Available");
                            bookSlot.setVisibility(View.VISIBLE);
                        }else{
                            result.setTextColor(ContextCompat.getColor(SlotBook.this, R.color.red));
                            result.setText("Error Occurred");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SlotBook.this, "my error :"+error, Toast.LENGTH_LONG).show();
                        Log.i("My error",""+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<String, String>();
                        map.put("day", ""+day);
                        map.put("month", ""+month);
                        map.put("year", ""+year);
                        map.put("slot", ""+slotnum);
                        map.put("game", ""+game);

                        return map;
                    }
                };
                queue.add(request);

                break;

            case R.id.bBook:

                Intent intent = new Intent(SlotBook.this, BuySlot.class);
                intent.putExtra("day", ""+day);
                intent.putExtra("month", ""+month);
                intent.putExtra("year", ""+year);
                intent.putExtra("slot", ""+slotnum);
                intent.putExtra("game", ""+game);
                intent.putExtra("user", strName);
                startActivity(intent);

                break;
        }

    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth;
        month = monthOfYear + 1;
        this.year = year;

        date.setText(dayOfMonth + "/" + month + "/" + year);

    }


}


