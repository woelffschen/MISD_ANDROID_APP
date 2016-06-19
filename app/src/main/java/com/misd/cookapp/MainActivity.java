package com.misd.cookapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.misd.cookapp.exceptions.LoginFailedException;
import com.misd.cookapp.helpers.HelperMethods;
import com.misd.cookapp.Server;
import com.misd.cookapp.interfaces.IServer;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.math.BigInteger;

import static com.misd.cookapp.helpers.HelperMethods.pasteCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener, MyEventsFragment.OnFragmentInteractionListener,
NewsFragment.OnFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener {

    public static final String SHOW_EVENT_FRAGMENT_NAME = "create_event_fragment";
    public static final String EVENT_EXTRA = "event";
    public static final String FRAGMENT_EXTRA = "fragment";
    public static final String TAG = "MainActivity";
    public static final String START_LOGOUT = "start_logout";


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

        CookApplication cookApplication = (CookApplication) getApplication();

        String currentUserMail = cookApplication.getLoggedInUser().getMailAddress();
        TextView userEmail = (TextView) navHeaderLayout.findViewById(R.id.user_mail);
        userEmail.setText(currentUserMail);

        String currentUserFirstname = cookApplication.getLoggedInUser().getFirstname();
        String currentUserLastname = cookApplication.getLoggedInUser().getLastname();
        TextView userDisplayName = (TextView) navHeaderLayout.findViewById(R.id.user_name);
        userDisplayName.setText(currentUserFirstname + " " + currentUserLastname);

    }

    private void loadFragment() {
            MainFragment firstFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, firstFragment)
                    .commit();
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
        //getMenuInflater().inflate(R.menu.create_event_activity_menu, menu);
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

        CookApplication cookApplication = (CookApplication)getApplication();

        if (id == R.id.nav_my_events) {
            fragment = new MyEventsFragment();

        } else if (id == R.id.nav_main) {
            fragment = new MainFragment();

        } else if (id == R.id.nav_news) {
            fragment = new NewsFragment();

        } else if (id == R.id.nav_share) {
            fragment = new MainFragment();

            LogoutTask logoutTask = new LogoutTask();
            logoutTask.execute(cookApplication.getSessionId());

            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra(START_LOGOUT,START_LOGOUT);
            startActivity(i);
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

    private class LogoutTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Start logout at server...");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "Logout at server finished...");

            Intent iMainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(iMainActivity);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                Log.i(TAG,"Server logout successful");
                CookApplication cookApplication = (CookApplication) getApplication();
                IServer server = cookApplication.getServer();

                int sessionId = cookApplication.getSessionId();

                if(sessionId > 0) {
                    //User user = server.getUserInfo(params[0]);
                    server.logout(sessionId);
                    cookApplication.setLoggedInUser(null);
                    cookApplication.setSessionId(0);
                }else {
                    // do nothing
                }
            }  catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

}
