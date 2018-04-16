package com.example.thithirat.test;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventCompleteFragment extends Fragment {

    ListView listview;

    List<LocationReminderComplete> mLocation;

    static String str_token;

    String task;
    String start_date;
    String end_date;


    public EventCompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_complete, container, false);

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        str_token = prefs.getString("TOKEN", "null");
        Log.e("Scheduled TOKEN", str_token);

        listview = (ListView)view.findViewById(R.id.list_view);
        mLocation = new ArrayList<>();

        connection_task_event_complete(str_token);

        return view;
    }

    private void connection_task_event_complete(String str_token) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/taskcomplete/event";
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
                                String msg_task_event = json.getString("msg");
                                Log.i("VOLLEY", msg_task_event);
                                if (msg_task_event.equals("taskcomplete/event : select reminder event complete")){
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Task Event", String.valueOf(data));
                                    for (int i=0; i < data.length(); i++) {
                                        JSONObject array = (JSONObject) data.get(i);
                                        task = (String) array.get("taskname");

                                        start_date = (String) array.get("start_date");
                                        String[] start = start_date.split("-");
                                        String start_year = start[0];
                                        String start_month = start[1];
                                        String start_day = start[2];
                                        String start_DDMMYYYY = start_day + "/" + start_month + "/" + start_year;

                                        end_date = (String) array.get("end_date");
                                        String[] end = start_date.split("-");
                                        String end_year = end[0];
                                        String end_month = end[1];
                                        String end_day = end[2];
                                        String end_DDMMYYYY = end_day + "/" + end_month + "/" + end_year;

                                        int reminder_id = (int) array.get("_id");
                                        int index = i+1;
                                        mLocation.add(new LocationReminderComplete(index, reminder_id, " ", start_DDMMYYYY + " - " + end_DDMMYYYY, task, "Event"));
                                        Log.e("Event Value", task + " / " + start_DDMMYYYY);
                                    }
                                    LocationReminderCompleteAdapter locationadapter = new LocationReminderCompleteAdapter(getContext().getApplicationContext(), mLocation);
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
