package com.example.thithirat.test;


import android.app.AlertDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditAccountFragment extends Fragment {


    public EditAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);

        getActivity().setTitle("My Account");

        ImageButton change_email = (ImageButton) view.findViewById(R.id.email_button);
        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.changeemail, null);

                builder.setView(rootview);
                final AlertDialog dialog_change_email = builder.create();
                dialog_change_email.show();

                Button save = (Button) rootview.findViewById(R.id.save_email);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_email.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel_email);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_email.cancel();
                    }
                });
            }
        });

        ImageButton change_password = (ImageButton) view.findViewById(R.id.password_button);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View rootview = getLayoutInflater().inflate(R.layout.changepassword, null);

                builder.setView(rootview);
                final AlertDialog dialog_change_email = builder.create();
                dialog_change_email.show();

                Button save = (Button) rootview.findViewById(R.id.save_email);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_email.cancel();
                    }
                });

                Button cancel = (Button) rootview.findViewById(R.id.cancel_email);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_change_email.cancel();
                    }
                });
            }
        });

        return view;
    }

}
