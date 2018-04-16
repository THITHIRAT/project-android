package com.example.thithirat.test;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    Context mContext;
    private List<Notification> notification;

    public NotificationAdapter(Context mContext, List<Notification> notification) {
        this.mContext = mContext;
        this.notification = notification;
    }

    @Override
    public int getCount() {
        return notification.size();
    }

    @Override
    public Object getItem(int position) {
        return notification.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View root_view = View.inflate(mContext, R.layout.notification, null);

        TextView tv_before_after = (TextView) root_view.findViewById(R.id.lv_before_after);
        tv_before_after.setText(notification.get(position).getBefore_after());

        TextView tv_number = (TextView) root_view.findViewById(R.id.lv_number);
        tv_number.setText(notification.get(position).getNumber());

        TextView tv_type = (TextView) root_view.findViewById(R.id.lv_type);
        tv_type.setText(notification.get(position).getType());

        TextView remove = (TextView) root_view.findViewById(R.id.lv_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.remove(position);
                notifyDataSetChanged();
            }
        });
        return root_view;
    }
}
