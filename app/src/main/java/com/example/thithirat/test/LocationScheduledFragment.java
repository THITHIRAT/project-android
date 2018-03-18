package com.example.thithirat.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationScheduledFragment extends Fragment {

    ListView listview;

    List<LocationReminder> mLocation;
    LocationReminderAdapter locationadapter;

    static String placename;
    static String notification;
    static String task;

    public LocationScheduledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_scheduled, container, false);

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        final String str_token = prefs.getString("TOKEN", "null");
        Log.e("Scheduled TOKEN", str_token);

        Button button_map = (Button)view.findViewById(R.id.button_map);

        button_map.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        listview = (ListView)view.findViewById(R.id.list_view);
        mLocation = new ArrayList<>();

        connection_task_location(str_token);

//        locationadapter = new LocationReminderAdapter(view.getContext().getApplicationContext(), mLocation);
//        listview.setAdapter(locationadapter);

        return view;
    }

    private void connection_task_location(String str_token) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/task/location";
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
                                String msg_task_location = json.getString("msg");
                                Log.i("VOLLEY", msg_task_location);
                                if (msg_task_location.equals("query success")){
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Task Location", String.valueOf(data));
                                    for (int i=0; i < data.length(); i++) {
                                        JSONObject array = (JSONObject) data.get(i);
                                        placename = (String) array.get("placename");
                                        notification = (String) array.get("notification");
                                        task = (String) array.get("taskname");
                                        int index = i+1;
                                        mLocation.add(new LocationReminder(index, placename, notification, task));
                                        Log.e("Value", placename + notification + task);
                                    }
                                    locationadapter = new LocationReminderAdapter(getContext().getApplicationContext(), mLocation);
                                    listview.setAdapter(locationadapter);
                                }
                            }catch (JSONException e){
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

}
