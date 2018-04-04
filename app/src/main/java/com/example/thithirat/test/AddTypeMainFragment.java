package com.example.thithirat.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTypeMainFragment extends Fragment {


    public AddTypeMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_type_main, container, false);

        Button button_reminder = (Button)view.findViewById(R.id.reminder_button);
        Button button_event = (Button)view.findViewById(R.id.event_button);
        Button button_location = (Button)view.findViewById(R.id.location_button);

        button_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder();
            }
        });

        button_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });

        return view;
    }

    private void location() {
        AddLocationFragment location_fragment = new AddLocationFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_add_type, location_fragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void event() {
        AddEventFragment event_fragment = new AddEventFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_add_type, event_fragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void reminder() {
        AddReminderFragment reminder_fragment = new AddReminderFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_add_type, reminder_fragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
