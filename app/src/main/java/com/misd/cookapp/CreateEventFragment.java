package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Calendar;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateEventFragment extends Fragment {
    private static final String TAG = CreateEventFragment.class.toString();
    public static final String ARGS_EVENT_OBJECT = "args_event_object";

    private OnFragmentInteractionListener mListener;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Event erstellen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_event_fragment, container, false);
        registerSafeEventListener(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

/**
 * TODO Event erstellen - Übergaben speichern
 */
    private void registerSafeEventListener(View rootView) {
    Button safeEvent = (Button) rootView.findViewById(R.id.eventSafe);
    safeEvent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText textEventMealName = (EditText) getActivity().findViewById(R.id.editText);
            String eventMealName = textEventMealName.getText().toString();

            EditText textEventDescription = (EditText) getActivity().findViewById(R.id.eventDescription);
            String eventDescription = textEventDescription.getText().toString();

            // TODO Heimatadresse verwenden

            EditText textEventStreet = (EditText) getActivity().findViewById(R.id.eventStreet);
            String eventStreet = textEventStreet.getText().toString();

            EditText textEventPostalCode = (EditText) getActivity().findViewById(R.id.eventPostalCode);
            int eventPostalCode = Integer.parseInt(textEventPostalCode.getText().toString());

            EditText textEventCity = (EditText) getActivity().findViewById(R.id.eventCity);
            String eventCity = textEventCity.getText().toString();

            RadioGroup radioGroupGender = (RadioGroup) getActivity().findViewById(R.id.radioGroupGender);
            int selectedId = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
            // Check which radiobutton was clicked
            char genderRestriction;
            switch(selectedId) {
                case R.id.radiobutton_onlyMale:
                    genderRestriction = 'm';
                    break;
                case R.id.radiobutton_onlyFemale:
                    genderRestriction = 'w';
                    break;
                default:
                    genderRestriction = 'b';
            }

            CheckBox chkEventLactoseFree = (CheckBox) getActivity().findViewById(R.id.lactoseFree);
            boolean eventLactoseFree = chkEventLactoseFree.isChecked();
            CheckBox chkEventGlutenFree = (CheckBox) getActivity().findViewById(R.id.glutenFree);
            boolean eventGlutenFree = chkEventGlutenFree.isChecked();
            CheckBox chkEventFructoseFree = (CheckBox) getActivity().findViewById(R.id.fructoseFree);
            boolean eventFructoseFree = chkEventFructoseFree.isChecked();
            CheckBox chkEventSorbitFree = (CheckBox) getActivity().findViewById(R.id.sorbitFree);
            boolean eventSorbitFree = chkEventSorbitFree.isChecked();

            RangeSeekBar textEventAge = (RangeSeekBar) getActivity().findViewById(R.id.eventAge);
            int eventMinAge = textEventAge.getAbsoluteMinValue().intValue();
            int eventMaxAge = textEventAge.getAbsoluteMaxValue().intValue();

            CalendarView calEventDateTime = (CalendarView) getActivity().findViewById(R.id.eventDateTime);

            /*
             * TODO DummyUser sowie Calendar ersetzen
             */

            Event createdEvent = new Event(eventDescription,(new Meal(eventMealName,eventLactoseFree, eventGlutenFree, eventFructoseFree, eventSorbitFree)),eventMinAge,eventMaxAge, genderRestriction, eventStreet, eventPostalCode, eventCity,(new User("Mustermann", "Max", "Teststraße", 48249, "Dülmen", 'm', 18, "+49123456")),pasteCalendar(2010,10,2,12,34));

            //Change to the ShowEventFragment
            Fragment fragment = new ShowEventFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARGS_EVENT_OBJECT, createdEvent);
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    });
  }

}
