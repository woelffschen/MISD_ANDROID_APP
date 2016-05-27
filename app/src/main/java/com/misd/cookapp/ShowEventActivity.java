package com.misd.cookapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

public class ShowEventActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        User currentUser = new User("Landreh", "Michael", "Boeselagerstr. 69b", 48163, "Münster", 'm', 23, "+49 163 138 92 82");
        Meal currentMeal =  new Meal("Spaghetti Bolognese",false, false, false,false);

        Event currentEvent = new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34));


        setTitle(currentEvent.getEventMeal().getName());
        
        TextView textEventCity = (TextView) findViewById(R.id.textEventCity);
        textEventCity.setText(currentEvent.getEventCity());


        /*
         * TODO EINFÜGEN WEITERER DATEN
         */

        final Button acceptButton = (Button) findViewById(R.id.buttonAccept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben akzeptieren angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button declineButton = (Button) findViewById(R.id.buttonDecline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben ablehnen angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben löschen angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}
