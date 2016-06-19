package com.misd.cookapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * @author Michael Landreh, Ines Mueller
 */
public class NewsFragment extends Fragment {
    private List<String> myEventNews = new ArrayList<>(); //ListView
    private static final int TYPE_REQUEST = 0;
    private static final int TYPE_MESSAGE = 1;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Benachrichtigungen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        // Aufruf Methoden für ListView
        populateEventList();
        populateListView(rootView);
        registerClickCallback(rootView);

        // TODO Sinnvolle Benachrichtigungen hinzufügen


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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // Methoden für die ListView - Beispiele zur Veranschaulichung
    private void populateEventList(){

        myEventNews.add("Benachrichtigung 1");
        myEventNews.add("Benachrichtigung 2");
        myEventNews.add("Benachrichtigung 3");

    }

    private void populateListView(final View rootView){
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) rootView.findViewById(R.id.newsListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback(final View rootView) {
        ListView list = (ListView) rootView.findViewById(R.id.newsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                String clickedMessage = myEventNews.get(position);
            }
        });
    }

    /*
     * TODO Prüfung welches Nachrichtenlayout angezeigt werden soll
     */

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(getActivity(), R.layout.news_list_item, myEventNews);
        }
        Event currentEvent;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getActivity().getLayoutInflater().inflate(R.layout.news_list_item2, parent, false);
            }
            return itemView;
        }

        @Override
        public int getViewTypeCount() {
            // Returns the number of types of Views that will be created by getView(int, View, ViewGroup).
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            // Get the type of View that will be created by getView(int, View, ViewGroup) for the specified item.
            return position % 2;
        }

    }


}
