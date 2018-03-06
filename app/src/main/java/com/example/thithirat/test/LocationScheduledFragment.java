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


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationScheduledFragment extends Fragment {

    ListView listview;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    static String strplace;

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
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, arrayList);
        listview.setAdapter(adapter);

        String name = strplace;
        arrayList.add(name);
        adapter.notifyDataSetChanged();

        return view;
    }

    public static void putArguments(Bundle args) {
        String placename = args.getString("PlaceName");
        Log.d("Value place ", placename);

        strplace = args.getString("PlaceName");
    }
}
