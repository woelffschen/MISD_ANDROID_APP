package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateEventFragment extends Fragment {
    private static final String TAG = CreateEventFragment.class.toString();

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
        registerSafeEventListener();
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
    private void registerSafeEventListener() {
    Button safeEvent = (Button) getActivity().findViewById(R.id.eventSafe);
    safeEvent.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText eventMealName = (EditText) getActivity().findViewById(R.id.editText);
            eventMealName.getText();

            EditText eventDescription = (EditText) getActivity().findViewById(R.id.eventDescription);
            eventDescription.getText();

            // Heimatadresse verwenden

            EditText eventStreet = (EditText) getActivity().findViewById(R.id.eventStreet);
            eventStreet.getText();

            EditText eventPostalCode = (EditText) getActivity().findViewById(R.id.eventPostalCode);

            EditText eventCity = (EditText) getActivity().findViewById(R.id.eventCity);

            RadioGroup radioGroupGender = (RadioGroup) getActivity().findViewById(R.id.radioGroupGender);
            int selectedId = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
            RadioButton radioGenderButton = (RadioButton) getActivity().findViewById(selectedId); // find the radiobutton by returned id
            radioGenderButton.getText();

            // Unverträglichkeiten
            // Altersbeschränkung
            // User
            // Calendar

        // erwartet: String eventDescription, Meal eventMeal, int eventMinAge, int eventMaxAge, char eventGender, String eventStreet, int eventPostal, String eventCity, User eventOwner, Calendar eventDate
        // Event createdEvent = new Event(eventDescription,(new Meal(eventMealName,??, ??, ??, ??)),?,?, radioGenderButton, eventStreet, eventPostalCode, eventCity,(new User()),?);
        }
    });
  }
}
