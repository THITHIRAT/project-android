package com.example.thithirat.test;


import android.content.Intent;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationScheduledFragment extends Fragment {

    ListView listview;

    List<LocationReminder> mLocation;
    LocationReminderAdapter locationadapter;

    static String strplace;
    static String strnoti;
    static String strtask;

    public LocationScheduledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_scheduled, container, false);

        Button button_map = (Button)view.findViewById(R.id.button_map);

        button_map.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
            }
        });

        listview = (ListView)view.findViewById(R.id.list_view);

        mLocation = new ArrayList<>();
        String name = strplace;
        String noti = strnoti;
        String task = strtask;
        mLocation.add(new LocationReminder(1, name, noti, task));
        mLocation.add(new LocationReminder(2, name, noti, task));
        locationadapter = new LocationReminderAdapter(view.getContext().getApplicationContext(), mLocation);
        listview.setAdapter(locationadapter);

        return view;
    }

}
