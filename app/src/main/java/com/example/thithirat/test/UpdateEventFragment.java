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

    String str_before_after;
    String str_number;
    String str_type_date;

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

    String con_str_alldaydate = null;
    String con_str_alldaymonth = null;
    String con_str_alldayyear = null;

    String con_str_alldayhour = null;
    String con_str_alldaymin = null;

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

        final Button btnstartdate = (Button)view.findViewById(R.id.start_date);
        final Button btnenddate = (Button)view.findViewById(R.id.end_date);

        final Button btnstarttime = (Button)view.findViewById(R.id.start_time);
        final Button btnendtime = (Button)view.findViewById(R.id.end_time);

        final TextView et_before_after = (TextView) view.findViewById(R.id.before_after);
        final TextView et_number = (TextView) view.findViewById(R.id.number);
        final TextView et_type_date = (TextView)view.findViewById(R.id.type_date);

        final EditText et_addtaskname = (EditText) view.findViewById(R.id.add_task_name);
        et_addplace = (EditText) view.findViewById(R.id.add_place);

        final Switch onOffSwitch = (Switch) view.findViewById(R.id.switch_allday);
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
                        month = _month + 1;
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
                                    button_date_allday.setText(dayOfMonth + "/" + month + "/" + year );
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
                                    button_time_allday.setText(hourOfDay + ":" + minute);
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

        Button delete =  (Button) view.findViewById(R.id.delete);
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

    private void connection_update(int reminder_id) {
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

}
