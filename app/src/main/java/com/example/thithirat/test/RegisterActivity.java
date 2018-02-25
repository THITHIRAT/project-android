package com.example.thithirat.test;

import android.app.Activity;
import android.content.Intent;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

    private EditText username;
    private EditText password;
    private EditText email;

    private Button login;
    private Button register;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
                db.insertData(username.getText().toString(), password.getText().toString(), email.getText().toString());
                Log.e("INSERT DATA username", String.valueOf(username.getText()));
                Log.e("INSERT DATA password", String.valueOf(password.getText()));
                Log.e("INSERT DATA email", String.valueOf(email.getText()));
            }
        });
    }

    private void login(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void home() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
