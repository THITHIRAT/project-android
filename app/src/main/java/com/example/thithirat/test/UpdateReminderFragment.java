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
import android.support.v4.app.FragmentActivity;
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
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpdateReminderFragment extends Fragment {

    private static View getrootview;

    static TextView et_addplace;
    private static String str_placename;

    DatePickerDialog datePickerDialog;

    ArrayAdapter<String> adapter;

    List<Taskname> mtaskname;
    TasknameAdapter mtasknameAdapter;

    List<Taskname> msubtaskname;
    SubtasknameAdapter msubtasknameAdapter;

    List<Notification> mnotification;
    NotificationAdapter mnotificationadapter;
    NonScrollListView lv_notification;

    ListView listview;

    Spinner spinner_before_after;
    EditText et_number;
    Spinner spinner_type_date;

    String str_token;

    String con_str_startdate = null;
    String con_str_startmonth = null;
    String con_str_startyear = null;

    String con_str_enddate = null;
    String con_str_endmonth = null;
    String con_str_endyear = null;

    String fn_con_str_enddate = null;
    String fn_con_str_endmonth = null;
    String fn_con_str_endyear = null;

    TextView et_addtaskname;
    TextView et_addsubtaskname;
    String str_taskname;
    String str_subtaskname;
    TextView sugguest_taskname;
    String sugguest_addtaskname;

    Button btnstartdate;
    Button btnenddate;
    String str_startdate;
    String str_enddate;

    TextView time_notification;

    EditText subtaskname_other;
    EditText taskname_other;

    String get_tasknamefromfragment;

    int reminder_id = 0;
    public UpdateReminderFragment(int id) {
        // Required empty public constructor
        this.reminder_id = id;
        Log.e("UpdateReminder", String.valueOf(id));
    }

    public UpdateReminderFragment(String str_taskname) {
        String taskname = str_taskname;
    }

    public static View getroot() {
        return getrootview;
    }

    public static String update() {
        return "update";
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_update_reminder, container, false);

        getActivity().setTitle("Edit Reminder");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        str_token = prefs.getString("TOKEN", "null");
        Log.e("Reminder TOKEN", str_token);

        btnstartdate = (Button)view.findViewById(R.id.rm_start_date);
        btnenddate = (Button)view.findViewById(R.id.rm_end_date);

        time_notification = (TextView) view.findViewById(R.id.rm_time);

        et_addtaskname = (TextView) view.findViewById(R.id.rm_add_task_name);
        et_addsubtaskname = (TextView) view.findViewById(R.id.rm_add_subtask_name);
        et_addplace = (TextView) view.findViewById(R.id.rm_add_place);

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.rm_marker_map);

        TextView repeat = (TextView) view.findViewById(R.id.rm_repeat);

        final Button add_notification = (Button) view.findViewById(R.id.add_notification);

        spinner_before_after = (Spinner)view.findViewById(R.id.spinner_before_after);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.types_event_notification_before_after));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_before_after.setAdapter(adapter);

        et_number = (EditText) view.findViewById(R.id.edittext_number);

        spinner_type_date = (Spinner)view.findViewById(R.id.spinner_type_date);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.types_reminder_notification_type_date));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_date.setAdapter(adapter);

        lv_notification = (NonScrollListView) view.findViewById(R.id.listview_notification);
        mnotification = new ArrayList<>();

        Button delete = (Button) view.findViewById(R.id.delete);
        Button update = (Button) view.findViewById(R.id.update);

        Calendar calendar = Calendar.getInstance();
        final int _year = calendar.get(Calendar.YEAR);
        final int _month = calendar.get(Calendar.MONTH);
        final int _day = calendar.get(Calendar.DAY_OF_MONTH);

        et_addtaskname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview_taskname = getLayoutInflater().inflate(R.layout.sugguest_taskname, null);
                getrootview = rootview_taskname;

                listview = (ListView) rootview_taskname.findViewById(R.id.listview);
                mtaskname = new ArrayList<>();

                taskname_other = (EditText) rootview_taskname.findViewById(R.id.other_taskname);

                builder.setView(rootview_taskname);
                final AlertDialog dialog_update = builder.create();
                dialog_update.show();

                connection_taskname();

                Button btn_save = (Button) rootview_taskname.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_addtaskname.setText(taskname_other.getText().toString());
                        dialog_update.cancel();
                        connection_taskname_notification(taskname_other.getText().toString());
                    }
                });

                Button btn_close = (Button) rootview_taskname.findViewById(R.id.close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_update.cancel();
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
                final View rootview_subtaskname = getLayoutInflater().inflate(R.layout.sugguest_subtaskname, null);
                getrootview = rootview_subtaskname;

                listview = (ListView)rootview_subtaskname.findViewById(R.id.listview);
                msubtaskname = new ArrayList<>();

                sugguest_taskname = (TextView) rootview_subtaskname.findViewById(R.id.sugguest_taskname);
                sugguest_taskname.setText(et_addtaskname.getText().toString());
                subtaskname_other = (EditText) rootview_subtaskname.findViewById(R.id.other_subtaskname);

                builder.setView(rootview_subtaskname);
                final AlertDialog dialog_update = builder.create();
                dialog_update.show();

                connection_subtaskname();

                Button btn_save = (Button) rootview_subtaskname.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_addsubtaskname.setText(subtaskname_other.getText().toString());
                        dialog_update.cancel();
                        connection_subtaskname_notification(sugguest_taskname.getText().toString(), subtaskname_other.getText().toString());
                    }
                });

                Button btn_close = (Button) rootview_subtaskname.findViewById(R.id.close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_update.cancel();
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
                        month = month + 1;
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

        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateReminderMapsActivity.class);
                startActivity(intent);
            }
        });

        time_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar time = Calendar.getInstance();
                int _hour = time.get(Calendar.HOUR);
                int _minute = time.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time_notification.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },_hour,_minute,true);
                timePickerDialog.show();
            }
        });

        add_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mnotificationadapter == null || mnotificationadapter.getCount() < 3 ) {
                    int index = 1;
                    String before_after = spinner_before_after.getSelectedItem().toString();
                    String number = et_number.getText().toString();
                    String type = spinner_type_date.getSelectedItem().toString();
                    mnotification.add(new Notification(index, before_after, number, type));
                    mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                    lv_notification.setAdapter(mnotificationadapter);
                }
            }
        });

        //show detail each reminder_id
        connection_show();

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
                        boolean check = connection_update(reminder_id);
                        if (check == true) {
                            ScheduledFragment scheduled_fragment = new ScheduledFragment();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            dialog_update.cancel();
                        }
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

    private boolean getenddatetime() {
        String enddate = (String) btnenddate.getText();
        String[] array_enddate = enddate.split("/");
        if(array_enddate.length == 1) {
            Toast.makeText(getActivity(), "Plase fill enddate", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            fn_con_str_enddate = array_enddate[0];
            fn_con_str_endmonth = array_enddate[1];
            int endyear = Integer.parseInt(array_enddate[2]) - 543;
            fn_con_str_endyear = String.valueOf(endyear);
        }

        return true;
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
                                mtasknameAdapter = new TasknameAdapter(getContext().getApplicationContext(), mtaskname, "UpdateReminderFragment");
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
                                    msubtasknameAdapter = new SubtasknameAdapter(getContext().getApplicationContext(), msubtaskname, "UpdateReminderFragment");
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
                                if(msg_taskname.equals("suggestreminder/tasknamenotification : no before_after complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");

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

                                }
                                if (msg_taskname.equals("suggestreminder/tasknamenotification : complete")) {
                                    Log.e("Notification taskname", "dont have data");
                                    et_addsubtaskname.setText("");
                                    et_addsubtaskname.setHint("Add Subtask");

                                    btnstartdate.setText("Start Date");
                                    btnenddate.setText("End Date");
                                }
                                if (msg_taskname.equals("suggestreminder/tasknamenotification : add data complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");
                                    JSONObject array_notification = (JSONObject) json.getJSONObject("notification");
                                    et_addsubtaskname.setText("");
                                    et_addsubtaskname.setHint("Add Subtask");

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
                                        mnotification.add(new Notification(0, before_after_1, number_1, type_date_1));
                                        mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                        lv_notification.setAdapter(mnotificationadapter);

                                    }

                                    String noti_2 = (String) array_notification.get("notification_2");
                                    if(noti_2 != null) {
                                        String[] split_noti2 = noti_2.split(" ");
                                        String before_after_2 = split_noti2[0];
                                        String number_2 = split_noti2[1];
                                        String type_date_2 = split_noti2[2];
                                        mnotification.add(new Notification(0, before_after_2, number_2, type_date_2));
                                        mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                        lv_notification.setAdapter(mnotificationadapter);

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
                                if(msg_subtaskname.equals("suggestreminder/subtasknamenotification : no before_after complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");

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

                                }
                                if (msg_subtaskname.equals("suggestreminder/subtasknamenotification : complete")) {
                                    btnstartdate.setText("Start Date");
                                    btnenddate.setText("End Date");

                                    btnstartdate.setText("Start Date");
                                    btnenddate.setText("End Date");
                                }
                                if (msg_subtaskname.equals("suggestreminder/subtasknamenotification : add data complete")) {
                                    JSONObject array_output = (JSONObject) json.getJSONObject("output");
                                    JSONObject array_notification = (JSONObject) json.getJSONObject("notification");

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
                                        mnotification.add(new Notification(0, before_after_1, number_1, type_date_1));
                                        mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                        lv_notification.setAdapter(mnotificationadapter);
                                    }

                                    String noti_2 = (String) array_notification.get("notification_2");
                                    if(noti_2 != null) {
                                        String[] split_noti2 = noti_2.split(" ");
                                        String before_after_2 = split_noti2[0];
                                        String number_2 = split_noti2[1];
                                        String type_date_2 = split_noti2[2];
                                        mnotification.add(new Notification(0, before_after_2, number_2, type_date_2));
                                        mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                        lv_notification.setAdapter(mnotificationadapter);
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
                        @SuppressLint("LongLogTag")
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

                                    str_startdate = (String) array.get("start_date");
                                    String[] split_start = str_startdate.split("-");
                                    if(split_start.length == 1) {
                                        btnstartdate.setText("Start Date");
                                    }else {
                                        String split_startyear = split_start[0];
                                        String split_startmonth = split_start[1];
                                        String split_startdate = split_start[2];
                                        String str_startdate_show = split_startdate + "/" + split_startmonth + "/" + split_startyear;
                                        btnstartdate.setText(str_startdate_show);
                                    }

                                    str_enddate = (String) array.get("end_date");
                                    String[] split_end = str_enddate.split("-");
                                    if(split_end.length == 1) {
                                        btnstartdate.setText("Start Date");
                                    }else {
                                        String split_endyear = split_end[0];
                                        String split_endmonth = split_end[1];
                                        String split_enddate = split_end[2];
                                        String str_enddate_show = split_enddate + "/" + split_endmonth + "/" + split_endyear;
                                        btnenddate.setText(str_enddate_show);
                                    }

                                    reminder_id = (int) array.get("_id");

                                    Log.e("Reminder Value", str_placename + " / " + str_taskname + " / " + str_subtaskname);

                                    JSONArray notification = json.getJSONArray("notification");
                                    Log.i("Data Notification", String.valueOf(notification));
                                    for (int i=0; i < notification.length(); i++) {
                                        JSONObject array_notification = (JSONObject) notification.get(i);
                                        if(array_notification.get("time").equals(null)) {

                                        }else {
                                            String time = (String) array_notification.get("time");
                                            String[] split_time = time.split(":");
                                            time_notification.setText(split_time[0] + ":" + split_time[1]);
                                        }
                                        if(i == 0) {
                                            String before_after_1 = (String) array_notification.get("before_after");
                                            String number_1 = String.valueOf(array_notification.get("number"));
                                            String type_date_1 = (String) array_notification.get("type");
                                            mnotification.add(new Notification(0, before_after_1, number_1, type_date_1));
                                            mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                            lv_notification.setAdapter(mnotificationadapter);
                                        }
                                        if(i == 1) {
                                            String before_after_2 = (String) array_notification.get("before_after");
                                            String number_2 = String.valueOf(array_notification.get("number"));;
                                            String type_date_2 = (String) array_notification.get("type");
                                            mnotification.add(new Notification(0, before_after_2, number_2, type_date_2));
                                            mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                            lv_notification.setAdapter(mnotificationadapter);
                                        }
                                        if(i == 2) {
                                            String before_after_3 = (String) array_notification.get("before_after");
                                            String number_3 = String.valueOf(array_notification.get("number"));;
                                            String type_date_3 = (String) array_notification.get("type");
                                            mnotification.add(new Notification(0, before_after_3, number_3, type_date_3));
                                            mnotificationadapter = new NotificationAdapter(getContext().getApplicationContext(), mnotification);
                                            lv_notification.setAdapter(mnotificationadapter);
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

    private boolean connection_update(int reminder_id) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String URL = "http://161.246.5.195:3000/updatereminder/task";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id", reminder_id);
            jsonBody.put("type", "Reminder");

            String[] split_startdate = btnstartdate.getText().toString().split("/");
            if(split_startdate.length == 1) {
                con_str_startdate = null;
                con_str_startmonth = null;
                con_str_startyear = null;
            }else {
                con_str_startdate = split_startdate[0];
                con_str_startmonth = split_startdate[1];
                int int_str_startyear = Integer.parseInt(split_startdate[2]) - 543;
                con_str_startyear = String.valueOf(int_str_startyear);
            }
            jsonBody.put("startdate", con_str_startdate);
            jsonBody.put("startmonth", con_str_startmonth);
            jsonBody.put("startyear", con_str_startyear);
            Log.e("Startdate" , con_str_startdate + "/" + con_str_startmonth + "/" + con_str_startyear);

            String[] split_enddate = btnenddate.getText().toString().split("/");
            if(split_enddate.length == 1) {
                con_str_enddate = null;
                con_str_endmonth = null;
                con_str_endyear = null;
            }else {
                con_str_enddate = split_enddate[0];
                con_str_endmonth = split_enddate[1];
                int int_str_endyear = Integer.parseInt(split_enddate[2]) - 543;
                con_str_endyear = String.valueOf(int_str_endyear);
            }
            Log.e("Enddate" , con_str_enddate + "/" + con_str_endmonth + "/" + con_str_endyear);
            jsonBody.put("enddate", con_str_enddate);
            jsonBody.put("endmonth", con_str_endmonth);
            jsonBody.put("endyear", con_str_endyear);

            jsonBody.put("time", time_notification.getText().toString());

            String get_con_str_placename = et_addplace.getText().toString();
            jsonBody.put("placename", get_con_str_placename);

            String get_taskname = et_addtaskname.getText().toString();
            jsonBody.put("taskname", get_taskname);

            String get_subtaskname = et_addsubtaskname.getText().toString();
            jsonBody.put("subtaskname", get_subtaskname);

            String before_after_1 = null, number_notification_1 = null, type_num_1 = null;
            String before_after_2 = null, number_notification_2 = null, type_num_2 = null;
            String before_after_3 = null, number_notification_3 = null, type_num_3 = null;
            if(mnotificationadapter != null) {
                int count = mnotificationadapter.getCount();
                if(count > 0) {
                    before_after_1 = mnotification.get(0).getBefore_after();
                    number_notification_1 = mnotification.get(0).getNumber();
                    type_num_1 = mnotification.get(0).getType();
                    Log.e("get data notification 1", "Count " + count + " : " + before_after_1 + number_notification_1 + type_num_1);
                    if(count > 1) {
                        before_after_2 = mnotification.get(1).getBefore_after();
                        number_notification_2 = mnotification.get(1).getNumber();
                        type_num_2 = mnotification.get(1).getType();
                        Log.e("get data notification 2", "Count " + count + " : " + before_after_2 + number_notification_2 + type_num_2);
                        if(count > 2) {
                            before_after_3 = mnotification.get(2).getBefore_after();
                            number_notification_3 = mnotification.get(2).getNumber();
                            type_num_3 = mnotification.get(2).getType();
                            Log.e("get data notification 3", "Count " + count + " : " + before_after_3 + number_notification_3 + type_num_3);
                        }else {
                            number_notification_3 = "0";
                        }
                    }else {
                        number_notification_2 = "0";
                    }
                }else {
                    number_notification_1 = "0";
                }
            }

            jsonBody.put("before_after_1", before_after_1);
            jsonBody.put("num_notification_1", number_notification_1);
            jsonBody.put("type_num_1", type_num_1);

            jsonBody.put("before_after_2", before_after_2);
            jsonBody.put("num_notification_2", number_notification_2);
            jsonBody.put("type_num_2", type_num_2);

            jsonBody.put("before_after_3", before_after_3);
            jsonBody.put("num_notification_3", number_notification_3);
            jsonBody.put("type_num_3", type_num_3);
            
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
        return true;
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
