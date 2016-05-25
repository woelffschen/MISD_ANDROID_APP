package com.misd.cookapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

public class MyEventsActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Event>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_events_activity);
        setTitle(getString(R.string.my_events)); // Titel für Activity festlegen

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<Event>>();

        // Adding child data
        listDataHeader.add("Meine Events");
        listDataHeader.add("Meine Anfragen");
        listDataHeader.add("Meine Teilnahmen");
        listDataHeader.add("Vergangene Events");

        // Adding child data
        List<Event> myevents = new ArrayList<>();

        User currentUser = new User("Landreh", "Michael", "Boeselagerstr. 69b", 48163, "Münster", 'm', 23, "+49 163 138 92 82");
        Meal currentMeal =  new Meal("Spaghetti Bolognese",false, false, false,false);

        myevents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> myrequests = new ArrayList<>();
        myrequests.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> myattendance = new ArrayList<>();
        myattendance.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> pastevents = new ArrayList<>();
        pastevents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));


        listDataChild.put(listDataHeader.get(0), myevents); // Header, Child data
        listDataChild.put(listDataHeader.get(1), myrequests);
        listDataChild.put(listDataHeader.get(2), myattendance);
        listDataChild.put(listDataHeader.get(3), pastevents);
    }

}
