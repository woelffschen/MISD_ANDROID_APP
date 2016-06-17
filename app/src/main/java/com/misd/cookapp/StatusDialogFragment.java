package com.misd.cookapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
 * @author Ines Müller
 */
public class StatusDialogFragment extends DialogFragment {

    // TODO Aktion bei Anfragen ausführen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.status_dialog_fragment, container, false);
        //getDialog().setTitle("Status Dialog");

        Button requestButton = (Button) rootView.findViewById(R.id.request_attendance);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_attendance);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do soemthing
            }
        });

        Button confirmButton = (Button) rootView.findViewById(R.id.confirm_attendance);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });

        Button rejectButton = (Button) rootView.findViewById(R.id.reject_attendance);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
        return rootView;
    }
}
