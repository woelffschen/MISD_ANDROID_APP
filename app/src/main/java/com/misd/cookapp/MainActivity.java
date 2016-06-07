package com.misd.cookapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.misd.cookapp.helpers.HelperMethods;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener, MyEventsFragment.OnFragmentInteractionListener, ShowEventFragment.OnFragmentInteractionListener,
NewsFragment.OnFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public static final String SHOW_EVENT_FRAGMENT_NAME = "create_event_fragment";
    public static final String EVENT_EXTRA = "event";
    public static final String FRAGMENT_EXTRA = "fragment";
    public static final String TAG = "MainActivity";

    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.event)); // Titel fÃ¼r Activity festlegen


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
                    startActivity(i);
                }
            });

        loadFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set the User Info in Navigation Drawer
        View navHeaderLayout = navigationView.getHeaderView(0);

        TextView userMail = (TextView) navHeaderLayout.findViewById(R.id.user_mail);
        GoogleSignInAccount  acct = (GoogleSignInAccount) HelperMethods.getPreferenceObject(this,HelperMethods.PREFS_USER_DATA, GoogleSignInAccount.class, "GoogleSignInAccount");
        userMail.setText(acct.getEmail());

        TextView userDisplayName = (TextView) navHeaderLayout.findViewById(R.id.user_display_name);
        userDisplayName.setText(acct.getDisplayName());




    }

    private void loadFragment() {
        Intent i = getIntent();
        String pFragmentExtra = i.getStringExtra(FRAGMENT_EXTRA);


        //Abfrage, ob ein bestimmtes Fragment aufgerufen werden soll. Wir keines angegeben, wird MainFragment geladen.
        if (pFragmentExtra != null && pFragmentExtra.equals(SHOW_EVENT_FRAGMENT_NAME)) {

            Fragment fragment = new ShowEventFragment();
            Bundle args = new Bundle();
            args.putSerializable(EVENT_EXTRA, i.getSerializableExtra(EVENT_EXTRA));
            fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            MainFragment firstFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, firstFragment)
                    .addToBackStack(null)
                    .commit();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        Bundle args = new Bundle();

        if (id == R.id.nav_my_events) {
            fragment = new MyEventsFragment();

        } else if (id == R.id.nav_main) {
            fragment = new MainFragment();

        } else if (id == R.id.nav_news) {
            fragment = new NewsFragment();

        } else if (id == R.id.nav_share) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    //.requestScopes(new Scope(Scopes.PROFILE))
                    .requestEmail()
                    //.requestIdToken(SERVER_CLIENT_ID) Wird für die kommunikation mit dem Server gebraucht. Erfordert das hizufügen weiterer rechte
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            Intent i = new Intent(getParent(),LoginActivity.class);
                            startActivity(i);
                            // [END_EXCLUDE]
                        }
                    });
            fragment = new MainFragment();

        } else if (id == R.id.nav_send) {
            fragment = new MainFragment();

        } else {
            fragment = new MainFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).addToBackStack(null)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    @Override
    public void onConnected(Bundle arg0) {

    }

    @Override
    public void onConnectionSuspended(int arg0) {

        mGoogleApiClient.connect();
        // updateUI(false);
    }

}
