package com.example.thithirat.test;


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


    public AddTypeReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_type_reminder, container, false);
        // Inflate the layout for this fragment
        getActivity().setTitle("Add Reminder");

        AddTypeMainFragment main_add_fragment = new AddTypeMainFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout_add_type, main_add_fragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return view;
    }
}
