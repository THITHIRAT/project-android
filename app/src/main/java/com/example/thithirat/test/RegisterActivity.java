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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final String REGISTER_URL = "http://161.246.5.195:3000/users/register";

    private EditText et_username;
    private EditText et_password;
    private EditText et_email;

    private Button register;

    String str_username;
    String str_password;
    String str_email;

    public String KEY_USERNAME = "username";
    public String KEY_PASSWORD = "password";
    public String KEY_EMAIL = "email";

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = (EditText)findViewById(R.id.username);
        et_password = (EditText)findViewById(R.id.password);
        et_email = (EditText)findViewById(R.id.email);

        str_username = et_username.getText().toString();
        str_password = et_password.getText().toString();
        str_email = et_email.getText().toString();

        register = (Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
                db.insertData(et_username.getText().toString(), et_password.getText().toString(), et_email.getText().toString());
                Log.e("INSERT DATA username", String.valueOf(et_username.getText()));
                Log.e("INSERT DATA password", String.valueOf(et_password.getText()));
                Log.e("INSERT DATA email", String.valueOf(et_email.getText()));

            }
        });
    }

    private void home() {
        final String username = str_username.trim();
        final String password = str_password.trim();
        final String email = str_email.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);

        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
