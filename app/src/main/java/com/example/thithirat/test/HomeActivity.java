package com.example.thithirat.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class HomeActivity extends AppCompatActivity {

    android.support.v4.app.FragmentManager fragmentmanager;
    FragmentTransaction fragmenttransaction;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getApplication().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        String str_token = prefs.getString("TOKEN", "null");
        Log.e("HOME_ACT TOKEN", str_token);

        connect_detail_user(str_token);

        fragmentmanager = getSupportFragmentManager();
        fragmenttransaction = fragmentmanager.beginTransaction();
        fragmenttransaction.replace(R.id.frag, new ScheduledFragment()).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.nav_list) {
                    setTitle("Scheduled");
                    FragmentTransaction fragmentTransaction= fragmentmanager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag, new ScheduledFragment()).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if (item.getItemId()== R.id.nav_calendar) {
                    setTitle("Calendar");
                    FragmentTransaction fragmentTransaction= fragmentmanager.beginTransaction();
                    fragmentTransaction.replace(R.id.frag, new CalendarFragment()).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if (item.getItemId()==R.id.nav_complete)
                {
                    setTitle("Complete");
                    FragmentTransaction fragmentTransaction1=fragmentmanager.beginTransaction();
                    fragmentTransaction1.replace(R.id.frag,new CompleteFragment()).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if (item.getItemId()==R.id.nav_settings)
                {
                    setTitle("Settings");
                    FragmentTransaction fragmentTransaction1=fragmentmanager.beginTransaction();
                    fragmentTransaction1.replace(R.id.frag,new MainSettingFragment()).commit();
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }

                if (item.getItemId()==R.id.nav_logout)
                {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);

                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        int id = getIntent().getIntExtra("id", 0);

        boolean location = getIntent().getBooleanExtra("location", false);
        if(id != 0) {
            if (location) {
                UpdateLocationFragment up_location_fragment = new UpdateLocationFragment(id);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, up_location_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

        boolean event = getIntent().getBooleanExtra("event", false);
        if(id != 0) {
            if (event) {
                UpdateEventFragment up_event_fragment = new UpdateEventFragment(id);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, up_event_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

        boolean reminder = getIntent().getBooleanExtra("reminder", false);
        if(id != 0) {
            if (reminder) {
                UpdateReminderFragment up_reminder_fragment = new UpdateReminderFragment(id);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, up_reminder_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }
    }

    void connect_detail_user(String str_token) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = ConnectAPI.getUrl() + "users/detail";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", str_token);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_users = json.getString("msg");
                                Log.i("VOLLEY HOME_ACT", msg_users);
                                if (msg_users.equals("users/detail : permission denied")) {
                                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                if (msg_users.equals("users/detail : complete")) {
                                    JSONArray data = json.getJSONArray("data");
                                    JSONObject array = (JSONObject) data.get(0);
                                    String username = (String) array.get("username");
                                    String email = (String) array.get("email");
                                    TextView tv_username = (TextView) findViewById(R.id.username_nav_header);
                                    TextView tv_email = (TextView) findViewById(R.id.email_nav_header);

                                    tv_username.setText(username);
                                    tv_email.setText(email);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

