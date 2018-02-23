package com.example.thithirat.test;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private Button login;
    private Button register;

    private TextView infoattempt;

    private int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);

        infoattempt = (TextView)findViewById(R.id.infoattempt);
        infoattempt.setText("No of attempts remaining: 3");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void validate(String username, String password) {
        if((username.equals("root")) && (password.equals("1234"))) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            counter--;

            infoattempt.setText("No of attempts remaining: " + String.valueOf(counter));

            if(counter == 0) {
                login.setEnabled(false);
            }
        }
    }

    private void register(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
