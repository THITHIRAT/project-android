package com.example.thithirat.test;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddReminderFragment extends Fragment {

    private static View getrootview;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    ArrayAdapter<String> adapter;

    List<Taskname> mtaskname;
    TasknameAdapter mtasknameAdapter;

    List<Taskname> msubtaskname;
    SubtasknameAdapter msubtasknameAdapter;

    ListView listview;

    Spinner spinner_before_after_1;
    Spinner spinner_type_date_1;

    Spinner spinner_before_after_2;
    Spinner spinner_type_date_2;

    Spinner spinner_before_after_3;
    Spinner spinner_type_date_3;

    String str_before_after_1;
    String str_number_1;
    String str_type_date_1;

    String str_before_after_2;
    String str_number_2;
    String str_type_date_2;

    String str_before_after_3;
    String str_number_3;
    String str_type_date_3;

    static EditText et_addplace;

    String str_token;

    String con_str_startdate = null;
    String con_str_startmonth = null;
    String con_str_startyear = null;

    String con_str_enddate = null;
    String con_str_endmonth = null;
    String con_str_endyear = null;

    String con_str_starthour = null;
    String con_str_startmin = null;

    String con_str_endhour = null;
    String con_str_endmin = null;

    TextView et_addtaskname;
    TextView et_addsubtaskname;

    String sugguest_taskname;
    String sugguest_addtaskname;
    EditText taskname_popup;
    EditText subtaskname_popup;

    Switch onOffSwitch;
    String con_str_onoffswitch;

    Button btnstartdate;
    Button btnenddate;
    String str_startdate;
    String str_enddate;

    Button btnstarttime;
    Button btnendtime;
    String str_starttime;
    String str_endtime;

    TextView et_before_after_1;
    TextView et_number_1;
    TextView et_type_date_1;

    TextView et_before_after_2;
    TextView et_number_2;
    TextView et_type_date_2;

    TextView et_before_after_3;
    TextView et_number_3;
    TextView et_type_date_3;

    String get_tasknamefromfragment;

    public AddReminderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AddReminderFragment(String str_taskname) {
        sugguest_addtaskname = str_taskname;
        Log.e("Show Taskname", sugguest_addtaskname);
    }

    public static View getroot() {
        return getrootview;
    }

    public static String add() {
        return "add";
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);

        getActivity().setTitle("Add Reminder");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        str_token = prefs.getString("TOKEN", "null");
        Log.e("Reminder TOKEN", str_token);

        btnstartdate = (Button)view.findViewById(R.id.rm_start_date);
        btnenddate = (Button)view.findViewById(R.id.rm_end_date);

        btnstarttime = (Button)view.findViewById(R.id.rm_start_time);
        btnendtime = (Button)view.findViewById(R.id.rm_end_time);

        et_before_after_1 = (TextView) view.findViewById(R.id.rm_before_after_1);
        et_number_1 = (TextView) view.findViewById(R.id.rm_number_1);
        et_type_date_1 = (TextView)view.findViewById(R.id.rm_type_date_1);

        et_before_after_2 = (TextView) view.findViewById(R.id.rm_before_after_2);
        et_number_2 = (TextView) view.findViewById(R.id.rm_number_2);
        et_type_date_2 = (TextView)view.findViewById(R.id.rm_type_date_2);

        et_before_after_3 = (TextView) view.findViewById(R.id.rm_before_after_3);
        et_number_3 = (TextView) view.findViewById(R.id.rm_number_3);
        et_type_date_3 = (TextView)view.findViewById(R.id.rm_type_date_3);

        et_addtaskname = (TextView) view.findViewById(R.id.rm_add_task_name);
        et_addsubtaskname = (TextView) view.findViewById(R.id.rm_add_subtask_name);
        et_addplace = (EditText) view.findViewById(R.id.rm_add_place);

        onOffSwitch = (Switch) view.findViewById(R.id.rm_switch_allday);
        final boolean[] checkswitch = {false};

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.rm_marker_map);

        Button repeat = (Button)view.findViewById(R.id.rm_repeat);

        Calendar calendar = Calendar.getInstance();
        final int _year = calendar.get(Calendar.YEAR);
        final int _month = calendar.get(Calendar.MONTH);
        final int _day = calendar.get(Calendar.DAY_OF_MONTH);

        et_addtaskname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.sugguest_taskname, null);
                getrootview = rootview;

                listview = (ListView)rootview.findViewById(R.id.listview);
                mtaskname = new ArrayList<>();

                taskname_popup = (EditText)rootview.findViewById(R.id.sugguest_addtaskname);
                taskname_popup.setText(et_addtaskname.getText().toString());

                builder.setView(rootview);
                final AlertDialog dialog = builder.create();
                dialog.show();

                connection_taskname();

                Button btn_save = (Button) rootview.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str_taskname_popup = taskname_popup.getText().toString();
                        et_addtaskname.setText(str_taskname_popup);

                        dialog.cancel();
                        connection_taskname_notification(str_taskname_popup);
                    }
                });

                Button btn_close = (Button) rootview.findViewById(R.id.close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

        et_addsubtaskname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_get_taskname = (TextView)view.findViewById(R.id.rm_add_task_name);
                get_tasknamefromfragment = tv_get_taskname.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.sugguest_subtaskname, null);
                getrootview = rootview;

                listview = (ListView)rootview.findViewById(R.id.listview);
                msubtaskname = new ArrayList<>();

                subtaskname_popup = (EditText)rootview.findViewById(R.id.sugguest_addsubtaskname);
                subtaskname_popup.setText(et_addsubtaskname.getText().toString());

                builder.setView(rootview);
                final AlertDialog dialog = builder.create();
                dialog.show();

                connection_subtaskname();

                Button btn_save = (Button) rootview.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str_taskname_popup = taskname_popup.getText().toString();
                        String str_subtaskname_popup = subtaskname_popup.getText().toString();
                        et_addsubtaskname.setText(str_subtaskname_popup);
                        dialog.cancel();
                        connection_subtaskname_notification(str_taskname_popup, str_subtaskname_popup);
                    }
                });

                Button btn_close = (Button) rootview.findViewById(R.id.close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

        btnstartdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        int yyyy = year + 543;
                        btnstartdate.setText(dayOfMonth + "/" + month + "/" + yyyy );
                        con_str_startdate = String.valueOf(dayOfMonth);
                        con_str_startmonth = String.valueOf(month);
                        con_str_startyear = String.valueOf(year);
                    }
                }, _year, _month, _day);
                datePickerDialog.show();
            }
        });

        btnenddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        int yyyy = year + 543;
                        btnenddate.setText(dayOfMonth + "/" + month + "/" + yyyy );
                        con_str_enddate = String.valueOf(dayOfMonth);
                        con_str_endmonth = String.valueOf(month);
                        con_str_endyear = String.valueOf(year);
                    }
                }, _year, _month, _day);
                datePickerDialog.show();
            }
        });

        btnstarttime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int _hour = calendar.get(Calendar.HOUR);
                int _minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btnstarttime.setText(hourOfDay + ":" + minute);
                        con_str_starthour = String.valueOf(hourOfDay);
                        con_str_startmin = String.valueOf(minute);
                    }
                }, _hour, _minute, true);
                timePickerDialog.show();
            }
        });

        btnendtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int _hour = calendar.get(Calendar.HOUR);
                int _minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btnendtime.setText(hourOfDay + ":" + minute);
                        con_str_endhour = String.valueOf(hourOfDay);
                        con_str_endmin = String.valueOf(minute);
                    }
                }, _hour, _minute, true);
                timePickerDialog.show();
            }
        });

        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddReminderMapsReminderActivity.class);
                startActivity(intent);
            }
        });

        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.v("Switch State", ""+isChecked);
                if (isChecked == true) {
                    checkswitch[0] = true;
                    btnstarttime.setVisibility(View.GONE);
                    btnendtime.setVisibility(View.GONE);

                }
                if (isChecked == false) {
                    checkswitch[0] = false;
                    btnstarttime.setVisibility(View.VISIBLE);
                    btnendtime.setVisibility(View.VISIBLE);
                }
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View view = getLayoutInflater().inflate(R.layout.reminder_notification, null);

                spinner_before_after_1 = (Spinner)view.findViewById(R.id.spinner_event_notification_before_after_1);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_before_after));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_before_after_1.setAdapter(adapter);

                spinner_type_date_1 = (Spinner)view.findViewById(R.id.spinner_event_notification_type_date_1);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_reminder_notification_type_date));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_type_date_1.setAdapter(adapter);

                spinner_before_after_2 = (Spinner)view.findViewById(R.id.spinner_event_notification_before_after_2);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_before_after));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_before_after_2.setAdapter(adapter);

                spinner_type_date_2 = (Spinner)view.findViewById(R.id.spinner_event_notification_type_date_2);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_reminder_notification_type_date));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_type_date_2.setAdapter(adapter);

                spinner_before_after_3 = (Spinner)view.findViewById(R.id.spinner_event_notification_before_after_3);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_before_after));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_before_after_3.setAdapter(adapter);

                spinner_type_date_3 = (Spinner)view.findViewById(R.id.spinner_event_notification_type_date_3);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_reminder_notification_type_date));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_type_date_3.setAdapter(adapter);

                final EditText mNumber1 = (EditText)view.findViewById(R.id.edittext_number_1);
                final EditText mNumber2 = (EditText)view.findViewById(R.id.edittext_number_2);
                final EditText mNumber3 = (EditText)view.findViewById(R.id.edittext_number_3);

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button button_done = (Button)view.findViewById(R.id.done_notification);
                button_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        str_number_1 = mNumber1.getText().toString();
                        str_before_after_1 = spinner_before_after_1.getSelectedItem().toString();
                        str_type_date_1 = spinner_type_date_1.getSelectedItem().toString();

                        str_number_2 = mNumber2.getText().toString();
                        str_before_after_2 = spinner_before_after_2.getSelectedItem().toString();
                        str_type_date_2 = spinner_type_date_2.getSelectedItem().toString();

                        str_number_3 = mNumber3.getText().toString();
                        str_before_after_3 = spinner_before_after_3.getSelectedItem().toString();
                        str_type_date_3 = spinner_type_date_3.getSelectedItem().toString();

                        View view = inflater.inflate(R.layout.fragment_add_reminder, container, false);

                        if(str_number_1.equals("0")) {
                            et_before_after_1.setVisibility(View.INVISIBLE);
                            et_number_1.setVisibility(View.INVISIBLE);
                            et_type_date_1.setVisibility(View.INVISIBLE);
                            et_number_1.setText("0");
                        }else {
                            et_before_after_1.setVisibility(View.VISIBLE);
                            et_number_1.setVisibility(View.VISIBLE);
                            et_type_date_1.setVisibility(View.VISIBLE);
                            et_before_after_1.setText(str_before_after_1);
                            et_number_1.setText(str_number_1);
                            et_type_date_1.setText(str_type_date_1);
                        }

                        if(str_number_2.equals("0")) {
                            et_before_after_2.setVisibility(View.INVISIBLE);
                            et_number_2.setVisibility(View.INVISIBLE);
                            et_type_date_2.setVisibility(View.INVISIBLE);
                            et_number_2.setText("0");
                        }else {
                            et_before_after_2.setVisibility(View.VISIBLE);
                            et_number_2.setVisibility(View.VISIBLE);
                            et_type_date_2.setVisibility(View.VISIBLE);
                            et_before_after_2.setText(str_before_after_2);
                            et_number_2.setText(str_number_2);
                            et_type_date_2.setText(str_type_date_2);
                        }

                        if(str_number_3.equals("0")) {
                            et_before_after_3.setVisibility(View.INVISIBLE);
                            et_number_3.setVisibility(View.INVISIBLE);
                            et_type_date_3.setVisibility(View.INVISIBLE);
                            et_number_3.setText("0");
                        }else {
                            et_before_after_3.setVisibility(View.VISIBLE);
                            et_number_3.setVisibility(View.VISIBLE);
                            et_type_date_3.setVisibility(View.VISIBLE);
                            et_before_after_3.setText(str_before_after_3);
                            et_number_3.setText(str_number_3);
                            et_type_date_3.setText(str_type_date_3);
                        }

                        Log.d("AddReminderNoti_1", str_before_after_1 + str_number_1 + str_type_date_1);
                        Log.d("AddReminderNoti_2", str_before_after_2 + str_number_2 + str_type_date_2);
                        Log.d("AddReminderNoti_3", str_before_after_3 + str_number_3 + str_type_date_3);

                        dialog.cancel();
                    }
                });

                Button button_close = (Button)view.findViewById(R.id.back_notification);
                button_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

        FloatingActionButton fab_done_reminder = (FloatingActionButton)view.findViewById(R.id.fab_done);
        fab_done_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String con_str_taskname = et_addtaskname.getText().toString();
                String con_str_subtaskname = et_addsubtaskname.getText().toString();
                if(con_str_subtaskname.equals(null)) {
                    con_str_subtaskname = " ";
                }
                boolean bool_onoffswitch = onOffSwitch.isChecked();
                String con_str_onoffswitch;
                if(bool_onoffswitch) {
                    con_str_onoffswitch = "1";
                }else {
                    con_str_onoffswitch = "0";
                }
                String con_str_placename = et_addplace.getText().toString();

                connection_addreminder_reminder(con_str_taskname, con_str_subtaskname, con_str_onoffswitch, con_str_startdate, con_str_startmonth, con_str_startyear, con_str_starthour, con_str_startmin,
                        con_str_enddate, con_str_endmonth, con_str_endyear, con_str_endhour, con_str_endmin, con_str_placename, str_before_after_1, str_number_1, str_type_date_1,
                        str_before_after_2, str_number_2, str_type_date_2, str_before_after_3, str_number_3, str_type_date_3);
                ScheduledFragment scheduled_fragment = new ScheduledFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void connection_taskname() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = "http://161.246.5.195:3000/sugguestreminder/taskname";
        JSONObject jsonBody = new JSONObject();

        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            String msg_taskname = json.getString("msg");
                            Log.i("VOLLEY", msg_taskname);
                            if (msg_taskname.equals("suggestreminder/taskname : complete")) {
                                JSONArray data = json.getJSONArray("data");
                                Log.i("Data Taskname", String.valueOf(data));
                                for (int i=0; i < data.length(); i++) {
                                    JSONObject array = (JSONObject) data.get(i);
                                    String str_taskname = (String) array.get("type");
                                    int index = i+1;
                                    mtaskname.add(new Taskname(index, str_taskname.toLowerCase()));
                                    Log.e("Taskname Value", str_taskname);
                                }
                                mtasknameAdapter = new TasknameAdapter(getContext().getApplicationContext(), mtaskname, "AddReminderFragment");
                                listview.setAdapter(mtasknameAdapter);
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
    }

    private void connection_subtaskname() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/sugguestreminder/subtaskname";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("type", get_tasknamefromfragment);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_subtaskname = json.getString("msg");
                                Log.i("VOLLEY", msg_subtaskname);
                                if (msg_subtaskname.equals("suggestreminder/subtaskname : complete")) {
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Taskname", String.valueOf(data));
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject array = (JSONObject) data.get(i);
                                        String str_subtaskname = (String) array.get("item");
                                        int index = i + 1;
                                        msubtaskname.add(new Taskname(index, str_subtaskname.toLowerCase()));
                                        Log.e("Subtaskname Value", str_subtaskname);
                                    }
                                    msubtasknameAdapter = new SubtasknameAdapter(getContext().getApplicationContext(), msubtaskname, "AddReminderFragment");
                                    listview.setAdapter(msubtasknameAdapter);
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

    private void connection_taskname_notification(String string) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/sugguestreminder/tasknamenotification";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("taskname", string);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_taskname = json.getString("msg");
                                Log.i("VOLLEY", msg_taskname);
                                if (msg_taskname.equals("suggestreminder/tasknamenotification : complete")) {
                                    Log.e("Notification taskname", "dont have data");
                                    et_addsubtaskname.setText("");
                                    et_addsubtaskname.setHint("Add Subtask");
                                    onOffSwitch.setChecked(false);
                                    btnstartdate.setText("Start Date");
                                    btnstarttime.setText("Start Time");
                                    btnenddate.setText("End Date");
                                    btnendtime.setText("End Time");

                                    et_before_after_1.setVisibility(View.VISIBLE);
                                    et_number_1.setVisibility(View.VISIBLE);
                                    et_type_date_1.setVisibility(View.VISIBLE);
                                    et_before_after_1.setText("");
                                    et_number_1.setText("");
                                    et_type_date_1.setText("");

                                    et_before_after_2.setVisibility(View.VISIBLE);
                                    et_number_2.setVisibility(View.VISIBLE);
                                    et_type_date_2.setVisibility(View.VISIBLE);
                                    et_before_after_2.setText("");
                                    et_number_2.setText("");
                                    et_type_date_2.setText("");

                                    et_before_after_3.setVisibility(View.VISIBLE);
                                    et_number_3.setVisibility(View.VISIBLE);
                                    et_type_date_3.setVisibility(View.VISIBLE);
                                    et_before_after_3.setText("");
                                    et_number_3.setText("");
                                    et_type_date_3.setText("");
                                }
                                if (msg_taskname.equals("suggestreminder/tasknamenotification : add data complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");
                                    JSONObject array_notification = (JSONObject) json.getJSONObject("notification");
                                    et_addsubtaskname.setText("");
                                    et_addsubtaskname.setHint("Add Subtask");

                                    String suggest_onoffswitch = (String) array_output.get("allday");
                                    if(suggest_onoffswitch.equals("0")) {
                                        onOffSwitch.setChecked(false);
                                    }
                                    if(suggest_onoffswitch.equals("1")) {
                                        onOffSwitch.setChecked(true);
                                    }

                                    str_startdate = (String) array_output.get("startdate");
                                    String[] split_start = str_startdate.split("/");
                                    String split_startyear = split_start[2];
                                    String split_startmonth = split_start[1];
                                    String split_startdate = split_start[0];
                                    String str_startdate_show = split_startdate + "/" + split_startmonth + "/" + split_startyear;
                                    btnstartdate.setText(str_startdate_show);

                                    str_enddate = (String) array_output.get("enddate");
                                    String[] split_end = str_enddate.split("/");
                                    String split_endyear = split_end[2];
                                    String split_endmonth = split_end[1];
                                    String split_enddate = split_end[0];
                                    String str_enddate_show = split_enddate + "/" + split_endmonth + "/" + split_endyear;
                                    btnenddate.setText(str_enddate_show);

                                    String noti_1 = (String) array_notification.get("notification_1");
                                    if(noti_1 != null) {
                                        String[] split_noti1 = noti_1.split(" ");
                                        String before_after_1 = split_noti1[0];
                                        String number_1 = split_noti1[1];
                                        String type_date_1 = split_noti1[2];

                                        et_before_after_1.setVisibility(View.VISIBLE);
                                        et_number_1.setVisibility(View.VISIBLE);
                                        et_type_date_1.setVisibility(View.VISIBLE);
                                        et_before_after_1.setText(before_after_1);
                                        et_number_1.setText(number_1);
                                        et_type_date_1.setText(type_date_1);
                                        Log.e("Notification 1", str_before_after_1 + " " + str_number_1 + " " + str_type_date_1);
                                    }

                                    String noti_2 = (String) array_notification.get("notification_2");
                                    if(noti_2 != null) {
                                        String[] split_noti2 = noti_2.split(" ");
                                        String before_after_2 = split_noti2[0];
                                        String number_2 = split_noti2[1];
                                        String type_date_2 = split_noti2[2];

                                        et_before_after_2.setVisibility(View.VISIBLE);
                                        et_number_2.setVisibility(View.VISIBLE);
                                        et_type_date_2.setVisibility(View.VISIBLE);
                                        et_before_after_2.setText(before_after_2);
                                        et_number_2.setText(number_2);
                                        et_type_date_2.setText(type_date_2);
                                        Log.e("Notification 2", str_before_after_2 + " " + str_number_2 + " " + str_type_date_2);
                                    }
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

    private void connection_subtaskname_notification(String taskname, String subtaskname) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/sugguestreminder/subtasknamenotification";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("taskname", taskname);
            jsonBody.put("subtaskname", subtaskname);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_subtaskname = json.getString("msg");
                                Log.i("VOLLEY", msg_subtaskname);
                                if (msg_subtaskname.equals("suggestreminder/subtasknamenotification : complete")) {
                                    Log.e("Notification taskname", "dont have data");
                                    onOffSwitch.setChecked(false);
                                    btnstartdate.setText("Start Date");
                                    btnstarttime.setText("Start Time");
                                    btnenddate.setText("End Date");
                                    btnendtime.setText("End Time");

                                    et_before_after_1.setVisibility(View.VISIBLE);
                                    et_number_1.setVisibility(View.VISIBLE);
                                    et_type_date_1.setVisibility(View.VISIBLE);
                                    et_before_after_1.setText("");
                                    et_number_1.setText("");
                                    et_type_date_1.setText("");

                                    et_before_after_2.setVisibility(View.VISIBLE);
                                    et_number_2.setVisibility(View.VISIBLE);
                                    et_type_date_2.setVisibility(View.VISIBLE);
                                    et_before_after_2.setText("");
                                    et_number_2.setText("");
                                    et_type_date_2.setText("");

                                    et_before_after_3.setVisibility(View.VISIBLE);
                                    et_number_3.setVisibility(View.VISIBLE);
                                    et_type_date_3.setVisibility(View.VISIBLE);
                                    et_before_after_3.setText("");
                                    et_number_3.setText("");
                                    et_type_date_3.setText("");
                                }
                                if (msg_subtaskname.equals("suggestreminder/subtasknamenotification : add data complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");
                                    JSONObject array_notification = (JSONObject) json.getJSONObject("notification");

                                    String suggest_onoffswitch = (String) array_output.get("allday");
                                    if(suggest_onoffswitch.equals("0")) {
                                        onOffSwitch.setChecked(false);
                                    }
                                    if(suggest_onoffswitch.equals("1")) {
                                        onOffSwitch.setChecked(true);
                                    }

                                    str_startdate = (String) array_output.get("startdate");
                                    String[] split_start = str_startdate.split("/");
                                    String split_startyear = split_start[2];
                                    String split_startmonth = split_start[1];
                                    String split_startdate = split_start[0];
                                    String str_startdate_show = split_startdate + "/" + split_startmonth + "/" + split_startyear;
                                    btnstartdate.setText(str_startdate_show);

                                    str_enddate = (String) array_output.get("enddate");
                                    String[] split_end = str_enddate.split("/");
                                    String split_endyear = split_end[2];
                                    String split_endmonth = split_end[1];
                                    String split_enddate = split_end[0];
                                    String str_enddate_show = split_enddate + "/" + split_endmonth + "/" + split_endyear;
                                    btnenddate.setText(str_enddate_show);

                                    String noti_1 = (String) array_notification.get("notification_1");
                                    if(noti_1 != null) {
                                        String[] split_noti1 = noti_1.split(" ");
                                        String before_after_1 = split_noti1[0];
                                        String number_1 = split_noti1[1];
                                        String type_date_1 = split_noti1[2];

                                        et_before_after_1.setVisibility(View.VISIBLE);
                                        et_number_1.setVisibility(View.VISIBLE);
                                        et_type_date_1.setVisibility(View.VISIBLE);
                                        et_before_after_1.setText(before_after_1);
                                        et_number_1.setText(number_1);
                                        et_type_date_1.setText(type_date_1);
                                        Log.e("Notification 1", str_before_after_1 + " " + str_number_1 + " " + str_type_date_1);
                                    }

                                    String noti_2 = (String) array_notification.get("notification_2");
                                    if(noti_2 != null) {
                                        String[] split_noti2 = noti_2.split(" ");
                                        String before_after_2 = split_noti2[0];
                                        String number_2 = split_noti2[1];
                                        String type_date_2 = split_noti2[2];

                                        et_before_after_2.setVisibility(View.VISIBLE);
                                        et_number_2.setVisibility(View.VISIBLE);
                                        et_type_date_2.setVisibility(View.VISIBLE);
                                        et_before_after_2.setText(before_after_2);
                                        et_number_2.setText(number_2);
                                        et_type_date_2.setText(type_date_2);
                                        Log.e("Notification 2", str_before_after_2 + " " + str_number_2 + " " + str_type_date_2);
                                    }
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

    private void connection_addreminder_reminder(String con_str_taskname, String con_str_subtaskname, String con_str_onoffswitch, String con_str_startdate, String con_str_startmonth, String con_str_startyear, String con_str_starthour, String con_str_startmin, String con_str_enddate, String con_str_endmonth, String con_str_endyear, String con_str_endhour, String con_str_endmin, String con_str_placename, String str_before_after_1, String str_number_1, String str_type_date_1, String str_before_after_2, String str_number_2, String str_type_date_2, String str_before_after_3, String str_number_3, String str_type_date_3) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/addreminder/reminder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("token", str_token);
            jsonBody.put("type", "Reminder");
            jsonBody.put("allday", con_str_onoffswitch);

            jsonBody.put("startdate", con_str_startdate);
            jsonBody.put("startmonth", con_str_startmonth);
            jsonBody.put("startyear", con_str_startyear);
            jsonBody.put("starthour", con_str_starthour);
            jsonBody.put("startmin", con_str_startmin);

            jsonBody.put("enddate", con_str_enddate);
            jsonBody.put("endmonth", con_str_endmonth);
            jsonBody.put("endyear", con_str_endyear);
            jsonBody.put("endhour", con_str_endhour);
            jsonBody.put("endmin", con_str_endmin);

            jsonBody.put("placename", con_str_placename);
            jsonBody.put("taskname", con_str_taskname);
            jsonBody.put("subtaskname", con_str_subtaskname);
            jsonBody.put("complete", "0");

            jsonBody.put("before_after_1", str_before_after_1);
            jsonBody.put("num_notification_1", str_number_1);
            jsonBody.put("type_num_1", str_type_date_1);

            jsonBody.put("before_after_2", str_before_after_2);
            jsonBody.put("num_notification_2", str_number_2);
            jsonBody.put("type_num_2", str_type_date_2);

            jsonBody.put("before_after_3", str_before_after_3);
            jsonBody.put("num_notification_3", str_number_3);
            jsonBody.put("type_num_3", str_type_date_3);

            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_event = json.getString("msg");
                                Log.i("VOLLEY", msg_event);
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

    public static void putArguments(Bundle args) {
        String placename = args.getString("PlaceName");
        Log.d("Value place ", placename);
        et_addplace.setText(placename);
    }

}
