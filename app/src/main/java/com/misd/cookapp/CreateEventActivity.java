package com.misd.cookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

public class CreateEventActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);

        setTitle(getString(R.string.event)); // Titel fÃ¼r Activity festlegen

        registerSafeEventListener();
    }


    private void registerSafeEventListener() {
        Button safeEvent = (Button) findViewById(R.id.eventSafe);
        safeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        MainActivity.class);
                //pass data to an intent to load a specific fragment of reader activity
                i.putExtra(MainActivity.FRAGMENT_EXTRA, MainActivity.SHOW_EVENT_FRAGMENT_NAME);
                i.putExtra(MainActivity.EVENT_EXTRA,createdEvent);
                startActivity(i);
            }
        });
    }

}