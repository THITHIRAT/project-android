package com.example.thithirat.test;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateReminderFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    ArrayAdapter<String> adapter;

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


    public UpdateReminderFragment() {
        // Required empty public constructor
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

        final Button btnstartdate = (Button)view.findViewById(R.id.rm_start_date);
        final Button btnenddate = (Button)view.findViewById(R.id.rm_end_date);

        final Button btnstarttime = (Button)view.findViewById(R.id.rm_start_time);
        final Button btnendtime = (Button)view.findViewById(R.id.rm_end_time);

        final TextView et_before_after_1 = (TextView) view.findViewById(R.id.rm_before_after_1);
        final TextView et_number_1 = (TextView) view.findViewById(R.id.rm_number_1);
        final TextView et_type_date_1 = (TextView)view.findViewById(R.id.rm_type_date_1);

        final TextView et_before_after_2 = (TextView) view.findViewById(R.id.rm_before_after_2);
        final TextView et_number_2 = (TextView) view.findViewById(R.id.rm_number_2);
        final TextView et_type_date_2 = (TextView)view.findViewById(R.id.rm_type_date_2);

        final TextView et_before_after_3 = (TextView) view.findViewById(R.id.rm_before_after_3);
        final TextView et_number_3 = (TextView) view.findViewById(R.id.rm_number_3);
        final TextView et_type_date_3 = (TextView)view.findViewById(R.id.rm_type_date_3);

        final EditText et_addtaskname = (EditText) view.findViewById(R.id.rm_add_task_name);
        final EditText et_addsubtaskname = (EditText) view.findViewById(R.id.rm_add_subtask_name);
        et_addplace = (EditText) view.findViewById(R.id.rm_add_place);

        final Switch onOffSwitch = (Switch) view.findViewById(R.id.rm_switch_allday);
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

                        View view = inflater.inflate(R.layout.fragment_update_reminder, container, false);

                        if(str_number_1.equals("0")) {
                            et_before_after_1.setVisibility(View.INVISIBLE);
                            et_number_1.setVisibility(View.INVISIBLE);
                            et_type_date_1.setVisibility(View.INVISIBLE);
                            et_number_1.setText("0");
                        }else {
                            et_type_date_1.setVisibility(View.VISIBLE);
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
                            et_before_after_2.setVisibility(View.VISIBLE);
                            et_number_2.setVisibility(View.VISIBLE);
                            et_type_date_2.setVisibility(View.VISIBLE);
                            et_before_after_3.setText(str_before_after_3);
                            et_number_3.setText(str_number_3);
                            et_type_date_3.setText(str_type_date_3);
                        }

                        Log.d("Up_EventNotification 1", str_before_after_1 + str_number_1 + str_type_date_1);
                        Log.d("Up_EventNotification 2", str_before_after_2 + str_number_2 + str_type_date_2);
                        Log.d("Up_EventNotification 3", str_before_after_3 + str_number_3 + str_type_date_3);

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

        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button update = (Button) view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
