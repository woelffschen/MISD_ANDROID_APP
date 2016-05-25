package com.misd.cookapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);
        setTitle(getString(R.string.title_activity_create_event)); // Titel für Activity festlegen
    }
}
