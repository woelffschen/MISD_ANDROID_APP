package com.misd.cookapp;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.misd.cookapp.helpers.HelperMethods;
import com.misd.cookapp.interfaces.IServer;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;

public class CreateEventActivity extends AppCompatActivity implements DatePickerFragment.OnDatePickedListener, TimePickerFragment.OnTimePickedListener {

    private Toolbar toolbar;
    private int eventYear;
    private int eventMonth;
    private int eventDay;
    private int eventMinute;
    private int eventHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        setTitle(""); // Titel für Activity festlegen

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
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
            if (isValidMealName() &&
                    isValidEventDate() &&
                    isValidEventTime() &&
                    isValidEventStreet() &&
                    isValidEventPc() &&
                    isValidEventCity()) {
                saveEvent();
                return true;
            }

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
        CheckBox chkEventVegan = (CheckBox) findViewById(R.id.vegan);
        boolean eventVegan = chkEventVegan.isChecked();
        CheckBox chkEventVegetarisch = (CheckBox) findViewById(R.id.vegetarisch);
        boolean eventVegetarisch = chkEventVegetarisch.isChecked();

        final RangeSeekBar textEventAge = (RangeSeekBar) findViewById(R.id.eventAge);
        int eventMinAge = textEventAge.getSelectedMinValue().intValue();
        int eventMaxAge = textEventAge.getSelectedMaxValue().intValue();

        CookApplication cookApplication = CookApplication.getCookApplication();

        Event createdEvent = new Event(0,eventDescription, (new Meal(eventMealName, eventLactoseFree, eventGlutenFree, eventFructoseFree, eventSorbitFree, eventVegan, eventVegetarisch)), eventMinAge, eventMaxAge, genderRestriction, eventStreet, eventPostalCode, eventCity, cookApplication.getLoggedInUser().getMailAddress(), pasteCalendar(eventYear, eventMonth, eventDay, eventHour, eventMinute));
        new CreateEventTask().execute(createdEvent);

    }

    @Override
    public void onDatePicked(final int year, final int month, final int day) {
        eventYear = year;
        eventMonth = month;
        eventDay = day;

        Button datePicker = (Button) findViewById(R.id.date_picker);
        datePicker.setText(day + "." + month + "." + year);
    }

    @Override
    public void onTimePicked(final int hour, final int minute) {
        eventHour = hour;
        eventMinute = minute;
        Button timePicker = (Button) findViewById(R.id.time_picker);
        timePicker.setText(hour + ":" + minute);
    }

    /*
     * @author Ines Müller - Validierung
     */
    public boolean isValidMealName() {
        EditText textEventMealName = (EditText) findViewById(R.id.editText);
        if (textEventMealName.length() == 0) {
            textEventMealName.setError("Feld darf nicht leer sein.");
            return false;
        } else if (textEventMealName.length() > 20) {
            textEventMealName.setError("Es dürfen max. 20 Zahlen eingegeben werden.");
            return false;
        } else {
            textEventMealName.setError(null);
            return true;
        }
    }

    public boolean isValidEventStreet() {
        EditText textEventStreet = (EditText) findViewById(R.id.eventStreet);
        if (textEventStreet.length() == 0) {
            textEventStreet.setError("Feld darf nicht leer sein.");
            return false;
        } else {
            textEventStreet.setError(null);
            return true;
        }
    }

    public boolean isValidEventPc() {

        EditText textEventPostalCode = (EditText) findViewById(R.id.eventPostalCode);
        if (textEventPostalCode.length() == 0) {
            textEventPostalCode.setError("Feld darf nicht leer sein.");
            return false;
        } else if (!HelperMethods.isNumericInt(textEventPostalCode.getText().toString())) {
            textEventPostalCode.setError("Es dürfen nur Zahlen eingegeben werden.");
            return false;
        } else {
            textEventPostalCode.setError(null);
            return true;
        }
    }

    public boolean isValidEventCity() {
        EditText textEventCity = (EditText) findViewById(R.id.eventCity);
        if (textEventCity.length() == 0) {
            textEventCity.setError("Feld darf nicht leer sein.");
            return false;
        } else {
            textEventCity.setError(null);
            return true;
        }
    }

    public boolean isValidEventDate() {

        Button btnDate = (Button) findViewById(R.id.date_picker);
        if (btnDate.getText().equals("Wähle ein Datum")) {
            btnDate.setError("Bitte wählen Sie ein Datum aus");
            return false;
        }
        btnDate.setError(null);
        return true;
    }

    public boolean isValidEventTime() {
        Button btnTime = (Button) findViewById(R.id.time_picker);
        if (btnTime.getText().equals("Wähle eine Zeit")) {
            btnTime.setError("Bitte wählen Sie ein Datum aus");
            return false;
        }
        btnTime.setError(null);
        return true;
    }


    private class CreateEventTask extends AsyncTask<Event, Void, Integer> {

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer != null && integer > 0) {
                Intent i = new Intent(getApplicationContext(), ShowEventActivity.class);
                i.putExtra("eventId", integer);
                startActivity(i);
            } else {
                // TODO Fehler
            }
        }

        @Override
        protected Integer doInBackground(Event... params) {
            IServer server = CookApplication.getCookApplication().getServer();
            CookApplication cookApplication = CookApplication.getCookApplication();
            int sessionId = cookApplication.getSessionId();
            String userId = cookApplication.getLoggedInUser().getMailAddress();

            try {
                return server.create(sessionId, userId,
                        params[0].getEventMinAge(),
                        params[0].getEventMaxAge(),
                        params[0].getEventStreet(),
                        params[0].getEventPostal(),
                        params[0].getEventCity(),
                        params[0].getEventDescription(),
                        params[0].getEventGender(),
                        params[0].getEventDate(),
                        params[0].getEventOwner(),
                        params[0].getEventMeal().getName(),
                        params[0].getEventMeal().isLactoseFree(),
                        params[0].getEventMeal().isGlutenFree(),
                        params[0].getEventMeal().isFructoseFree(),
                        params[0].getEventMeal().isSorbitFree(),
                        params[0].getEventMeal().isVegan(),
                        params[0].getEventMeal().isVegetarisch());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}