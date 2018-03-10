package com.example.thithirat.test;


import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.fields.StringField;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ResourceBundle;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLocationFragment extends Fragment {

    Spinner spinner;
    ArrayAdapter<String> adapter;
    static EditText editstrplacename;
    static EditText editstrtaskname;

    static String strplace;
    static String strnoti;
    static String strtask;

    public AddLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_location, container, false);

        spinner = (Spinner)view.findViewById(R.id.spinner_location);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_location_notification));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editstrplacename = (EditText)view.findViewById(R.id.add_place);
        editstrtaskname = (EditText)view.findViewById(R.id.add_name_task);

        FloatingActionButton fab_done_reminder = (FloatingActionButton)view.findViewById(R.id.fab_done);
        fab_done_reminder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                strnoti = spinner.getSelectedItem().toString();
                strtask = editstrtaskname.getText().toString();

                Log.d("Add Location", "Hello" + strtask);

                Bundle args = new Bundle();
                args.putString("PlaceName", strplace);
                args.putString("Noti", strnoti);
                args.putString("Task", strtask);

                Log.d("AddLocation", strplace + strnoti + strtask);
                LocationScheduledFragment.putArguments(args);

                ScheduledFragment scheduled_fragment = new ScheduledFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, scheduled_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.marker_map);
        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLocationMapsReminderActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public static void putArguments(Bundle args) {
        String placename = args.getString("PlaceName");
        Log.d("Value place ", placename);
        editstrplacename.setText(placename);

        strplace = args.getString("PlaceName");
    }
}
