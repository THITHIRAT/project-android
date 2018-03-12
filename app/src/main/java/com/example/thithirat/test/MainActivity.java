package com.example.thithirat.test;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "http://161.246.5.195:3000/users/login";

    private EditText et_email;
    private EditText et_password;

    private Button login;
    private Button register;

    private TextView infoattempt;

    private int counter = 3;

    private String str_email;
    private String str_password;

    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email = (EditText) findViewById(R.id.username);
        et_password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        infoattempt = (TextView) findViewById(R.id.infoattempt);
        infoattempt.setText("No of attempts remaining: 3");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email = et_email.getText().toString();
                str_password = et_password.getText().toString();

                counter--;
                infoattempt.setText("No of attempts remaining: " + String.valueOf(counter));

                if(counter == 0) {
                    login.setEnabled(false);
                }

                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    String URL = "http://161.246.5.195:3000/users/login";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", str_email);
                    jsonBody.put("password", str_password);
                    final String requestBody = jsonBody.toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("VOLLEY", response);
                                    JSONObject json = null;
                                    try {
                                        json = new JSONObject(response);
                                        String loudScreaming = json.getString("msg");
                                        Log.i("VOLLEY", loudScreaming);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("VOLLEY", error.toString());
                                }
                            }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }
                    };
                    requestQueue.add(stringRequest);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void openHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra(KEY_EMAIL, str_email);
        startActivity(intent);
    }

    private void register(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}