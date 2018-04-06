package com.example.thithirat.test;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpdateReminderFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    ArrayAdapter<String> adapter;

    static String str_placename;
    String str_taskname;
    String str_subtaskname;
    int int_onoffswitch;

    EditText et_addtaskname;
    EditText et_addsubtaskname;

    static EditText et_addplace;

    Button btnstartdate;
    Button btnenddate;
    String str_startdate;
    String str_enddate;

    Button btnstarttime;
    Button btnendtime;
    String str_starttime;
    String str_endtime;

    Spinner spinner_before_after_1;
    Spinner spinner_type_date_1;

    Spinner spinner_before_after_2;
    Spinner spinner_type_date_2;

    Spinner spinner_before_after_3;
    Spinner spinner_type_date_3;

    Switch onOffSwitch = null;

    String str_before_after_1;
    String str_number_1;
    String str_type_date_1;

    String str_before_after_2;
    String str_number_2;
    String str_type_date_2;

    String str_before_after_3;
    String str_number_3;
    String str_type_date_3;

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

    String con_str_onoffswitch;

    TextView et_before_after_1 = null;
    TextView et_number_1 = null;
    TextView et_type_date_1 = null;

    TextView et_before_after_2 = null;
    TextView et_number_2 = null;
    TextView et_type_date_2 = null;

    TextView et_before_after_3 = null;
    TextView et_number_3 = null;
    TextView et_type_date_3 = null;

    int reminder_id = 0;
    public UpdateReminderFragment(int id) {
        // Required empty public constructor
        this.reminder_id = id;
        Log.e("UpdateReminder", String.valueOf(id));
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_reminder, container, false);

        getActivity().setTitle("Edit Reminder");

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

        et_addtaskname = (EditText) view.findViewById(R.id.rm_add_task_name);
        et_addsubtaskname = (EditText) view.findViewById(R.id.rm_add_subtask_name);
        et_addplace = (EditText) view.findViewById(R.id.rm_add_place);

        onOffSwitch = (Switch) view.findViewById(R.id.rm_switch_allday);
        final boolean[] checkswitch = {false};

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.rm_marker_map);

        Button repeat = (Button)view.findViewById(R.id.rm_repeat);

        Calendar calendar = Calendar.getInstance();
        final int _year = calendar.get(Calendar.YEAR);
        final int _month = calendar.get(Calendar.MONTH);
        final int _day = calendar.get(Calendar.DAY_OF_MONTH);

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
                Intent intent = new Intent(getActivity(), UpdateReminderMapsActivity.class);
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

                    et_before_after_1.setVisibility(View.INVISIBLE);
                    et_number_1.setVisibility(View.INVISIBLE);
                    et_type_date_1.setVisibility(View.INVISIBLE);

                    et_before_after_2.setVisibility(View.INVISIBLE);
                    et_number_2.setVisibility(View.INVISIBLE);
                    et_type_date_2.setVisibility(View.INVISIBLE);

                    et_before_after_3.setVisibility(View.INVISIBLE);
                    et_number_3.setVisibility(View.INVISIBLE);
                    et_type_date_3.setVisibility(View.INVISIBLE);

                    et_number_1.setText("0");
                    et_number_2.setText("0");
                    et_number_3.setText("0");
                }
                if (isChecked == false) {
                    checkswitch[0] = false;
                    btnstarttime.setVisibility(View.VISIBLE);
                    btnendtime.setVisibility(View.VISIBLE);

                    et_before_after_1.setVisibility(View.INVISIBLE);
                    et_number_1.setVisibility(View.INVISIBLE);
                    et_type_date_1.setVisibility(View.INVISIBLE);

                    et_before_after_2.setVisibility(View.INVISIBLE);
                    et_number_2.setVisibility(View.INVISIBLE);
                    et_type_date_2.setVisibility(View.INVISIBLE);

                    et_before_after_3.setVisibility(View.INVISIBLE);
                    et_number_3.setVisibility(View.INVISIBLE);
                    et_type_date_3.setVisibility(View.INVISIBLE);

                    et_number_1.setText("0");
                    et_number_2.setText("0");
                    et_number_3.setText("0");
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

                        Log.d("UpdateReminderNoti_1", str_before_after_1 + str_number_1 + str_type_date_1);
                        Log.d("UpdateReminderNoti_2", str_before_after_2 + str_number_2 + str_type_date_2);
                        Log.d("UpdateReminderNoti_3", str_before_after_3 + str_number_3 + str_type_date_3);

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

        //show detail each reminder_id
        connection_show();

        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
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

        Button update = (Button) view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection_update(reminder_id);
                ScheduledFragment scheduled_fragment = new ScheduledFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void checkswitch() {
        boolean bool_onoffswitch = onOffSwitch.isChecked();
        if(bool_onoffswitch) {
            con_str_onoffswitch = "1";
        }else {
            con_str_onoffswitch = "0";
        }
    }

    private void getstartdatetime() {
        String startdate = (String) btnstartdate.getText();
        String[] array_startdate = startdate.split("/");
        con_str_startdate = array_startdate[0];
        con_str_startmonth = array_startdate[1];
        con_str_startyear = array_startdate[2];

        String starttime = (String) btnstarttime.getText();

        String[] array_starttime = starttime.split(":");
        if(array_starttime.length == 1) {
            con_str_starthour = "0";
            con_str_startmin = "0";
        }else {
            con_str_starthour = array_starttime[0];
            con_str_startmin = array_starttime[1];
        }
    }

    private void getenddatetime() {
        String enddate = (String) btnenddate.getText();
        String[] array_startdate = enddate.split("/");
        con_str_enddate = array_startdate[0];
        con_str_endmonth = array_startdate[1];
        con_str_endyear = array_startdate[2];

        String endtime = (String) btnendtime.getText();
        String[] array_endtime = endtime.split(":");
        if(array_endtime.length == 1) {
            con_str_endhour = "0";
            con_str_endmin = "0";
        }else {
            con_str_endhour = array_endtime[0];
            con_str_endmin = array_endtime[1];
        }
    }

    private void connection_show() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/detailreminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Reminder");
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            JSONObject json = null;
                            try {
                                json = new JSONObject(response);
                                String msg_reminder = json.getString("msg");
                                Log.i("VOLLEY", msg_reminder);
                                if (msg_reminder.equals("detailreminder/task : complete")){
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Reminder" , String.valueOf(data));
                                    JSONObject array = (JSONObject) data.get(0);
                                    str_placename = (String) array.get("placename");
                                    if (str_placename.equals("null")){
                                        //placename == null
                                        et_addplace.setHint("Place name");
                                    }else {
                                        et_addplace.setText(str_placename);
                                    }
                                    str_taskname = (String) array.get("taskname");
                                    et_addtaskname.setText(str_taskname);
                                    str_subtaskname = (String) array.get("subtaskname");
                                    et_addsubtaskname.setText(str_subtaskname);

                                    int_onoffswitch = (int)array.get("allday");
                                    if(int_onoffswitch == 0) {
                                        onOffSwitch.setChecked(false);
                                    }
                                    if(int_onoffswitch == 1) {
                                        onOffSwitch.setChecked(true);
                                    }

                                    str_startdate = (String) array.get("start_date");
                                    String[] split_start = str_startdate.split("-");
                                    String split_startyear = split_start[0];
                                    String split_startmonth = split_start[1];
                                    String split_startdate = split_start[2];
                                    String str_startdate_show = split_startdate + "/" + split_startmonth + "/" + split_startyear;
                                    btnstartdate.setText(str_startdate_show);

                                    str_enddate = (String) array.get("end_date");
                                    String[] split_end = str_enddate.split("-");
                                    String split_endyear = split_end[0];
                                    String split_endmonth = split_end[1];
                                    String split_enddate = split_end[2];
                                    String str_enddate_show = split_enddate + "/" + split_endmonth + "/" + split_endyear;
                                    btnenddate.setText(str_enddate_show);

                                    if(array.get("start_time").equals(null)){
                                        //allday
                                        btnstarttime.setText("00:00");
                                    }else {
                                        str_starttime = (String) array.get("start_time");
                                        btnstarttime.setText(str_starttime);
                                    }
                                    if(array.get("end_time").equals(null)) {
                                        //allday
                                        btnendtime.setText("00:00");
                                    }else {
                                        str_endtime = (String) array.get("end_time");
                                        btnendtime.setText(str_endtime);
                                    }

                                    reminder_id = (int) array.get("_id");

                                    Log.e("Reminder Value", str_placename + " / " + str_taskname + " / " + str_subtaskname);
                                }
                                if (msg_reminder.equals("detailreminder/task : complete")) {
                                    JSONArray notification = json.getJSONArray("notification");
                                    Log.i("Data Notification", String.valueOf(notification));
                                    for (int i=0; i < notification.length(); i++) {
                                        JSONObject array_notification = (JSONObject) notification.get(i);
                                        if(i == 0) {
                                            et_before_after_1.setVisibility(View.VISIBLE);
                                            et_number_1.setVisibility(View.VISIBLE);
                                            et_type_date_1.setVisibility(View.VISIBLE);
                                            str_before_after_1 = (String) array_notification.get("before_after");
                                            et_before_after_1.setText(str_before_after_1);
                                            str_number_1 = array_notification.get("number").toString();
                                            et_number_1.setText(str_number_1);
                                            str_type_date_1 = (String) array_notification.get("type");
                                            et_type_date_1.setText(str_type_date_1);
                                            Log.e("Notification 1", str_before_after_1 + " " + str_number_1 + " " + str_type_date_1);
                                        }
                                        if(i == 1) {
                                            et_before_after_2.setVisibility(View.VISIBLE);
                                            et_number_2.setVisibility(View.VISIBLE);
                                            et_type_date_2.setVisibility(View.VISIBLE);
                                            str_before_after_2 = (String) array_notification.get("before_after");
                                            et_before_after_2.setText(str_before_after_2);
                                            str_number_2 = array_notification.get("number").toString();
                                            et_number_2.setText(str_number_2);
                                            str_type_date_2 = (String) array_notification.get("type");
                                            et_type_date_2.setText(str_type_date_2);
                                            Log.e("Notification 2", str_before_after_2 + " " + str_number_2 + " " + str_type_date_2);
                                        }
                                        if(i == 2) {
                                            et_before_after_3.setVisibility(View.VISIBLE);
                                            et_number_3.setVisibility(View.VISIBLE);
                                            et_type_date_3.setVisibility(View.VISIBLE);
                                            str_before_after_3 = (String) array_notification.get("before_after");
                                            et_before_after_3.setText(str_before_after_3);
                                            str_number_3 = array_notification.get("number").toString();
                                            et_number_3.setText(str_number_3);
                                            str_type_date_3 = (String) array_notification.get("type");
                                            et_type_date_3.setText(str_type_date_3);
                                            Log.e("Notification 3", str_before_after_3 + " " + str_number_3 + " " + str_type_date_3);
                                        }
                                    }
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
            String URL = "http://161.246.5.195:3000/updatereminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Reminder");

            checkswitch();
            jsonBody.put("allday", con_str_onoffswitch);

            getstartdatetime();
            Log.e("Update Start", con_str_startdate + con_str_startmonth + con_str_startyear + " " + con_str_starthour + ":" + con_str_startmin);
            jsonBody.put("startdate", con_str_startdate);
            jsonBody.put("startmonth", con_str_startmonth);
            jsonBody.put("startyear", con_str_startyear);
            jsonBody.put("starthour", con_str_starthour);
            jsonBody.put("startmin", con_str_startmin);

            getenddatetime();
            Log.e("Update End", con_str_enddate + con_str_endmonth + con_str_endyear + " " + con_str_endhour + ":" + con_str_endmin);
            jsonBody.put("enddate", con_str_enddate);
            jsonBody.put("endmonth", con_str_endmonth);
            jsonBody.put("endyear", con_str_endyear);
            jsonBody.put("endhour", con_str_endhour);
            jsonBody.put("endmin", con_str_endmin);

            jsonBody.put("placename", str_placename);

            Log.e("Update Place", str_placename);

            String con_str_taskname = et_addtaskname.getText().toString();
            String con_str_subtaskname = et_addsubtaskname.getText().toString();
            jsonBody.put("taskname", con_str_taskname);
            jsonBody.put("subtaskname", con_str_subtaskname);

            jsonBody.put("before_after_1", str_before_after_1);
            jsonBody.put("num_notification_1", str_number_1);
            jsonBody.put("type_num_1", str_type_date_1);

            Log.e("Update Noti 1", str_before_after_1 + str_number_1 + str_type_date_1);

            jsonBody.put("before_after_2", str_before_after_2);
            jsonBody.put("num_notification_2", str_number_2);
            jsonBody.put("type_num_2", str_type_date_2);

            Log.e("Update Noti 2", str_before_after_2 + str_number_2 + str_type_date_2);

            jsonBody.put("before_after_3", str_before_after_3);
            jsonBody.put("num_notification_3", str_number_3);
            jsonBody.put("type_num_3", str_type_date_3);

            Log.e("Update Noti 3", str_before_after_3 + str_number_3 + str_type_date_3);

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

    private void connection_delete(int reminder_id) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/deletereminder/task";
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
        et_addplace.setText(placename);
        str_placename = args.getString("PlaceName");
    }
}
