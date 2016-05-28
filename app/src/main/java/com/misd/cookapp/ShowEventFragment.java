package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

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
        Bundle args = getArguments();
        currentEvent = (Event) args.getSerializable(MainFragment.ARGS_EVENT_OBJECT);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.show_event_content, container, false);




        getActivity().setTitle(currentEvent.getEventMeal().getName());

        TextView textEventOwner = (TextView) rootView.findViewById(R.id.textEventOwner);
        textEventOwner.setText(currentEvent.getEventOwner().getFirstname() + " " + currentEvent.getEventOwner().getLastname());

        TextView textEventCity = (TextView) rootView.findViewById(R.id.textEventCity);
        textEventCity.setText(currentEvent.getEventCity());

        TextView textEventDescription = (TextView) rootView.findViewById(R.id.textEventDescription);
        textEventDescription.setText(currentEvent.getEventDescription());



        /*
         * TODO EINFÜGEN WEITERER DATEN
         */


        final Button acceptButton = (Button) rootView.findViewById(R.id.buttonAccept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben akzeptieren angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button declineButton = (Button) rootView.findViewById(R.id.buttonDecline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben ablehnen angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button deleteButton = (Button) rootView.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Snackbar.make(view, "Sie haben löschen angeklickt.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
