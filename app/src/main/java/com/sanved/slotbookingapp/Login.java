package com.sanved.slotbookingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Sanved on 10-04-2018.
 */

public class Login extends AppCompatActivity {

    EditText user, pass;
    Button add;
    ImageButton back;

    String struser, strpass;
    boolean isEdit = false;
    int keyid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initVals();

    }

    public void initVals(){

        // Just declaration and initialisation of the variables.

        user = (EditText) findViewById(R.id.etUser);
        pass = (EditText) findViewById(R.id.etPass);

        user.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(user, InputMethodManager.SHOW_IMPLICIT);


        add = (Button) findViewById(R.id.bAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Validations

                // If the user is empty
                if(user.getText().toString() == null){
                    Toast.makeText(Login.this, "Please enter the username", Toast.LENGTH_SHORT).show();
                }
                // If the pass is empty
                else if(pass.getText().toString() == null){
                    Toast.makeText(Login.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }

                else{

                    // TODO: 10-04-2018 Authentication

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


}
