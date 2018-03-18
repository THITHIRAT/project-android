package com.example.thithirat.test;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import java.util.Calendar;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Spinner spinner_before_after;
    Spinner spinner_min_hour;
    ArrayAdapter<String> adapter;

    String str_before_after;
    String str_number;
    String str_type_date;

    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        final Button btnstartdate = (Button)view.findViewById(R.id.start_date);
        final Button btnenddate = (Button)view.findViewById(R.id.end_date);

        final Button btnstarttime = (Button)view.findViewById(R.id.start_time);
        final Button btnendtime = (Button)view.findViewById(R.id.end_time);

        final TextView et_before_after = (TextView) view.findViewById(R.id.before_after);
        final TextView et_number = (TextView) view.findViewById(R.id.number);
        final TextView et_type_date = (TextView)view.findViewById(R.id.type_date);

        Switch onOffSwitch = (Switch) view.findViewById(R.id.switch_allday);

        btnstartdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               int _yaer = calendar.get(Calendar.YEAR);
               int _month = calendar.get(Calendar.MONTH);
               int _day = calendar.get(Calendar.DAY_OF_MONTH);

               datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       btnstartdate.setText(dayOfMonth + "/" + month + "/" + year );
                   }
               }, _yaer, _month, _day);
               datePickerDialog.show();
            }
        });

        btnenddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int _yaer = calendar.get(Calendar.YEAR);
                int _month = calendar.get(Calendar.MONTH);
                int _day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnenddate.setText(dayOfMonth + "/" + month + "/" + year );
                    }
                }, _yaer, _month, _day);
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
                    }
                }, _hour, _minute, true);
                timePickerDialog.show();
            }
        });

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.marker_map);
        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventMapsReminderActivity.class);
                startActivity(intent);
            }
        });

        Button notification = (Button)view.findViewById(R.id.type_event_notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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

                        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

                        et_before_after.setText(str_before_after);
                        et_number.setText(str_number);
                        et_type_date.setText(str_type_date);

                        Log.d("AddEventNotification", str_before_after + str_number + str_type_date);

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

        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State", ""+isChecked);
                if (isChecked == true) {
                    btnstarttime.setVisibility(View.GONE);
                    btnendtime.setVisibility(View.GONE);
                }
                if (isChecked == false) {
                    btnstarttime.setVisibility(View.VISIBLE);
                    btnendtime.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

}
