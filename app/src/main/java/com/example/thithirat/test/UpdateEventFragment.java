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
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpdateEventFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Spinner spinner_before_after;
    Spinner spinner_min_hour;
    ArrayAdapter<String> adapter;

    static String str_placename;
    String str_taskname;
    String str_subtaskname;
    int int_onoffswitch;

    Switch onOffSwitch;

    Button btnstartdate;
    Button btnenddate;
    String str_startdate;
    String str_enddate;

    Button btnstarttime;
    Button btnendtime;
    String str_starttime;
    String str_endtime;

    String str_before_after;
    String str_number;
    String str_type_date;

    static EditText et_addplace;
    static EditText et_addtaskname;

    String str_token;

    String con_str_onoffswitch;

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

    String con_str_alldaydate = null;
    String con_str_alldaymonth = null;
    String con_str_alldayyear = null;

    String con_str_alldayhour = null;
    String con_str_alldaymin = null;

    TextView et_before_after;
    TextView et_number;
    TextView et_type_date;

    int reminder_id = 0;
    @SuppressLint("ValidFragment")
    public UpdateEventFragment(int id) {
        // Required empty public constructor
        this.reminder_id = id;
        Log.e("UpdateEvent", String.valueOf(id));
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_event, container, false);

        getActivity().setTitle("Edit Event");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        str_token = prefs.getString("TOKEN", "null");
        Log.e("Event TOKEN", str_token);

        btnstartdate = (Button)view.findViewById(R.id.start_date);
        btnenddate = (Button)view.findViewById(R.id.end_date);

        btnstarttime = (Button)view.findViewById(R.id.start_time);
        btnendtime = (Button)view.findViewById(R.id.end_time);

        et_before_after = (TextView) view.findViewById(R.id.before_after);
        et_number = (TextView) view.findViewById(R.id.number);
        et_type_date = (TextView)view.findViewById(R.id.type_date);

        et_addtaskname = (EditText) view.findViewById(R.id.add_task_name);
        et_addplace = (EditText) view.findViewById(R.id.add_place);

        onOffSwitch = (Switch) view.findViewById(R.id.switch_allday);
        final boolean[] checkswitch = {false};

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.marker_map);

        Button notification = (Button)view.findViewById(R.id.type_notification);

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
                        btnstartdate.setText(String.format("%02d/%02d/%04d", dayOfMonth, month, yyyy));
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
                        month = _month + 1;
                        int yyyy = year + 543;
                        btnenddate.setText(String.format("%02d/%02d/%04d", dayOfMonth, month, yyyy));
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
                        btnstarttime.setText(String.format("%02d:%02d", hourOfDay, minute));
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
                        btnendtime.setText(String.format("%02d:%02d", hourOfDay, minute));
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
                Intent intent = new Intent(getActivity(), UpdateEventMapsActivity.class);
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
                    et_before_after.setText(" ");
                    et_number.setText(" ");
                    et_type_date.setText(" ");
                }
                if (isChecked == false) {
                    checkswitch[0] = false;
                    btnstarttime.setVisibility(View.VISIBLE);
                    btnendtime.setVisibility(View.VISIBLE);
                    et_before_after.setText(" ");
                    et_number.setText(" ");
                    et_type_date.setText(" ");
                }
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(checkswitch[0] == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final View view = getLayoutInflater().inflate(R.layout.event_notification_allday, null);

                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    final Button button_date_allday = (Button) view.findViewById(R.id.button_date);
                    button_date_allday.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            int _yaer = calendar.get(Calendar.YEAR);
                            final int _month = calendar.get(Calendar.MONTH);
                            int _day = calendar.get(Calendar.DAY_OF_MONTH);

                            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    month = _month + 1;
                                    int yyyy = year + 543;
                                    button_date_allday.setText(String.format("%02d/%02d/%04d", dayOfMonth, month, yyyy));
                                    con_str_alldaydate = String.valueOf(dayOfMonth);
                                    con_str_alldaymonth = String.valueOf(month);
                                    con_str_alldayyear = String.valueOf(year);
                                }
                            }, _yaer, _month, _day);
                            datePickerDialog.show();
                        }
                    });

                    final Button button_time_allday = (Button) view.findViewById(R.id.button_time);
                    button_time_allday.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            int _hour = calendar.get(Calendar.HOUR);
                            int _minute = calendar.get(Calendar.MINUTE);

                            timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    button_time_allday.setText(String.format("%02d:%02d", hourOfDay, minute));
                                    con_str_alldayhour = String.valueOf(hourOfDay);
                                    con_str_alldaymin = String.valueOf(minute);
                                }
                            }, _hour, _minute, true);
                            timePickerDialog.show();
                        }
                    });

                    Button button_done = (Button)view.findViewById(R.id.done_notification);
                    button_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_number = button_date_allday.getText().toString();
                            str_before_after = " ";
                            str_type_date = button_time_allday.getText().toString();

                            View view = inflater.inflate(R.layout.fragment_update_event, container, false);

                            et_before_after.setText(str_before_after);
                            et_number.setText(str_number);
                            et_type_date.setText(str_type_date);

                            Log.d("UpdateEventNotification", str_before_after + str_number + str_type_date);

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
                if(checkswitch[0] == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final View view = getLayoutInflater().inflate(R.layout.event_notification, null);
                    spinner_before_after = (Spinner)view.findViewById(R.id.spinner_event_notification_before_after);
                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_before_after));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_before_after.setAdapter(adapter);

                    spinner_min_hour = (Spinner)view.findViewById(R.id.spinner_event_notification_min_hour);
                    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_min_hour));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_min_hour.setAdapter(adapter);

                    final EditText mNumber = (EditText)view.findViewById(R.id.edittext_number);

                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    Button button_done = (Button)view.findViewById(R.id.done_notification);
                    button_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            str_number = mNumber.getText().toString();
                            str_before_after = spinner_before_after.getSelectedItem().toString();
                            str_type_date = spinner_min_hour.getSelectedItem().toString();

                            View view = inflater.inflate(R.layout.fragment_update_event, container, false);

                            et_before_after.setText(str_before_after);
                            et_number.setText(str_number);
                            et_type_date.setText(str_type_date);

                            Log.d("UpdateEventNotification", str_before_after + str_number + str_type_date);

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
            }
        });

        //show detail each reminder_id
        connection_show();

        Button delete =  (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        dialog_delete.cancel();
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
        int int_startyear = Integer.parseInt(array_startdate[2]) - 543;
        con_str_startyear = String.valueOf(int_startyear);

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
        int int_endyear = Integer.parseInt(array_startdate[2]) - 543;
        con_str_endyear = String.valueOf(int_endyear);

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

    private void getallday() {
        String date = (String) et_number.getText();
        String[] array_d = date.split("/");
        con_str_alldaydate = array_d[0];
        con_str_alldaymonth = array_d[1];
        int allday_year = Integer.parseInt(array_d[2]) - 543;
        con_str_alldayyear = String.valueOf(allday_year);

        String time = (String) et_type_date.getText();
        String[] array_t = time.split(":");
        con_str_alldayhour = array_t[0];
        con_str_alldaymin = array_t[1];
    }

    private void connection_show() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = ConnectAPI.getUrl() + "detailreminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Event");
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
                                if (msg_event.equals("detailreminder/task : complete")){
                                    JSONArray data = json.getJSONArray("data");
                                    Log.i("Data Event" , String.valueOf(data));
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
                                    }else {
                                        str_starttime = (String) array.get("start_time");
                                        String[] split_starttime = str_starttime.split(":");
                                        btnstarttime.setText(split_starttime[0] + ":" + split_starttime[1]);
                                    }
                                    if(array.get("end_time").equals(null)) {
                                        //allday
                                    }else {
                                        str_endtime = (String) array.get("end_time");
                                        String[] split_endtime = str_endtime.split(":");
                                        btnendtime.setText(split_endtime[0] + ":" + split_endtime[1]);
                                    }

                                    reminder_id = (int) array.get("_id");

                                    Log.e("Event Value", str_placename + " / " + str_taskname);
                                }
                                if (msg_event.equals("detailreminder/task : complete")) {
                                    JSONArray notification = json.getJSONArray("notification");
                                    Log.i("Data Notification", String.valueOf(notification));
                                    for (int i=0; i < notification.length(); i++) {
                                        JSONObject array_notification = (JSONObject) notification.get(i);
                                        if(int_onoffswitch == 0) {
                                            et_before_after.setVisibility(View.VISIBLE);
                                            et_number.setVisibility(View.VISIBLE);
                                            et_type_date.setVisibility(View.VISIBLE);
                                            str_before_after = (String) array_notification.get("before_after");
                                            et_before_after.setText(str_before_after);
                                            str_number = array_notification.get("number").toString();
                                            et_number.setText(str_number);
                                            str_type_date = (String) array_notification.get("type");
                                            et_type_date.setText(str_type_date);
                                            Log.e("Notification allday 0", str_before_after + " " + str_number + " " + str_type_date);
                                        }else {
                                            et_number.setVisibility(View.VISIBLE);
                                            et_type_date.setVisibility(View.VISIBLE);
                                            str_number = (String) array_notification.get("date");
                                            String[] ddmmyyyy = str_number.split("-");
                                            String yyyy = ddmmyyyy[0];
                                            String mm = ddmmyyyy[1];
                                            String dd = ddmmyyyy[2];
                                            String date = dd + "/" + mm + "/" + yyyy;
                                            et_number.setText(date);
                                            str_type_date = (String) array_notification.get("time");
                                            String[] date_time = str_type_date.split(":");
                                            String hr = date_time[0];
                                            String min = date_time[1];
                                            String time = hr + ":" + min;
                                            et_type_date.setText(time);
                                            Log.e("Notification allday 1", str_before_after + " " + date + " " + time);
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
            String URL = ConnectAPI.getUrl() + "updatereminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Event");

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
            jsonBody.put("taskname", con_str_taskname);

            if(con_str_onoffswitch == "0") {
                jsonBody.put("before_after_1", str_before_after);
                jsonBody.put("num_notification_1", str_number);
                jsonBody.put("type_num_1", str_type_date);
            }

            if(con_str_onoffswitch == "1") {
                if(et_number.getText().toString() == "") {
                    Log.e("All Day", et_number.getText().toString() + " : empty text");
                }else {
                    getallday();
                    jsonBody.put("allday_date", con_str_alldaydate);
                    jsonBody.put("allday_month", con_str_alldaymonth);
                    jsonBody.put("allday_year", con_str_alldayyear);
                    jsonBody.put("allday_hrs", con_str_alldayhour);
                    jsonBody.put("allday_mins", con_str_alldaymin);
                }
            }

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
        et_addplace.setText(placename);
        str_placename = args.getString("PlaceName");
    }

}
