package com.misd.cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Event> myEvents = new ArrayList<>(); //ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.event)); // Titel fÃ¼r Activity festlegen

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Aufruf Methoden für ListView
        populateEventList();
        populateListView();
        registerClickCallback();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myevents) {
            Intent intent = new Intent(this, MyEventsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            // Intent intent = new Intent(this, ShowEventActivity.class);
            // startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            // Intent intent = new Intent(this, ???.class);
            // startActivity(intent);

        } else if (id == R.id.nav_manage) {
            // Intent intent = new Intent(this, ???.class);
            // startActivity(intent);

        } else if (id == R.id.nav_share) {
            // Intent intent = new Intent(this, ???.class);
            // startActivity(intent);

        } else if (id == R.id.nav_send) {
            // Intent intent = new Intent(this, ???.class);
            // startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Methoden für die ListView - Beispiele zur Veranschaulichung
    private void populateEventList(){
        myEvents.add(new Event(R.drawable.pic1, "Lasagne", "Münster", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Pizza", "Berlin", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Test", "Hamburg", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Salat", "München", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Nudelauflauf", "Osnabrück", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Lasagne", "Münster", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Pizza", "Berlin", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Test", "Hamburg", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Salat", "München", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Nudelauflauf", "Osnabrück", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Lasagne", "Münster", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Pizza", "Berlin", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Test", "Hamburg", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Salat", "München", pasteCalendar(2010,10,2,12,34)));
        myEvents.add(new Event(R.drawable.pic1, "Nudelauflauf", "Osnabrück", pasteCalendar(2010,10,2,12,34)));
    }

    private Calendar pasteCalendar(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day,hour,minute);
        return cal;
    }

    private void populateListView(){
        ArrayAdapter<Event> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.eventsListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.eventsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Event clickedEvent = myEvents.get(position);
                String message = "Du hast Position " + position + " angeklickt = " + clickedEvent.getMeal() + ".";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Event> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.main_list_item, myEvents);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.main_list_item, parent, false);
            }

            // Find the event to work with
            Event currentEvent = myEvents.get(position);

            // Fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_pic);
            imageView.setImageResource(currentEvent.getIconId());

            // Gericht:
            TextView mealText = (TextView) itemView.findViewById(R.id.textMeal);
            mealText.setText(currentEvent.getMeal());

            // Ort:
            TextView locationText = (TextView) itemView.findViewById(R.id.textLocation);
            locationText.setText(currentEvent.getLocation());

            // Datum:
            TextView datumText = (TextView) itemView.findViewById(R.id.textDate);
            datumText.setText(currentEvent.getDateAsString());

            // Uhrzeit:
            TextView uhrzeitText = (TextView) itemView.findViewById(R.id.textTime);
            uhrzeitText.setText(currentEvent.getTimeAsString());


            return itemView;
        }

    }
}
