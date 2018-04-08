package com.example.thithirat.test;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class SubtasknameAdapter extends BaseAdapter {
    Context mContext;
    private List<Taskname> taskname;

    String myFragment;

    public SubtasknameAdapter(Context mContext, List<Taskname> taskname, String myFragment) {
        this.mContext = mContext;
        this.taskname = taskname;
        this.myFragment = myFragment;
    }

    @Override
    public int getCount() {
        return taskname.size();
    }

    @Override
    public Object getItem(int position) {
        return taskname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(mContext, R.layout.sugguest, null);

        TextView et_taskname = (TextView) view.findViewById(R.id.task_name);
        et_taskname.setMaxLines(1);

        final String str_taskname = taskname.get(position).getName();
        et_taskname.setText(str_taskname);
        view.setTag(taskname.get(position).getId());

        RelativeLayout task = (RelativeLayout)view.findViewById(R.id.sugguest_task);
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_subtaskname = taskname.get(position).getName();
                Log.e("Show Subtaskname", str_subtaskname);

                View root_update_subtaskname = UpdateReminderFragment.getroot();
                View root_add_subtaskname = AddReminderFragment.getroot();

                if(myFragment.equals("UpdateReminderFragment")) {
                    EditText taskname_popup_update = (EditText) root_update_subtaskname.findViewById(R.id.sugguest_addsubtaskname);
                    taskname_popup_update.setText(str_subtaskname);
                }
                if(myFragment.equals("AddReminderFragment")) {
                    EditText taskname_popup_add = (EditText)root_add_subtaskname.findViewById(R.id.sugguest_addsubtaskname);
                    taskname_popup_add.setText(str_subtaskname);
                }
            }
        });

        return view;
    }
}
