package com.example.thithirat.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


public class UpdateLocationFragment extends Fragment {

    Spinner spinner;
    ArrayAdapter<String> adapter;

    static EditText editstrplacename;
    static EditText editstrtaskname;

    public UpdateLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_location, container, false);

        getActivity().setTitle("Edit Location");

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        final String str_token = prefs.getString("TOKEN", "null");
        Log.e("Update Location TOKEN", str_token);

        spinner = (Spinner)view.findViewById(R.id.spinner_location);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types_location_notification));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editstrplacename = (EditText)view.findViewById(R.id.add_place);
        editstrtaskname = (EditText)view.findViewById(R.id.add_name_task);

        ImageButton marker_maps = (ImageButton)view.findViewById(R.id.marker_map);
        marker_maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateLocationMapsActivity.class);
                startActivity(intent);
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
