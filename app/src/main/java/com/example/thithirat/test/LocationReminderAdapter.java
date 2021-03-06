package com.example.thithirat.test;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.List;

/**
 * Created by Thithirat on 6/3/2561.
 */

public class LocationReminderAdapter extends BaseAdapter{

    Context mContext;
    private Fragment currentfragment;
    private List<LocationReminder> mLocationReminder;

    int position_location;
    private FragmentActivity activity;

    public LocationReminderAdapter(Context mcontext, List<LocationReminder> mLocationReminder) {
        this.mContext = mcontext;
        this.mLocationReminder = mLocationReminder;
    }

    @Override
    public int getCount() {
        return mLocationReminder.size();
    }

    @Override
    public Object getItem(int position) {
        return mLocationReminder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = View.inflate(mContext, R.layout.location_reminder, null);

        TextView textviewname = (TextView)view.findViewById(R.id.lc_place_name);
        textviewname.setMaxLines(1);

        TextView textviewnoti = (TextView)view.findViewById(R.id.lc_type_noti);
        textviewnoti.setMaxLines(1);

        TextView textviewtask = (TextView)view.findViewById(R.id.lc_task_name);
        textviewnoti.setMaxLines(1);

        textviewname.setText(mLocationReminder.get(position).getName());
        textviewnoti.setText(mLocationReminder.get(position).getNoti());
        textviewtask.setText(mLocationReminder.get(position).getTask());

        view.setTag(mLocationReminder.get(position).getId());
        final String type = mLocationReminder.get(position).getType();
        final int id = mLocationReminder.get(position).getReminder_id();

        final CheckBox lc_check_complete = (CheckBox)view.findViewById(R.id.lc_complete);
        lc_check_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                position_location = position;
                if(isChecked) {
                    Log.e("Checkif", String.valueOf(isChecked) + " " + position);
                    connect_complete_location(position);
                }else {
                    Log.e("Checkelse", String.valueOf(isChecked) + " " + position);
                    connect_incomplete_location(position);
                }
            }
        });

        RelativeLayout task = (RelativeLayout)view.findViewById(R.id.task);
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position_location = position;
                Log.e("OnclickTask", "Type : " + type + " & Position : " + String.valueOf(position_location));
                if (type.equals("Location")) {
                    Intent intent = new Intent(mContext.getApplicationContext(), HomeActivity.class);
                    intent.putExtra("location", true);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
                if (type.equals("Event")) {
                    Intent intent = new Intent(mContext.getApplicationContext(), HomeActivity.class);
                    intent.putExtra("event", true);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
                if (type.equals("Reminder")) {
                    Intent intent = new Intent(mContext.getApplicationContext(), HomeActivity.class);
                    intent.putExtra("reminder", true);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
            }

        });

        return view;
    }

    private void connect_complete_location(int position) {
        int reminder_id = mLocationReminder.get(position).getReminder_id();

        Log.d("Complete Location", String.valueOf(reminder_id));

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            String URL = ConnectAPI.getUrl() + "complete/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("reminder_id", reminder_id);;
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
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void connect_incomplete_location(int position) {
        int reminder_id = mLocationReminder.get(position).getReminder_id();

        Log.d("Complete Location", String.valueOf(reminder_id));

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            String URL = ConnectAPI.getUrl() + "incomplete/location";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("reminder_id", reminder_id);;
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
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
