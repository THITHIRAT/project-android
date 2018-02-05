package com.example.thithirat.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import static com.example.thithirat.test.TabScheduledFragment.int_items;

/**
 * Created by Thithirat on 1/26/2018.
 */

public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReminderScheduledFragment();
            case 1:
                return new EventScheduledFragment();
            case 2:
                return new LocationScheduledFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Reminder";
            case 1:
                return "Event";
            case 2:
                return "Location";
        }
        return null;
    }
}
