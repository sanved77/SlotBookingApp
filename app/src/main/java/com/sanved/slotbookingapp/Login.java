package com.sanved.slotbookingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sanved on 10-04-2018.
 */

public class Login extends AppCompatActivity {

    EditText user, pass;
    Button add;
    ImageButton back;
    ArrayList<UserDat> list;

    String struser, strpass;
    boolean isEdit = false;
    int keyid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initVals();

    }

    public void initVals() {

        // Just declaration and initialisation of the variables.

        user = (EditText) findViewById(R.id.etUser);
        pass = (EditText) findViewById(R.id.etPass);

        user.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(user, InputMethodManager.SHOW_IMPLICIT);


        add = (Button) findViewById(R.id.bLogin);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validations

                // If the user is empty
                if (user.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please enter the username", Toast.LENGTH_SHORT).show();
                }
                // If the pass is empty
                else if (pass.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                } else {

                    getDataFromServer();

                }

            }
        });

        back = (ImageButton) findViewById(R.id.ibBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getDataFromServer(){
        String url = "http://tapkeer.com/slot/user.php";

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            String usertemp, passtemp;
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);


                                usertemp = product.getString("user");
                                passtemp = product.getString("pass");
                                Log.d("user - " + usertemp, "pass - " + passtemp);

                                list.add(new UserDat(usertemp, passtemp));

                            }

                            checkAndValidate(user.getText().toString() , pass.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(Login.this).add(stringRequest);
    }

    public void checkAndValidate(String usert, String passt){

        int flag = 0;

        for(int i = 0; i < list.size(); i++){
            if(usert.equals(list.get(i).user) && passt.equals(list.get(i).pass)){
                flag = 1;
                Toast.makeText(this, "Nagdi Bai zindabaad", Toast.LENGTH_SHORT).show();
            }
        }

        if(flag == 0){
            Toast.makeText(this, "Login Failed, please check username and pass again", Toast.LENGTH_SHORT).show();
        }

    }

}
