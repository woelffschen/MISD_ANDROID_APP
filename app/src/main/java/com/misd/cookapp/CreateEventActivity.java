package com.misd.cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;

public class CreateEventActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        setTitle(""); // Titel für Activity festlegen
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_event_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveEvent();
            return true;
        }
        if (id == R.id.action_abort) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private void saveEvent() {
        EditText textEventMealName = (EditText) findViewById(R.id.editText);
        String eventMealName = textEventMealName.getText().toString();

        EditText textEventDescription = (EditText) findViewById(R.id.eventDescription);
        String eventDescription = textEventDescription.getText().toString();

        // TODO Heimatadresse verwenden

        EditText textEventStreet = (EditText) findViewById(R.id.eventStreet);
        String eventStreet = textEventStreet.getText().toString();

        EditText textEventPostalCode = (EditText) findViewById(R.id.eventPostalCode);
        int eventPostalCode = Integer.parseInt(textEventPostalCode.getText().toString());

        EditText textEventCity = (EditText) findViewById(R.id.eventCity);
        String eventCity = textEventCity.getText().toString();

        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
        // Check which radiobutton was clicked
        char genderRestriction;
        switch (selectedId) {
            case R.id.radiobutton_onlyMale:
                genderRestriction = 'm';
                break;
            case R.id.radiobutton_onlyFemale:
                genderRestriction = 'w';
                break;
            default:
                genderRestriction = 'b';
        }

        CheckBox chkEventLactoseFree = (CheckBox) findViewById(R.id.lactoseFree);
        boolean eventLactoseFree = chkEventLactoseFree.isChecked();
        CheckBox chkEventGlutenFree = (CheckBox) findViewById(R.id.glutenFree);
        boolean eventGlutenFree = chkEventGlutenFree.isChecked();
        CheckBox chkEventFructoseFree = (CheckBox) findViewById(R.id.fructoseFree);
        boolean eventFructoseFree = chkEventFructoseFree.isChecked();
        CheckBox chkEventSorbitFree = (CheckBox) findViewById(R.id.sorbitFree);
        boolean eventSorbitFree = chkEventSorbitFree.isChecked();

        RangeSeekBar textEventAge = (RangeSeekBar) findViewById(R.id.eventAge);
        int eventMinAge = textEventAge.getSelectedMinValue().intValue();
        int eventMaxAge = textEventAge.getSelectedMaxValue().intValue();

        CalendarView calEventDateTime = (CalendarView) findViewById(R.id.eventDateTime);

            /*
             * TODO DummyUser sowie Calendar ersetzen
             */

        Event createdEvent = new Event(eventDescription, (new Meal(eventMealName, eventLactoseFree, eventGlutenFree, eventFructoseFree, eventSorbitFree)), eventMinAge, eventMaxAge, genderRestriction, eventStreet, eventPostalCode, eventCity, (new User("Mustermann", "Max", "Teststraße", 48249, "Dülmen", 'm', 18, "+49123456")), pasteCalendar(2010, 10, 2, 12, 34));

        Intent i = new Intent(CreateEventActivity.this,
                ShowEventActivity.class);
        //pass data to an intent to load a specific fragment of reader activity
        i.putExtra(MainActivity.EVENT_EXTRA,createdEvent);
        startActivity(i);
    }
    
}