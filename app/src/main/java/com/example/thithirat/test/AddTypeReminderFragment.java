package com.example.thithirat.test;


        import android.annotation.SuppressLint;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.view.ActionMode;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTypeReminderFragment extends Fragment {


    public Button button_reminder;
    public Button button_event;
    public Button button_location;

    int type;

    public AddTypeReminderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AddTypeReminderFragment(int type) {
        // Required empty public constructor
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_type_reminder, container, false);
        // Inflate the layout for this fragment
        getActivity().setTitle("Select Type");

//        AddTypeMainFragment main_add_fragment = new AddTypeMainFragment();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.layout_add_type, main_add_fragment, null);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        button_reminder = (Button)view.findViewById(R.id.reminder_button);
        button_event = (Button)view.findViewById(R.id.event_button);
        button_location = (Button)view.findViewById(R.id.location_button);

        button_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddReminderFragment reminder_fragment = new AddReminderFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, reminder_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        button_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventFragment event_fragment = new AddEventFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, event_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLocationFragment location_fragment = new AddLocationFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, location_fragment, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
