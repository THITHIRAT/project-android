package com.example.thithirat.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

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


@SuppressLint("ValidFragment")
public class UpdateLocationFragment extends Fragment {

    Spinner spinner;
    ArrayAdapter<String> adapter;

    static EditText editstrplacename;
    static EditText editstrtaskname;

    static String str_placename;
    String str_taskname;
    String str_notification;

    int reminder_id = 0;
    @SuppressLint("ValidFragment")
    public UpdateLocationFragment(int id) {
        // Required empty public constructor
        this.reminder_id = id;
        Log.e("UpdateLocation", String.valueOf(id));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_location, container, false);

        getActivity().setTitle("Edit Location");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        final String str_token = prefs.getString("TOKEN", "null");
        Log.e("Update Location TOKEN", str_token);

        spinner = (Spinner)view.findViewById(R.id.spinner_location);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_location_notification));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editstrplacename = (EditText)view.findViewById(R.id.add_place);
        editstrtaskname = (EditText)view.findViewById(R.id.add_name_task);

        //show detail each reminder_id
        connection_show();

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.marker_map);
        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateLocationMapsActivity.class);
                startActivity(intent);
            }
        });

        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.confirm_delete, null);

                builder.setView(rootview);
                final AlertDialog dialog_delete = builder.create();
                dialog_delete.show();

                Button ok = (Button) rootview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connection_delete(reminder_id);
                        ScheduledFragment scheduled_fragment = new ScheduledFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_delete.cancel();
                    }
                });
            }
        });

        Button update = (Button) view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.confirm_update, null);

                builder.setView(rootview);
                final AlertDialog dialog_update = builder.create();
                dialog_update.show();

                Button ok = (Button) rootview.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connection_update(reminder_id);
                        ScheduledFragment scheduled_fragment = new ScheduledFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        dialog_update.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_update.cancel();
                    }
                });
            }
        });

        return view;
    }

    private void connection_show() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "detailreminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_location = json.getString("msg");
                                Log.i("VOLLEY", msg_location);
                                if (msg_location.equals("detailreminder/task : complete")){
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Task Location", String.valueOf(data));
                                    JSONObject array = (JSONObject) data.get(0);
                                    str_placename = (String) array.get("placename");
                                    editstrplacename.setText(str_placename);
                                    str_notification = (String) array.get("notification");
                                    int i = 0;
                                    if(str_notification.equals("Arrive")) {
                                        i = 0;
                                    }
                                    if(str_notification.equals("Depart")) {
                                        i = 1;
                                    }
                                    if(str_notification.equals("Pass")) {
                                        i = 2;
                                    }
                                    spinner.setSelection(i);
                                    str_taskname = (String) array.get("taskname");
                                    editstrtaskname.setText(str_taskname);
                                    reminder_id = (int) array.get("_id");

                                    Log.e("Location Value", str_placename + " / " + str_notification + " / " + str_taskname);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void connection_update(int reminder_id) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "updatereminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Location");
            jsonBody.put("notification", spinner.getSelectedItem().toString());
            jsonBody.put("placename", editstrplacename.getText().toString());
            jsonBody.put("taskname", editstrtaskname.getText().toString());

            Log.e("Update location", spinner.getSelectedItem().toString() + " " + editstrplacename.getText().toString() + " " + editstrtaskname.getText().toString());

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_update = json.getString("msg");
                                Log.i("VOLLEY", msg_update);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void connection_delete(int reminder_id) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "deletereminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_delete = json.getString("msg");
                                Log.i("VOLLEY", msg_delete);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void putArguments(Bundle args) {
        String placename = args.getString("PlaceName");
        Log.d("Value place ", placename);
        editstrplacename.setText(placename);
        str_placename = args.getString("PlaceName");
    }
}
