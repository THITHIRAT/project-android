package com.example.thithirat.test;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UCharacterEnums;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {

    private static final String REGISTER_URL = "http://161.246.5.195:3000/users/register";

    private EditText et_username;
    private EditText et_password;
    private EditText et_confirmpassword;
    private EditText et_email;

    private Button register;

    String str_username;
    String str_password;
    String str_confirmpassword;
    String str_email;

    public String KEY_USERNAME = "username";
    public String KEY_PASSWORD = "password";
    public String KEY_CONFIRMPASSWORD = "confirmpassword";
    public String KEY_EMAIL = "email";

    String token;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = (EditText)findViewById(R.id.username);
        et_password = (EditText)findViewById(R.id.password);
        et_confirmpassword = (EditText)findViewById(R.id.confirmpassword);
        et_email = (EditText)findViewById(R.id.email);

        register = (Button)findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = et_username.getText().toString();
                str_password = et_password.getText().toString();
                str_confirmpassword = et_confirmpassword.getText().toString();
                str_email = et_email.getText().toString();

                connect_register(str_username, str_password, str_confirmpassword, str_email);
            }
        });
    }

    private void connect_register(String str_username, String str_password, String str_confirmpassword, String str_email) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            String URL = ConnectAPI.getUrl() + "users/register";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", str_username);
            jsonBody.put("email", str_email);
            jsonBody.put("password", str_password);
            jsonBody.put("confirmpassword", str_confirmpassword);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_login = json.getString("msg");
                                Log.i("VOLLEY", msg_login);
                                if(msg_login.equals("users/register : user register sucessfully")) {
                                    token = json.getString("token");

                                    //from DatabaseHelper.java
                                    //db.insertDatatoken(token);
                                    //Log.e("INSERT DATA token", String.valueOf(token));
                                    home();

                                    //preference
                                    String token_name = "PUTGET_TOKEN";
                                    SharedPreferences.Editor editor = getSharedPreferences(token_name, MODE_PRIVATE).edit();
                                    editor.putString("TOKEN", token);
                                    editor.apply();
                                }
                                if(msg_login.equals("there are email to signup")) {
                                    Toast.makeText(RegisterActivity.this, "This email already used", Toast.LENGTH_LONG).show();
                                }
                                if(msg_login.equals("password not match")) {
                                    Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_LONG).show();
                                }
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

    private void home() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
