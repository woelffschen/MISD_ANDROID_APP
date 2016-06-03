package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ShowEventFragment extends Fragment {
    private Event currentEvent;

    private OnFragmentInteractionListener mListener;

    public ShowEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Event anzeigen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_event_content, container, false);
        Bundle args = getArguments();
        currentEvent = (Event) args.getSerializable(MainActivity.EVENT_EXTRA);

        /*
         * TODO Teilname anfragen, Teilnahme beenden, Anfrage abbrechen hinzufügen
         */

        getActivity().setTitle(currentEvent.getEventMeal().getName());

        TextView textEventOwner = (TextView) rootView.findViewById(R.id.textEventOwner);
        textEventOwner.setText("Veranstalter:  " + currentEvent.getEventOwner().getFirstname() + " " + currentEvent.getEventOwner().getLastname());

        TextView textEventDescription = (TextView) rootView.findViewById(R.id.textEventDescription);
        textEventDescription.setText(currentEvent.getEventDescription());

        TextView textEventPostalCode = (TextView) rootView.findViewById(R.id.textEventPostalCode);
        textEventPostalCode.setText(String.valueOf(currentEvent.getEventPostal()));

        TextView textEventCity = (TextView) rootView.findViewById(R.id.textEventCity);
        textEventCity.setText(currentEvent.getEventCity());

        // TODO Adresse nur anzeigen lassen, wenn man zum Event zugelassen wurde
        TextView textEventStreet = (TextView) rootView.findViewById(R.id.textEventStreet);
        textEventStreet.setText(currentEvent.getEventStreet());
        textEventStreet.setVisibility(TextView.VISIBLE);

        TextView textEventDate = (TextView) rootView.findViewById(R.id.textEventDate);
        textEventDate.setText("Veranstaltungsdatum:  " + currentEvent.getEventDateAsString());

        TextView textEventGender = (TextView) rootView.findViewById(R.id.textEventGender);
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

        TextView textEventAge = (TextView) rootView.findViewById(R.id.textEventAge);
        textEventAge.setText("Alter erwünscht von " + currentEvent.getEventMinAge() + " bis " + currentEvent.getEventMaxAge() + " Jahren.");

        TextView textEventIncompatibility = (TextView) rootView.findViewById(R.id.textEventIncompatibility);
        TextView textEventLactose = (TextView) rootView.findViewById(R.id.textEventLactoseFree);
        if (currentEvent.getEventMeal().isLactoseFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventLactose.setVisibility(TextView.VISIBLE);
        }
        TextView textEventGluten = (TextView) rootView.findViewById(R.id.textEventGlutenFree);
        if (currentEvent.getEventMeal().isGlutenFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventGluten.setVisibility(TextView.VISIBLE);
        }
        TextView textEventFructose = (TextView) rootView.findViewById(R.id.textEventFructoseFree);
        if (currentEvent.getEventMeal().isFructoseFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventFructose.setVisibility(TextView.VISIBLE);
        }
        TextView textEventSorbit = (TextView) rootView.findViewById(R.id.textEventSorbitFree);
        if (currentEvent.getEventMeal().isSorbitFree() == true){
            textEventIncompatibility.setVisibility(TextView.VISIBLE);
            textEventSorbit.setVisibility(TextView.VISIBLE);
        }

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


}
