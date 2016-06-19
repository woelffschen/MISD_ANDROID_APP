package com.misd.cookapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.misd.cookapp.interfaces.IServer;

import org.w3c.dom.Text;

public class ShowEventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_STATUS = "event_status";
    private int eventStatus = -1;
    private static final String TAG = "ShowEventActivity";
    private Event currentEvent;
    View rootView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootView = findViewById(android.R.id.content);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                StatusDialogFragment dialogFragment = new StatusDialogFragment ();
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_EVENT_STATUS, eventStatus);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "Status Fragment");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get clickedEvent from Intent
        Intent i = getIntent();
        //int eventId = i.getIntExtra("eventId", 0);

        currentEvent = (Event) i.getSerializableExtra(MainActivity.EVENT_EXTRA);
        setTitle(currentEvent.getEventMeal().getName());

        new LoadEventStatusTask().execute();


    }

    private void setupUi(final Event currentEvent) {
        boolean isConfirmed = false;

        TextView textEventOwner = (TextView) findViewById(R.id.textEventOwner);
        textEventOwner.setText(currentEvent.getEventOwner());

        TextView textEventDescription = (TextView) findViewById(R.id.textEventDescription);
        textEventDescription.setText(currentEvent.getEventDescription());

        TextView textEventStatus = (TextView) findViewById(R.id.textEventStatus);
        switch (eventStatus) {
            case -1:
                textEventStatus.setVisibility(TextView.GONE);
                break;
            case 0:
                textEventStatus.setText(getString(R.string.eventStatus_0_eventOwner));
                isConfirmed = true;
                break;
            case 1:
                textEventStatus.setText(getString(R.string.eventStatus_1_canceled));
                isConfirmed = false;
                break;
            case 2:
                textEventStatus.setText(getString(R.string.eventStatus_2_confirmed));
                isConfirmed = true;
                break;
            case 3:
                textEventStatus.setText(getString(R.string.eventStatus_3_requested));
                isConfirmed = false;
                break;
            case 4:
                textEventStatus.setText(getString(R.string.eventStatus_4_rejected));
                isConfirmed = false;
                break;
            default:
                textEventStatus.setText(getString(R.string.eventStatus_default));
                isConfirmed = false;
                break;
        }

        TextView textEventPostalCode = (TextView) findViewById(R.id.textEventPostalCode);
        textEventPostalCode.setText(String.valueOf(currentEvent.getEventPostal()));

        TextView textEventCity = (TextView) findViewById(R.id.textEventCity);
        textEventCity.setText(currentEvent.getEventCity());

        TextView textEventStreet = (TextView) findViewById(R.id.textEventStreet);
        TextView textDataExcluded = (TextView) findViewById(R.id.contact_data_excluded);
        if (isConfirmed) {
            textEventStreet.setText(currentEvent.getEventStreet());
            textEventStreet.setVisibility(TextView.VISIBLE);
            textDataExcluded.setVisibility(TextView.GONE);
        } else {
            textEventStreet.setVisibility(TextView.GONE);
            textDataExcluded.setVisibility(TextView.VISIBLE);
        }


        TextView textEventDate = (TextView) findViewById(R.id.textEventDate);
        textEventDate.setText(currentEvent.getEventDateAsString());

        TextView textEventTime = (TextView) findViewById(R.id.textEventTime);
        textEventTime.setText(currentEvent.getEventTimeAsString());

        TextView textEventGender = (TextView) findViewById(R.id.textEventGender);
        char eventGender = currentEvent.getEventGender();
        switch(eventGender) {
            case 'm':
                textEventGender.setText("nur männliche Teilnehmer");
                break;
            case 'w':
                textEventGender.setText("nur weibliche Teilnehmer");
                break;
            default:
                textEventGender.setText("keine Beschränkung");
        }

        TextView textEventAge = (TextView) findViewById(R.id.textEventAge);
        textEventAge.setText(currentEvent.getEventMinAge() + " bis " + currentEvent.getEventMaxAge() + " Jahre");

        TextView textEventIncompatibility = (TextView) findViewById(R.id.textEventIncompatibility);
        TextView textEventLactose = (TextView) findViewById(R.id.textEventLactoseFree);
        if (currentEvent.getEventMeal().isLactoseFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventLactose.setVisibility(TextView.VISIBLE);
        }
        TextView textEventGluten = (TextView) findViewById(R.id.textEventGlutenFree);
        if (currentEvent.getEventMeal().isGlutenFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventGluten.setVisibility(TextView.VISIBLE);
        }
        TextView textEventFructose = (TextView) findViewById(R.id.textEventFructoseFree);
        if (currentEvent.getEventMeal().isFructoseFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventFructose.setVisibility(TextView.VISIBLE);
        }
        TextView textEventSorbit = (TextView) findViewById(R.id.textEventSorbitFree);
        if (currentEvent.getEventMeal().isSorbitFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventSorbit.setVisibility(TextView.VISIBLE);
        }
        TextView textEventVegan = (TextView) findViewById(R.id.textEventVegan);
        if (currentEvent.getEventMeal().isVegan() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventVegan.setVisibility(TextView.VISIBLE);
        }
        TextView textEventVegetarisch = (TextView) findViewById(R.id.textEventVegetarisch);
        if (currentEvent.getEventMeal().isVegetarisch() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventVegetarisch.setVisibility(TextView.VISIBLE);
        }
    }


    public class LoadEventStatusTask extends AsyncTask<Void, Void, Void> {

        private boolean taskFailed = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                CookApplication ca = CookApplication.getCookApplication();

                IServer server = ca.getServer();
                String email = ca.getLoggedInUser().getMailAddress();
                int eventId = currentEvent.getEventId();
                eventStatus = server.getEventStatus(email, eventId);
                Log.i(TAG, "Loaded EventStatus successfully");
            }  catch (Exception e) {
                taskFailed = true;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void params) {
            hideProgressDialog();
            if (!taskFailed) {
                setupUi(currentEvent);
            } else {
                Snackbar.make(rootView,"Der Status des Events konnte nicht geladen werden.",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading_event_status));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * This method hides the progress dialog
     *
     */
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
