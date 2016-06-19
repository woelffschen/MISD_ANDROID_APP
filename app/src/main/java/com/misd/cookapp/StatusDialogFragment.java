package com.misd.cookapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
    View rootView;
    Button cancelButton;
    Button requestButton;
    Button confirmButton;
    Button rejectButton;

    // TODO Aktion bei Anfragen ausführen

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.status_dialog_fragment, container, false);
        getDialog().setTitle("Bitte eine Aktion auswählen:");
        

        //Set Buttons
        requestButton = (Button) rootView.findViewById(R.id.request_attendance);
        cancelButton = (Button) rootView.findViewById(R.id.cancel_attendance);
        confirmButton = (Button) rootView.findViewById(R.id.confirm_attendance);
        rejectButton = (Button) rootView.findViewById(R.id.reject_attendance);

        int status = getArguments().getInt(ShowEventActivity.EXTRA_EVENT_STATUS);

        setupUi(status);
        setupOnButtonClickListener();

        return rootView;
    }

    private void setupUi(int status) {
        switch (status) {
            case -1:
                //Keine Teilnahme
                requestButton.setVisibility(Button.VISIBLE);
                break;
            case 0:
                //Eventowner
                cancelButton.setVisibility(Button.VISIBLE);
                break;
            case 1:
                //Event abgesagt
                break;
            case 2:
                //Teilnahme bestätigt
                break;
            case 3:
                //Teilnahme angefragt
                break;
            case 4:
                //Teilnahme abgelehnt
                break;
        }

    }

    private void setupOnButtonClickListener() {
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new RequestEventTask().execute();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do soemthing
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // do something
            }
        });
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            dismiss();
            super.onPostExecute(integer);

        }
    }
}
