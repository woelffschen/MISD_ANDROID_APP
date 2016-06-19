package com.misd.cookapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.interfaces.IServer;

/*
 * @author Ines Müller
 */
public class StatusDialogFragment extends DialogFragment {

    // TODO Aktion bei Anfragen ausführen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.status_dialog_fragment, container, false);
        getDialog().setTitle("Bitte eine Aktion auswählen:");

        Button requestButton = (Button) rootView.findViewById(R.id.request_attendance);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new RequestEventTask().execute();
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


    private class RequestEventTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int status = 0;
            try {
                /*
                CookApplication cookApplication = (CookApplication) getApplication();
                IServer server = cookApplication.getServer();
                int sessionId = cookApplication.getSessionId();
                int eventId = cookApplication.getCurrentEvent().getEventId();
                String email = cookApplication.getLoggedInUser().getMailAddress();
                status = server.request(sessionId, eventId, email);*/
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return status;
        }
    }
}
