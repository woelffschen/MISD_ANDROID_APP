package com.misd.cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        textEventOwner.setText(currentEvent.getEventOwner().getFirstname() + " " + currentEvent.getEventOwner().getLastname());

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
    }
}
