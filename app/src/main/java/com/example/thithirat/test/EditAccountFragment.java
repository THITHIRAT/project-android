package com.example.thithirat.test;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class EditAccountFragment extends Fragment {

    EditText et_newusername;
    EditText et_newemail;

    public EditAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit_account, container, false);

        getActivity().setTitle("My Account");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        final String str_token = prefs.getString("TOKEN", "null");

        ImageButton change_username = (ImageButton) view.findViewById(R.id.username_button);
        change_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.changeusername, null);

                builder.setView(rootview);
                final AlertDialog dialog_change_username = builder.create();
                dialog_change_username.show();

                connect_detail_username(str_token, rootview);
                et_newusername = (EditText) rootview.findViewById(R.id.new_username);

                Button save = (Button) rootview.findViewById(R.id.save_username);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connection_update_username(str_token, view);
                        dialog_change_username.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel_username);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_username.cancel();
                    }
                });
            }
        });

        ImageButton change_email = (ImageButton) view.findViewById(R.id.email_button);
        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.changeemail, null);

                builder.setView(rootview);
                final AlertDialog dialog_change_email = builder.create();
                dialog_change_email.show();

                connect_detail_email(str_token, rootview);
                et_newemail = (EditText) rootview.findViewById(R.id.new_email);

                Button save = (Button) rootview.findViewById(R.id.save_email);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connection_update_email(str_token, view);
                        dialog_change_email.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel_email);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_email.cancel();
                    }
                });
            }
        });

        ImageButton change_password = (ImageButton) view.findViewById(R.id.password_button);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.changepassword, null);

                builder.setView(rootview);
                final AlertDialog dialog_change_password = builder.create();
                dialog_change_password.show();

                Button save = (Button) rootview.findViewById(R.id.save_email);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connection_update_password(str_token, view);
                        dialog_change_password.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel_email);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_password.cancel();
                    }
                });
            }
        });

        return view;
    }

    private void connection_update_password(String str_token, View view) {
    }

    private void connection_update_username(String str_token, final View view) {
        try {
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "users/changeusername";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", str_token);
            final String new_username = et_newusername.getText().toString();
            jsonBody.put("newusername", new_username);
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
                                if(msg_users.equals("users/changeusername : update new username")) {
                                    View rootview_act = getLayoutInflater().inflate(R.layout.nav_header_home, null);
                                    TextView tv_username = (TextView) rootview_act.findViewById(R.id.username_nav_header);
                                    Log.e("NEW USERNAME" , new_username);
                                    tv_username.setText(new_username);
                                    Toast.makeText(getActivity(), "Change Username Complete", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
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

    private void connection_update_email(String str_token, final View view) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "users/changeemail";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", str_token);
            final String new_email = et_newemail.getText().toString();
            jsonBody.put("email", new_email);
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
                                if(msg_users.equals("users/changeusername : update new email")) {
                                    View rootview_act = getLayoutInflater().inflate(R.layout.nav_header_home, null);
                                    TextView tv_email = (TextView) rootview_act.findViewById(R.id.email_nav_header);
                                    Log.e("NEW EMAIL" , new_email);
                                    tv_email.setText(new_email);

                                    Toast.makeText(getActivity(), "Change Email Complete", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                                    startActivity(intent);
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

    private void connect_detail_username(String str_token, final View view) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                                if(msg_users.equals("users/detail : complete")) {
                                    JSONArray data = json.getJSONArray("data");
                                    JSONObject array = (JSONObject) data.get(0);
                                    String username = (String) array.get("username");
                                    TextView tv_username = (TextView) view.findViewById(R.id.current_username);
                                    tv_username.setText(username);
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

    private void connect_detail_email(String str_token, final View view) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                                if(msg_users.equals("users/detail : complete")) {
                                    JSONArray data = json.getJSONArray("data");
                                    JSONObject array = (JSONObject) data.get(0);
                                    String email = (String) array.get("email");
                                    TextView tv_email = (TextView) view.findViewById(R.id.current_email);
                                    tv_email.setText(email);
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
}
