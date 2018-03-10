package com.example.thithirat.test;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventFragment extends Fragment {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Spinner spinner_before_after;
    Spinner spinner_min_hour;
    ArrayAdapter<String> adapter;

    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        Button btndate = (Button)view.findViewById(R.id.show_date);
        Button btntime = (Button)view.findViewById(R.id.show_time);

        btndate.setOnClickListener(new View.OnClickListener() {

            TextView date = (TextView) view.findViewById(R.id.add_date);

            @Override
            public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               int _yaer = calendar.get(Calendar.YEAR);
               int _month = calendar.get(Calendar.MONTH);
               int _day = calendar.get(Calendar.DAY_OF_MONTH);

               datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       date.setText(dayOfMonth + "/" + month + "/" + year );
                   }
               }, _yaer, _month, _day);
               datePickerDialog.show();
            }
        });

        btntime.setOnClickListener(new View.OnClickListener() {

            TextView time = (TextView) view.findViewById(R.id.add_time);

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int _hour = calendar.get(Calendar.HOUR);
                int _minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
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
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.event_notification, null);
                spinner_before_after = (Spinner)view.findViewById(R.id.spinner_event_notification_before_after);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_before_after));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_before_after.setAdapter(adapter);

                spinner_min_hour = (Spinner)view.findViewById(R.id.spinner_event_notification_min_hour);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_event_notification_min_hour));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_min_hour.setAdapter(adapter);

                EditText mNumber = (EditText)view.findViewById(R.id.edittext_number);

                Button button_done = (Button)view.findViewById(R.id.done_notification);
                button_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(),
                                    R.string.sucesss_login_msg,
                                    Toast.LENGTH_LONG).show();
                    }
                });

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

}
