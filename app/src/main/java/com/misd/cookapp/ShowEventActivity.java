package com.misd.cookapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                StatusDialogFragment dialogFragment = new StatusDialogFragment ();
                dialogFragment.show(fm, "Status Fragment");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get clickedEvent from Intent
        Intent i = getIntent();
        Event currentEvent = (Event) i.getSerializableExtra(MainActivity.EVENT_EXTRA);

        //Set u the ui with the data from the clickes event
        setupUi(currentEvent);
    }

    private void setupUi(final Event currentEvent) {
        setTitle(currentEvent.getEventMeal().getName());

        TextView textEventOwner = (TextView) findViewById(R.id.textEventOwner);
        textEventOwner.setText(currentEvent.getEventOwner());

        TextView textEventDescription = (TextView) findViewById(R.id.textEventDescription);
        textEventDescription.setText(currentEvent.getEventDescription());

        TextView textEventPostalCode = (TextView) findViewById(R.id.textEventPostalCode);
        textEventPostalCode.setText(String.valueOf(currentEvent.getEventPostal()));

        TextView textEventCity = (TextView) findViewById(R.id.textEventCity);
        textEventCity.setText(currentEvent.getEventCity());

        // TODO Adresse nur anzeigen lassen, wenn man zum Event zugelassen wurde
        TextView textEventStreet = (TextView) findViewById(R.id.textEventStreet);
        textEventStreet.setText(currentEvent.getEventStreet());
        textEventStreet.setVisibility(TextView.VISIBLE);

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

}
