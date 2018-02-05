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


/**
 * A simple {@link Fragment} subclass.
 */
public class EventScheduledFragment extends Fragment {


    public EventScheduledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_scheduled, container, false);

        final ArrayList<EventScheduledList> eventscheduled = new ArrayList<>();

        EventScheduledList e1 = new EventScheduledList("ReminderList");
        EventScheduledList e2 = new EventScheduledList("LocationList");

        eventscheduled.add(e1);
        eventscheduled.add(e2);

        BindDictionary<EventScheduledList> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.textname, new StringExtractor<EventScheduledList>() {
            @Override
            public String getStringValue(EventScheduledList event, int position) {
                return event.getName();
            }

        });

        FunDapter dapter = new FunDapter(EventScheduledFragment.this.getActivity(),eventscheduled,R.layout.event_scheduled_layout,dictionary);
        ListView listView = (ListView)view.findViewById(R.id.event);

        listView.setAdapter(dapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventScheduledList selected = eventscheduled.get(position);
                Toast.makeText(EventScheduledFragment.this.getActivity(),selected.getName(),Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
