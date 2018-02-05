package com.example.thithirat.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderScheduledFragment extends Fragment {


    public ReminderScheduledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_reminder_scheduled, container, false);

        final ArrayList<ReminderScheduledList> reminderscheduled = new ArrayList<>();

        ReminderScheduledList r1 = new ReminderScheduledList("EventList");
        ReminderScheduledList r2 = new ReminderScheduledList("LocationList");

        reminderscheduled.add(r1);
        reminderscheduled.add(r2);

        BindDictionary<ReminderScheduledList> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.textname, new StringExtractor<ReminderScheduledList>() {
            @Override
            public String getStringValue(ReminderScheduledList reminder, int position) {
                return reminder.getName();
            }
        });

        FunDapter dapter = new FunDapter(ReminderScheduledFragment.this.getActivity(),reminderscheduled,R.layout.reminder_scheduled_layout,dictionary);
        ListView listView = (ListView)view.findViewById(R.id.reminder);

        listView.setAdapter(dapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReminderScheduledList selected = reminderscheduled.get(position);
                Toast.makeText(ReminderScheduledFragment.this.getActivity(),selected.getName(),Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
