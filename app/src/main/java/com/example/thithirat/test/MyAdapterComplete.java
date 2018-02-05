package com.example.thithirat.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.thithirat.test.TabScheduledFragment.int_items;

/**
 * Created by Thithirat on 2/5/2018.
 */

public class MyAdapterComplete extends FragmentPagerAdapter {

    public MyAdapterComplete(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReminderCompleteFragment();
            case 1:
                return new EventCompleteFragment();
            case 2:
                return new LocationCompleteFragment();
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
