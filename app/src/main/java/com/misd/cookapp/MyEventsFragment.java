package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.misd.cookapp.HelperMethods.pasteCalendar;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link MyEventsFragment.OnFragmentInteractionListener} interface to handle interaction events.
 */
public class MyEventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Event>> listDataChild;

    public MyEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void prepareExpandableListView(final View rootView) {
        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);


        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<Event>>();

        // Adding child data
        listDataHeader.add("Meine Events");
        listDataHeader.add("Meine Anfragen");
        listDataHeader.add("Meine Teilnahmen");
        listDataHeader.add("Vergangene Events");

        // Adding child data
        List<Event> myevents = new ArrayList<>();

        User currentUser = new User("Landreh", "Michael", "Boeselagerstr. 69b", 48163, "Münster", 'm', 23, "+49 163 138 92 82");
        Meal currentMeal =  new Meal("Spaghetti Bolognese",false, false, false,false);

        myevents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> myrequests = new ArrayList<>();
        myrequests.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> myattendance = new ArrayList<>();
        myattendance.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));

        List<Event> pastevents = new ArrayList<>();
        pastevents.add(new Event("Ich möchte heute etwas tolles kochen.", currentMeal,
                18,60, 'b', "Boeselagerstr. 69b", 48163, "Münster", currentUser, pasteCalendar(2010,10,2,12,34)));


        listDataChild.put(listDataHeader.get(0), myevents); // Header, Child data
        listDataChild.put(listDataHeader.get(1), myrequests);
        listDataChild.put(listDataHeader.get(2), myattendance);
        listDataChild.put(listDataHeader.get(3), pastevents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_events_fragment, container, false);
        // Aufruf Methoden für ListView
        // preparing list data
        prepareListData();
        prepareExpandableListView(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
