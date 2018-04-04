package com.example.thithirat.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.database.sqlite.SQLiteDatabase.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduledFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    public FloatingActionButton fab_add_reminder;


    public ScheduledFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        //preference
        String token_name = "PUTGET_TOKEN";
        SharedPreferences prefs = getActivity().getSharedPreferences(token_name, Context.MODE_PRIVATE);
        final String str_token = prefs.getString("TOKEN", "null");
        Log.e("Scheduled TOKEN", str_token);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        fab_add_reminder = (FloatingActionButton)view.findViewById(R.id.fab_add);
        fab_add_reminder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AddTypeReminderFragment add_fragment = new AddTypeReminderFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frag, add_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
