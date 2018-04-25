package com.sanved.slotbookingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sanved on 25-04-2018.
 */

public class AdminPanel extends AppCompatActivity{

    TextView tv, tvUser;
    String strName;
    String[] slots = {"","10 am","12 pm","4 pm"};
    String[] games = {"","Tennis","Badminton","Snooker"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        tvUser = findViewById(R.id.tvWel);
        tv = findViewById(R.id.tvStudent);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                strName = bundle.getString("user");
            }
        } else {
            strName = (String) savedInstanceState.getSerializable("user");
        }

        tv.setText("");
        tvUser.setText("Hello - " + strName);

        setInfo();

    }

    public void setInfo(){
        String url = "http://tapkeer.com/slot/admin_info.php";

        RequestQueue queue = Volley.newRequestQueue(AdminPanel.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("My success", "" + response);
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject reader = array.getJSONObject(i);
                        String name = reader.getString("userid");
                        String day = reader.getString("day");
                        String month = reader.getString("month");
                        String year = reader.getString("year");
                        String slot = reader.getString("slot");
                        String game = reader.getString("game");

                        String text
                                = "Name - " + name + " | "
                                + "Date - " + day + "/" + month + "/" + year + " | "
                                + "Slot - " + slots[Integer.parseInt(slot)] + " | "
                                + "Game - " + games[Integer.parseInt(game)] + " | ";

                        tv.setText(tv.getText().toString() + "\n" + text);
                    }
                } catch (Exception e) {
                    Toast.makeText(AdminPanel.this, "Data error 2", Toast.LENGTH_LONG).show();
                    Log.e("My error", e.toString());
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AdminPanel.this, "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("student", "69");

                return map;
            }
        };
        queue.add(request);
    }

}
