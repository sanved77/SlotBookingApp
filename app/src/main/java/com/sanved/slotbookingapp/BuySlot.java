package com.sanved.slotbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sanved on 11-04-2018.
 */

public class BuySlot extends AppCompatActivity {

    EditText et1,et2;
    Button buy;
    int day,month,year,slotnum,game;
    String strName;
    TextView summary;

    String url = "http://tapkeer.com/slot/buyslot.php";

    public String[] gametype = {"dummy","Tennis", "Badminton", "Snooker"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_slot);

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

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        summary = findViewById(R.id.tvSum);

        String summ = "Game - " + gametype[game] + "\n"
                + "Date - " + day + "/" + month + "/" + year + "\n"
                + "Slot - " + slotnum;

        summary.setText(summ);

        buy = findViewById(R.id.bBuy);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String se1 = et1.getText().toString();
                String se2 = et2.getText().toString();

                if(se1.isEmpty() || se2.isEmpty()){
                    Toast.makeText(BuySlot.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    sendData();
                }

            }
        });

    }

    public void sendData(){
        RequestQueue queue = Volley.newRequestQueue(BuySlot.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int test = 0;

                try {
                    JSONObject reader = new JSONObject(response);
                    test = reader.getInt("result");
                }catch(Exception e){
                    Log.e("",e.toString());
                }

                if(test == 1) {

                    Intent intent = new Intent(BuySlot.this, OrderConfirm.class);
                    intent.putExtra("day", "" + day);
                    intent.putExtra("month", "" + month);
                    intent.putExtra("year", "" + year);
                    intent.putExtra("slot", "" + slotnum);
                    intent.putExtra("game", "" + game);
                    intent.putExtra("user", strName);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BuySlot.this, "There is some error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BuySlot.this, "my error :"+error, Toast.LENGTH_LONG).show();
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

                map.put("user", strName);

                map.put("cardno", ""+et1.getText().toString());
                map.put("cardname", ""+et2.getText().toString());

                return map;
            }
        };
        queue.add(request);
    }

}
