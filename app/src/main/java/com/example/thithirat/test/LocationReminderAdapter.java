package com.example.thithirat.test;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Thithirat on 6/3/2561.
 */

public class LocationReminderAdapter extends BaseAdapter{

    Context mContext;
    private List<LocationReminder> mLocationReminder;

    public LocationReminderAdapter(Context mcontext, List<LocationReminder> mLocationReminder) {
        this.mContext = mcontext;
        this.mLocationReminder = mLocationReminder;
    }

    @Override
    public int getCount() {
        return mLocationReminder.size();
    }

    @Override
    public Object getItem(int position) {
        return mLocationReminder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.location_reminder, null);
        TextView textviewname = (TextView)view.findViewById(R.id.lc_place_name);

        textviewname.setText(mLocationReminder.get(position).getName());

        view.setTag(mLocationReminder.get(position).getId());
        return view;
    }
}
