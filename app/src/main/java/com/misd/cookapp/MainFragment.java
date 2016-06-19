package com.misd.cookapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.misd.cookapp.interfaces.IServer;

import java.util.ArrayList;
import java.util.List;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface to handle interaction events.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private List<Event> myEvents = new ArrayList<>(); //ListView
    private View rootView;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Events");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);

        // Aufruf Methoden für ListView
        populateEventList();

        registerClickCallback(rootView);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        populateEventList();

        super.onActivityResult(requestCode, resultCode, data);
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
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


    // Methoden für die ListView - Beispiele zur Veranschaulichung
    private void populateEventList(){

        new LoadEventsTask().execute();

        //String currentUserMail = CookApplication.getCookApplication().getLoggedInUser().getMailAddress();
        //Meal currentMeal =  new Meal("Spaghetti Bolognese", false, false, false, false, false, false);


        /*myEvents.add(new Event("Ich möchte heute scheiße kochen.", currentMeal,
                18,60, 'b', "Blubstr. 18", 48163, "Münster", currentUserMail, pasteCalendar(2016,3,2,12,34)));*/

    }



    private void populateListView(final View rootView){
        ArrayAdapter<Event> adapter = new MyListAdapter();
        ListView list = (ListView) rootView.findViewById(R.id.eventsListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback(final View rootView) {
        ListView list = (ListView) rootView.findViewById(R.id.eventsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Event clickedEvent = myEvents.get(position);

                Intent i = new Intent(getContext(),ShowEventActivity.class);
                //i.putExtra("eventId", clickedEvent.getEventId());
                i.putExtra(MainActivity.EVENT_EXTRA, clickedEvent);
                startActivity(i);
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<Event> {
        public MyListAdapter() {
            super(getActivity(), R.layout.main_list_item, myEvents);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.main_list_item, parent, false);
                Log.d(TAG, "itemView is null");
            }

            // Find the event to work with
            Event currentEvent = myEvents.get(position);

            // Gericht:
            TextView mealText = (TextView) itemView.findViewById(R.id.textMeal);
            mealText.setText(currentEvent.getEventMeal().getName());

            // Ort:
            TextView locationText = (TextView) itemView.findViewById(R.id.textLocation);
            locationText.setText(currentEvent.getEventCity());

            // Datum:
            TextView datumText = (TextView) itemView.findViewById(R.id.textDate);
            datumText.setText(currentEvent.getEventDateAsString());

            // Uhrzeit:
            TextView uhrzeitText = (TextView) itemView.findViewById(R.id.textTime);
            uhrzeitText.setText(currentEvent.getEventTimeAsString());


            return itemView;
        }

    }

    private class LoadEventsTask extends AsyncTask<Void, Void, List<Event>> {
        boolean taskFailed = false;

        @Override
        protected void onPostExecute(List<Event> events) {
            if (!taskFailed) {
                myEvents.addAll(events);
                populateListView(rootView);
            } else {
                Snackbar.make(rootView,"Eventliste konnte nicht geladen werden.",Snackbar.LENGTH_LONG).show();
            }

        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            List<Event> result= new ArrayList<Event>();
            try {
                IServer server = CookApplication.getCookApplication().getServer();
                result = server.getListOfEvents(CookApplication.getCookApplication().getSessionId());
                Log.i(TAG,"Server get Eventslist successful");
            }  catch (Exception e) {
                taskFailed = true;
                e.printStackTrace();
            }
            return result;
        }
    }

}
