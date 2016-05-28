package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.misd.cookapp.HelperMethods.pasteCalendar;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface to handle interaction events.
 */
public class MainFragment extends Fragment {
    public static final String ARGS_EVENT_OBJECT = "args_event_object";
    private List<Event> myEvents = new ArrayList<>(); //ListView

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
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        // Aufruf Methoden für ListView
        populateEventList();
        populateListView(rootView);
        registerClickCallback(rootView);
        // Inflate the layout for this fragment
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
        User currentUser = new User("Landreh", "Michael", "Boeselagerstr. 69b", 48163, "Münster", 'm', 23, "+49 163 138 92 82");
        Meal currentMeal =  new Meal("Spaghetti Bolognese",false, false, false,false);

        myEvents.add(new Event("Ich möchte heute scheiße kochen.", currentMeal,
                18,60, 'b', "Blubstr.", 48163, "Münster", currentUser, pasteCalendar(2016,3,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        myEvents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

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

                //Change to the ShowEventFragment
                Fragment fragment = new ShowEventFragment();
                Bundle args = new Bundle();
                args.putSerializable(ARGS_EVENT_OBJECT, clickedEvent);
                fragment.setArguments(args);

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
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
            }

            // Find the event to work with
            Event currentEvent = myEvents.get(position);

            // Fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_pic);
            imageView.setImageResource(currentEvent.getIconId());

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

}
